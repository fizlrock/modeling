package ssau.fizlrock.modeling.core.modeling.rvgeneratorscore;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import ssau.fizlrock.modeling.core.random.RandomValue;
import ssau.fizlrock.modeling.core.util.Cords;
import ssau.fizlrock.modeling.core.util.Range;

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
