package com.mp.exception;

public class RecordNotFoundException extends Exception{
  public RecordNotFoundException(String msg) {
    super(msg);
  }

  public RecordNotFoundException() {
    this("this is RecordNotFoundException");
  }
}
