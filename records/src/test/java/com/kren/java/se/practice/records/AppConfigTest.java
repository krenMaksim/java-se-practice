package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppConfigTest {

  @Test
  void readProperties() {
    assertDoesNotThrow(() -> System.out.println(AppConfig.newInstance()));
  }
}