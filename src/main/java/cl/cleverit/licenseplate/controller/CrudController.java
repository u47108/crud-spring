package cl.cleverit.licenseplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.cleverit.licenseplate.service.ServiceStatus;
import cl.cleverit.licenseplate.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/")
public class CrudController {

  TransactionService service;

  @Autowired
  public CrudController(TransactionService service) {
      this.service=service;
  }
  @CrossOrigin(origins = "*")
  @PutMapping("/save")
  @ApiOperation(value = "busca la lista de licencias en un ws y guarda la info en una base de datos mysql")
  public ResponseEntity<ServiceStatus> saveAllVechiculos(){
    log.info("saveAllVechiculos");
    ServiceStatus res = service.saveLicensePlate();
    return new ResponseEntity<>(service.saveLicensePlate(),HttpStatus.valueOf(res.getCode()));
  }
}
