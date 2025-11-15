package cl.cleverit.licenseplate.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.cleverit.licenseplate.entity.Movil;
import cl.cleverit.licenseplate.service.License;

/**
 * Utility class for transforming data objects.
 * Optimizado para mejor rendimiento y uso de memoria.
 */
public class Transform {

  private Transform() {
    // Utility class - no instantiation
  }

  /**
   * Converts an array of License objects to a List of Movil entities.
   * Optimizado: pre-dimensiona ArrayList y usa for-loop para mejor rendimiento
   * 
   * @param list Array of License objects to convert
   * @return List of Movil entities
   * @throws NullPointerException if list is null
   */
  public static List<Movil> converToEnttity(License[] list) {
    if (list == null) {
      throw new NullPointerException("License array cannot be null");
    }
    
    if (list.length == 0) {
      return new ArrayList<>(0);
    }
    
    // Pre-dimensionar ArrayList con tamaño conocido para evitar reasignaciones
    List<Movil> result = new ArrayList<>(list.length);
    
    // Usar for-loop tradicional que es más rápido que stream para operaciones simples
    for (License license : list) {
      if (license != null) {
        Movil obj = new Movil();
        obj.setColor(license.getColor());
        obj.setId(license.getId());
        obj.setPatente(license.getPatente());
        obj.setTipoAuto(license.getTipoAuto());
        result.add(obj);
      }
    }
    
    return result;
  }

}
