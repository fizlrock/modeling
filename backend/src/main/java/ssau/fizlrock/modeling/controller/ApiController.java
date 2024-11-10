package ssau.fizlrock.modeling.controller;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ssau.fizlrock.modeling.core.modeling.eventstream.Lab2;
import ssau.fizlrock.modeling.core.modeling.eventstream.Lab2.Report;
import ssau.fizlrock.modeling.core.modeling.rvgeneratorscore.ModelingParams;
import ssau.fizlrock.modeling.core.modeling.rvgeneratorscore.ModelingReport;
import ssau.fizlrock.modeling.core.modeling.rvgeneratorscore.ModelingSession;
import ssau.fizlrock.modeling.core.random.RandomValueCollection;
import ssau.fizlrock.modeling.core.random.generator.Puasson1;
import ssau.fizlrock.modeling.core.random.generator.Puasson2;

/**
 * MainController
 */
@RestController
public class ApiController {

  @GetMapping("/api/report")
  @CrossOrigin(origins = "*")
  Mono<ModelingReport> homePage(
      @RequestParam(required = true) Long valueCount,
      @RequestParam(required = true) Long rangeCount,
      @RequestParam(required = true) Double alpha) {

    var params = new ModelingParams(valueCount, rangeCount);
    var rv = RandomValueCollection.puasson(alpha);

    return Mono.just(new ModelingSession(rv, params).run());
  }

  @GetMapping("/api/lab2/22")
  @CrossOrigin(origins = "*")
  Report getLab2Report(@RequestParam Double alpha) {
    return Lab2.getV22Report(alpha);
  }

  @GetMapping("/api/puasson")
  @CrossOrigin(origins = "*")
  Flux<Number> getPuassonRandomValue(
      @RequestParam Long count,
      @RequestParam Double alpha) {

    final double defaultAlpha = 3;
    alpha = alpha == null ? defaultAlpha : alpha;
    final long defaultCount = 10;
    count = count == null ? defaultCount : count;

    return fluxFromSupplier(new Puasson2(alpha), count);
  }

  static <T> Flux<T> fluxFromSupplier(Supplier<T> s, long limit) {
    return Flux.<T>fromStream(
        Stream.generate(s).limit(limit));
  }

}
