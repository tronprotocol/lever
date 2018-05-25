package org.tron.common.config;

import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.util.EnumMap;

public class ConfigImpl implements Config {

  @Override
  public EnumMap<ConfigProperty, Object> getConfig(String configFilePath, String configFilePathEquals) {
    EnumMap<ConfigProperty, Object> result = new EnumMap<ConfigProperty, Object>(ConfigProperty.class);

    com.typesafe.config.Config config;
    String configTip;
    if (configFilePath.equals(configFilePathEquals)) {
      config = ConfigFactory.load(configFilePath);
      configTip = "Default config: " + configFilePath;
    } else {
      File configFile = new File(configFilePath);
      config = ConfigFactory.parseFile(configFile);
      configTip = configFile.getAbsolutePath();
    }

    result.put(ConfigProperty.CONFIG, config);
    result.put(ConfigProperty.TIP, configTip);

    return result;
  }
}
