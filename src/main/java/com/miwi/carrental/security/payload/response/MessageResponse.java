package com.miwi.carrental.security.payload.response;

public class MessageResponse {

  private String message;
  private Object data;
  private Boolean error;

  public MessageResponse(String message) {
    this.message = message;
  }

  public MessageResponse(Object data, String message) {
    this.data = data;
    this.message = message;
  }

  public MessageResponse(Object data, String message, Boolean error) {
    this.data = data;
    this.message = message;
    this.error = error;
  }

  public Boolean getError() {
    return error;
  }

  public void setError(Boolean error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
