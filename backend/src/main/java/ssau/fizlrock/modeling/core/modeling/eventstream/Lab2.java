package ssau.fizlrock.modeling.core.modeling.eventstream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongToDoubleFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Lab2
 */
public class Lab2 {

  public static Report getV22Report(double alpha) {

    LongToDoubleFunction lambdaFunction = t -> t * alpha;
    final long eventNeed = 1000;

    ArrayList<Long> simulationTimes = new ArrayList<>();

    double average = Stream.generate(() -> new NoStatPuasson(lambdaFunction))
        .peek(st -> st.waitNEvents(eventNeed))
        .map(NoStatPuasson::getTime)
        .peek(simulationTimes::add)
        .mapToLong(Long::valueOf)
        .limit(10)
        .average()
        .getAsDouble();

    var st = new NoStatPuasson(lambdaFunction);
    st.waitNEvents(eventNeed);

    if (st.time > Integer.MAX_VALUE)
      throw new IllegalStateException();

    var event_chart = new ArrayList<Long>();
    event_chart.add(st.history.getFirst());
    for (int i = 1; i < st.history.size(); i++)
      event_chart.add(i, st.history.get(i) + event_chart.get(i - 1));

    return new Report(simulationTimes, average, event_chart);

  }

  public static record Report(
      List<Long> simulationTimes,
      double averageSimulationTime,
      List<Long> eventChart) {
  }
}
