package ssau.fizlrock.modeling.core.random.generator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;

import ssau.fizlrock.modeling.core.random.RandomValue;
import ssau.fizlrock.modeling.core.random.RandomValueCollection;

/**
 * Генератор СВ распределенной по закону Пуассона.
 * <p>
 * Используется метотод обратной функции
 */
public class Puasson1 implements RandomValueGenerator {

  private RandomValue rv;
  private DoubleSupplier source_generator = ThreadLocalRandom.current()::nextDouble;

  public Puasson1(double alpha) {
    rv = RandomValueCollection.puasson(alpha);

  }

  @Override
  public Long get() {
    return rv.reverseDistributionFunction()
        .applyAsLong(source_generator.getAsDouble());
  }

}
