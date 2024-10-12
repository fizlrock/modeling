package ssau.fizlrock.modeling.core;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * ModelingEngine
 */
public class ModelingSession {

  class ValueToIntervalIndexF implements LongToIntFunction {

    @Override
    public int applyAsInt(long x) {
      // TODO тут по хорошему бин поиск применить
      int index = 0;
      for (; index < expRanges.size() - 1; index++) {
        var r = expRanges.get(index);
        if (r.a() <= x && r.b() > x)
          break;
      }
      return index;
    }
  }

  private static final double EPSILON = 0.0000001;

  /**
   * Функция разбивает область определения дискретной случайно величины с заданной
   * плотностью распределения вероятностей на отрезки с равной вероятностью
   * попадания
   * 
   * @param left            Левая граница области распределения
   * @param right           Правая граница области распределения
   * @param count           Желаемое число отрезков
   * @param densityFunction Функция распределения плотности вероятностей
   * @return
   */
  @Deprecated
  static List<Range> generateRanges(
      long left,
      long right,
      long count,
      LongToDoubleFunction densityFunction) {

    List<Range> ranges = new ArrayList<>();
    double targetProb = 1.0 / count;
    double summaryProb = 0;
    int a = 0;
    int b = 0;
    while (ranges.size() != count) {
      double rangeProb = densityFunction.applyAsDouble(a);
      while (rangeProb < targetProb)
        rangeProb += densityFunction.applyAsDouble(++b);
      ranges.add(new Range(a, b + 1, rangeProb));
      summaryProb += ranges.getLast().prob();
      if (1 - summaryProb < targetProb) {
        ranges.add(new Range(b + 1, Long.MAX_VALUE, 1 - summaryProb));
        summaryProb += ranges.getLast().prob();
        break;
      }
      a = b + 1;
      b = a;
    }

    // Чистка интервалов

    while (ranges.getLast().prob() < 0.05) {
      var last = ranges.removeLast();
      var prevLast = ranges.removeLast();
      ranges.add(new Range(prevLast.a(), last.b(), last.prob() + prevLast.prob()));
    }
    return ranges;
  }

  private RandomValue rv;

  private ModelingParams params;

  /**
   * Разбиение области определения на равновероятные интервалы
   */
  private List<Range> expRanges;
  private LongToIntFunction valueToIndexFunction;
  private DoubleSupplier sourceRv = ThreadLocalRandom.current()::nextDouble;
  private ModelingReport lastReport;

  public ModelingSession(RandomValue rv,
      ModelingParams params) {
    this.rv = rv;
    this.params = params;

    generateRangesForDiscrBeta();
    filterRanges();
    valueToIndexFunction = new ValueToIntervalIndexF();

  }

  public ModelingReport run() {

    double[] source_sequence = DoubleStream.generate(sourceRv)
        .limit(params.valuesCount())
        .toArray();

    long[] synt_sequence = DoubleStream.of(source_sequence)
        .mapToLong(rv.reverseDistributionFunction())
        .toArray();

    List<Range> realProbs = LongStream.of(synt_sequence)
        .mapToInt(valueToIndexFunction) // Индекс интервала в который попала СВ
        .boxed()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting())) // Мапа индекс интервала - число попаданий
        .entrySet().stream()
        .map(pair -> {
          var r = expRanges.get(pair.getKey());
          var realProb = new Range(r.a(), r.b(),
              pair.getValue() / (double) params.valuesCount());
          return realProb;
        })
        .sorted(Comparator.comparing(x -> x.a()))
        .collect(Collectors.toList());

    double hi = calcHi(realProbs);
    System.out.println("HI:  " + hi);

    lastReport = ModelingReport.builder()
        .rv(rv)
        .sourceValues(source_sequence)
        .syntValues(synt_sequence)
        .teorDensityDots(prepareTeorGraphic())
        .realDensityDots(prepareRealGraphic(synt_sequence))
        .realProbs(realProbs)
        .hi(hi)
        .build();

    return lastReport;
  }

  private List<Cords> prepareRealGraphic(long[] synt_values) {
    long l = max(rv.scope().a(), expRanges.getFirst().b() - 5);
    long r = min(rv.scope().b(), expRanges.getLast().a() + 5);

    Map<Long, Long> dict = LongStream.of(synt_values)
        .boxed()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));// Мапа индекс интервала - число попаданий

    return LongStream.range(l, r + 1)
        .boxed()
        .flatMap(i -> Stream.of(
            new Cords(i, (double) dict.getOrDefault(i, 0l) / synt_values.length),
            new Cords(i, (double) dict.getOrDefault(i+1, 0l) / synt_values.length)
          ))
        .collect(Collectors.toList());
  }

  private double calcHi(List<Range> realProbs) {
    record Pair(Range real, Range expect) {
    }

    return IntStream.range(0, realProbs.size())
        .mapToObj(i -> new Pair(realProbs.get(i), expRanges.get(i)))
        .mapToDouble(p -> calcHi(p.expect, p.real))
        .sum();
  }

  private double calcHi(Range expRange, Range realRange) {
    System.out.println("ModelingSession.calcHi()");
    long n = params.valuesCount();
    long expN = (long) (expRange.prob() * n);
    long realN = (long) (realRange.prob() * n);
    double hi = Math.pow(expN - realN, 2) / expN;

    return hi;
  }

  private List<Cords> prepareTeorGraphic() {

    long l = max(rv.scope().a(), expRanges.getFirst().b() - 5);
    long r = min(rv.scope().b(), expRanges.getLast().a() + 5);
    System.out.println(l);
    System.out.println(r);

    return LongStream.range(l, r + 1)
        .mapToObj(i -> new Cords(i, rv.densityFunction().applyAsDouble(i)))
        .collect(Collectors.toList());

  }

  /**
   * Функция разбивает область определения дискретной случайно величины с заданной
   * плотностью распределения вероятностей на отрезки с равной вероятностью
   * попадания
   * 
   * @param left            Левая граница области распределения
   * @param right           Правая граница области распределения
   * @param count           Желаемое число отрезков
   * @param densityFunction Функция распределения плотности вероятностей
   * @return
   */
  private void generateRangesForDiscrBeta() {

    double target_P = 1.0 / params.rangeCount();

    long[] points = DoubleStream
        .iterate(0.0, p -> p - 1 < EPSILON, p -> p += target_P)
        .mapToLong(rv.reverseDistributionFunction())
        .distinct()
        .toArray();

    List<Range> ranges = new ArrayList<>();

    for (int i = 0; i < points.length - 1; i += 1) {
      long l = points[i], r = points[i + 1];
      double p = rv.distributionFunction().applyAsDouble(r) - rv.distributionFunction().applyAsDouble(l);
      ranges.add(new Range(l, r, p));
    }

    this.expRanges = ranges;

  }

  private boolean filterRanges() {
    boolean changed = false;
    for (int i = 0; i < expRanges.size();) {

      if (expRanges.get(i).prob() >= 0.05) {
        i++;
        continue;
      }

      int mergeIndex = -1;

      if (i == 0)
        mergeIndex = i + 1;
      else if (i == expRanges.size() - 1)
        mergeIndex = i - 1;
      else if (expRanges.get(i - 1).prob() < expRanges.get(i + 1).prob())
        mergeIndex = i - 1;
      else
        mergeIndex = i + 1;

      int insertIndex = min(i, mergeIndex);

      var left = expRanges.remove(insertIndex);
      var right = expRanges.remove(insertIndex);

      expRanges.add(insertIndex, new Range(left.a(), right.b(), left.prob() + right.prob()));

    }

    return changed;

  }
}
