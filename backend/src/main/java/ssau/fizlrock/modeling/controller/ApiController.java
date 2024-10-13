package ssau.fizlrock.modeling.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import ssau.fizlrock.modeling.core.ModelingReport;
import ssau.fizlrock.modeling.core.ModelingParams;
import ssau.fizlrock.modeling.core.*;

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

    return Mono.just(new ModelingSession(rv,params).run());
  }

}
