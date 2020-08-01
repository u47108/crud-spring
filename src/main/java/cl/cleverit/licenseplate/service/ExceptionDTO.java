package cl.cleverit.licenseplate.service;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ExceptionDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private String error;
  private String message;

}
