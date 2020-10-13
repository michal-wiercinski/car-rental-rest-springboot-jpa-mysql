package com.miwi.carrental.security.payload.response;

public class MessageResponse {
  private String message;
  private Object data;

  public MessageResponse(String message) {
    this.message = message;
  }

  public MessageResponse(Object data, String message) {
    this.data = data;
    this.message = message;
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
