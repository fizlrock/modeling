package ssau.fizlrock.modeling.core;

import java.util.function.DoubleToLongFunction;
import java.util.function.LongToDoubleFunction;

/**
 * RandomValue
 */
public record RandomValue(
    String description,
    LongToDoubleFunction densityFunction,
    LongToDoubleFunction distributionFunction,
    DoubleToLongFunction reverseDistributionFunction,
    double disprersion,
    double mathExp,
    Range scope
  ) {
}
