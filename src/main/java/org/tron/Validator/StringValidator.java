package org.tron.Validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.apache.commons.lang3.StringUtils;

public class StringValidator implements IParameterValidator {

  @Override
  public void validate(String name, String value) throws ParameterException {
    if (StringUtils.isBlank(value)) {
      throw new ParameterException("Parameter " + name + " should be file path string (found " + value + ")");
    }
  }
}
