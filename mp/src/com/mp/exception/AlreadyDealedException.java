package com.mp.exception;

public class AlreadyDealedException extends Exception{
  public AlreadyDealedException(String msg) {
    super(msg);
  }

  public AlreadyDealedException() {
    this("this is AlreadyDealedException");
  }
}
