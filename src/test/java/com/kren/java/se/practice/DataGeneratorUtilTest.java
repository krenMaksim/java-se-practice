package com.kren.java.se.practice;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DataGeneratorUtilTest {

  @Test
  void writeRandomBytes() {
    var file = Paths.get("target", "random_bytes_data.txt").toFile();

    assertDoesNotThrow(() -> DataGeneratorUtil.writeRandomBytes(file, 10));
  }
}