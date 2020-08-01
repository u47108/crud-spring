package cl.cleverit.licenseplate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealcheckController {

  /**
   * Endpoint para hacer healthcheck
   */
  @CrossOrigin(origins = "*")
  @GetMapping(value = "/healthcheck")
  public ResponseEntity<String> healthcheck() {
    return new ResponseEntity<>("OK STATUS 200", HttpStatus.OK);
  }
}
