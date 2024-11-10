package ssau.fizlrock.modeling.core.random;

import java.util.function.DoubleToLongFunction;
import java.util.function.LongToDoubleFunction;

import ssau.fizlrock.modeling.core.util.Range;

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
    Range scope) {
}
