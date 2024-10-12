package ssau.fizlrock.modeling.core;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

import java.util.function.DoubleToLongFunction;
import java.util.function.LongToDoubleFunction;
import java.util.stream.LongStream;

/**
 * RandomValueCollection
 */
public class RandomValueCollection {

  private static final double EPSILON = 0.0000001;

  private static long factorial(long number) {
    long result = 1;
    for (int factor = 2; factor <= number; factor++) {
      result *= factor;
    }
    return result;
  }

  // TODO дописать нормально имя
  public static RandomValue puasson(double alpha) {

    var scope = new Range(0, Long.MAX_VALUE, 1);
    LongToDoubleFunction densityFunction = (long x) -> pow(alpha, x) / factorial(x) * exp(-alpha);

    DoubleToLongFunction reverseDistributionFunction = (double x) -> {
      if (x > 0.999)
        return scope.b();
      int k = 0;
      double right = densityFunction.applyAsDouble(k);
      while (x > right)
        right += densityFunction.applyAsDouble(++k);
      // System.out.printf("%f -> %d\n", x, k);
      return k;
    };

    LongToDoubleFunction distributionFunciton = (long x) -> {
      if (x < 0)
        return 0.0;
      if (x > alpha * 10) // TODO сомнительно, но окей
        return 1.0;
      else
        return LongStream
            .range(scope.a(), x)
            .mapToDouble(densityFunction)
            .sum();
    };

    return new RandomValue(
        "Распределение Пуассона",
        densityFunction,
        distributionFunciton,
        reverseDistributionFunction,
        alpha,
        alpha,
        scope);
  }

}
