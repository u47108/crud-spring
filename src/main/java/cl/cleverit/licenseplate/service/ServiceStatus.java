package cl.cleverit.licenseplate.service;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ServiceStatus implements Serializable{
  private static final long serialVersionUID = -6826357242922340726L;

  private int code;
  @JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
  private int nativeCode;
  private String message;
  @JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
  private String nativeMessage;
  @JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
  private transient LocalDateTime timeStamp = LocalDateTime.now();

  public ServiceStatus() {
   
  }

  public ServiceStatus(int code, String message, String nativeMessage) {
    this.code = code;
    this.message = message;
    this.nativeMessage = nativeMessage;
  }

  public ServiceStatus(int code, String message) {
    this.code = code;
    this.message = message;

  }

  public int getNativeCode() {
    return nativeCode;
  }

  public void setNativeCode(int nativeCode) {
    this.nativeCode = nativeCode;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getNativeMessage() {
    return nativeMessage;
  }

  public void setNativeMessage(String nativeMessage) {
    this.nativeMessage = nativeMessage;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  @Override
  public String toString() {
    return "ServiceStatus{" + "code=" + code + ", message='" + message + '\'' + ", nativeMessage='" + nativeMessage
        + '\'' + ", timeStamp=" + timeStamp + '}';
  }

}
