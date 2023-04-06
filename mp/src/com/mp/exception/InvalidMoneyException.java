package com.mp.exception;

public class InvalidMoneyException extends Exception{
  public InvalidMoneyException(String msg) {
    super(msg);
  }

  public InvalidMoneyException() {
    this("this is invalidMoneyException");
  }
}
