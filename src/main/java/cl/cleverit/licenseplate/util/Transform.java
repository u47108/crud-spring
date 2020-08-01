package cl.cleverit.licenseplate.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import cl.cleverit.licenseplate.entity.Movil;
import cl.cleverit.licenseplate.service.License;

public class Transform {

  public Transform() {
    super();
    // TODO Auto-generated constructor stub
  }

  public static Iterable converToEnttity(License[]list) {
    return Arrays.stream(list).map(temp -> {
      Movil obj = new Movil();
      obj.setColor(temp.getColor());
      obj.setId(temp.getId());
      obj.setPatente(temp.getPatente());
      obj.setTipoAuto(temp.getTipoAuto());
      return obj;
    }).collect(Collectors.toList());
  }

}
