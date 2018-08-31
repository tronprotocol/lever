package org.tron.common.exception;

public class TestTypeException extends LeverException {

  public TestTypeException() {
    super("Test type exception");
  }

  public TestTypeException(String message) {
    super(message);
  }
}
