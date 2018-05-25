package org.tron.common.config;

import java.util.EnumMap;

public interface Config {
  enum ConfigProperty {
    CONFIG, TIP
  }

  EnumMap<ConfigProperty, Object> getConfig(String configFilePath, String configFilePathEquals);
}
