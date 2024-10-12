package ssau.fizlrock.modeling.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller
 */
@org.springframework.stereotype.Controller
public class Controller {
  @GetMapping("/report")
  String getHomePage() {
    return "main";
  }

}
