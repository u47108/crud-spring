package cl.cleverit.licenseplate.service;

import java.io.Serializable;

public class License implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 2951697973563888841L;
  private String id;
  private String patente;
  private String tipoAuto;
  private String color;
  
  public License() {
    super();
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPatente() {
    return patente;
  }
  public void setPatente(String patente) {
    this.patente = patente;
  }
  public String getTipoAuto() {
    return tipoAuto;
  }
  public void setTipoAuto(String tipoAuto) {
    this.tipoAuto = tipoAuto;
  }
  public String getColor() {
    return color;
  }
  public void setColor(String color) {
    this.color = color;
  }
  

}
