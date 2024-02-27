package com.kren.java.se.practice.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataGeneratorUtilTest {

  @Test
  void writeRandomBytes() throws IOException {
    var file = Paths.get("target", "random_bytes_data.txt").toFile();
    file.delete();

    DataGeneratorUtil.writeRandomBytes(file, 1);

    assertEquals(1_000_000, file.length());
  }
}