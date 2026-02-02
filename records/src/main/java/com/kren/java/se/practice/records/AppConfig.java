package com.kren.java.se.practice.records;

import java.util.Optional;

import static com.kren.java.se.practice.records.AppConfig.Properties.JAVA_HOME;
import static com.kren.java.se.practice.records.AppConfig.Properties.NUMBER_OF_PROCESSORS;
import static com.kren.java.se.practice.records.AppConfig.Properties.USERNAME;
import static java.lang.Integer.parseInt;
import static java.util.function.Predicate.not;

record AppConfig(String userName, String javaHome, int numberOfProcessors) {

  public static AppConfig newInstance() {
    return new AppConfig(
        USERNAME.getValue(),
        JAVA_HOME.getValue(),
        parseInt(NUMBER_OF_PROCESSORS.getValue())
    );
  }

  AppConfig {
    validate(USERNAME.name(), userName);
    validate(JAVA_HOME.name(), javaHome);
    validate(NUMBER_OF_PROCESSORS.name(), numberOfProcessors);
  }

  private static void validate(String name, String value) {
    Optional.ofNullable(value)
        .filter(not(String::isBlank))
        .orElseThrow(() -> new IllegalArgumentException(String.format("%s is not set", name)));
  }

  private static void validate(String name, int value) {
    if (value <= 0) {
      throw new IllegalArgumentException(String.format("%s is not set", name));
    }
  }

  enum Properties {
    USERNAME,
    JAVA_HOME,
    NUMBER_OF_PROCESSORS;

    public String getValue() {
      return System.getenv().get(this.name());
    }
  }
}
