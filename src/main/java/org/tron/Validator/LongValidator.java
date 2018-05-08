package org.tron.Validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class LongValidator implements IParameterValidator {

  @Override
  public void validate(String name, String value) throws ParameterException {
    long v = Long.valueOf(value);
    if (v <= 0) {
      throw new ParameterException("Parameter " + name + " should be positive (found " + value + ")");
    }
  }
}
