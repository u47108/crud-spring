package cl.cleverit.licenseplate.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "movil", indexes =  @Index(name="movil_patente_IDX", columnList = "patente"))
public class Movil {
  private Long key;
  private String id;
  private String patente;
  private String tipoAuto;
  private String color;
  
  
  public Movil() {
    super();
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "key")
  public Long getKey() {
    return key;
  }
  public void setKey(Long key) {
    this.key = key;
  }
  @Column(name = "id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  @Column(name = "patente")
  public String getPatente() {
    return patente;
  }
  public void setPatente(String patente) {
    this.patente = patente;
  }
  @Column(name = "tipoAuto")
  public String getTipoAuto() {
    return tipoAuto;
  }
  public void setTipoAuto(String tipoAuto) {
    this.tipoAuto = tipoAuto;
  }
  @Column(name = "color")
  public String getColor() {
    return color;
  }
  public void setColor(String color) {
    this.color = color;
  }
  
  

}
