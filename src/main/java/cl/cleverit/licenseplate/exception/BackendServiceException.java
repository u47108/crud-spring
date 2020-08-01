package cl.cleverit.licenseplate.exception;

public class BackendServiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BackendServiceException(String message) {
      super(message);
  }
  
  public BackendServiceException(String message, Exception e) {
    super(message,e);
  }

}