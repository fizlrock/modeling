package ssau.fizlrock.modeling.core.random.generator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;

/**
 * Генератор СВ распределенной по пуассону, использующий стремный метод
 */
public class Puasson2 implements RandomValueGenerator {

  DoubleSupplier rv = ThreadLocalRandom.current()::nextDouble;

  private double l;

  public Puasson2(double alpha) {

    this.l = Math.exp(-alpha);
    System.out.println(l);
  }

  public void setAlpha(double alpha) {
    this.l = Math.exp(-alpha);
  }

  @Override
  public Long get() {

    long k = 0;
    double mk = rv.getAsDouble();
    double mkn = mk * rv.getAsDouble();

    while (l < mk) {
      mk = mkn;
      mkn *= rv.getAsDouble();
      k++;
    }

    return k;
  }

}
