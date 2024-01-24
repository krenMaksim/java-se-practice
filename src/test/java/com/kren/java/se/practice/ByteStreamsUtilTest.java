package com.kren.java.se.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ByteStreamsUtilTest {

  @Test
  void readFileAsBytes() throws IOException {
    var file = Paths.get("src", "test", "resources", "some_file.txt").toFile();

    assertDoesNotThrow(() -> ByteStreamsUtil.readFileAsBytes(file));
  }
}