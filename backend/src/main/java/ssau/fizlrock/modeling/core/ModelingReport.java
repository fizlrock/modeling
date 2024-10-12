package ssau.fizlrock.modeling.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelingReport {
  private final RandomValue rv;
  @JsonIgnore
  private final double[] sourceValues;
  @JsonIgnore
  private final long[] syntValues;
  @JsonIgnore
  private final List<Range> realProbs;
  private final List<Cords> teorDensityDots;
  private final List<Cords> realDensityDots;
  private final Double hi;
}
