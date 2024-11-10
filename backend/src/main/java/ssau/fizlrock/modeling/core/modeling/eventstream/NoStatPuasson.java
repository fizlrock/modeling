package ssau.fizlrock.modeling.core.modeling.eventstream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongToDoubleFunction;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssau.fizlrock.modeling.core.random.generator.Puasson2;

/**
 * NoStatPuasson
 */
@RequiredArgsConstructor
@Slf4j
public class NoStatPuasson {

  // Функция интенсивности от модельного времени
  final LongToDoubleFunction timeToLabmda;

  long eventHappen = 0; // Сколько событий случилось
  long time = 0; // Текущее модельное время

  public long getTime() {
    return time;
  }

  ArrayList<Long> history = new ArrayList<>();
  Puasson2 eventSupplier = new Puasson2(1);

  /**
   * Сделать шаг модельного времени
   * 
   * @return сколько событий случилось
   */
  public long makeStep() {
    double lambda = timeToLabmda.applyAsDouble(time);
    eventSupplier.setAlpha(lambda);
    long happenned = (long) eventSupplier.get();
    eventHappen += happenned;
    history.add(happenned);
    time++;
    return happenned;
  }

  public List<Long> makeSteps(long count) {
    return Stream.generate(this::makeStep)
        .limit(count)
        .toList();
  }

  public boolean waitNEvents(long eventNeed, long timeLimit) {
    while (time < timeLimit && eventHappen < eventNeed)
      makeStep();

    return eventHappen > eventNeed;
  }

  public boolean waitNEvents(long eventNeed) {
    return waitNEvents(eventNeed, 1000000);
  }
}
