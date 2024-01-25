package com.kren.java.se.practice;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ByteStreamsUtilTest {

  @Test
  void readFileAsBytes() {
    var file = Paths.get("src", "test", "resources", "some_file.txt").toFile();

    assertDoesNotThrow(() -> ByteStreamsUtil.readFileAsBytes(file));
  }

  @Test
  void writeBytesToFile() {
    var bytes = new ByteArrayInputStream(new byte[] {100, 103, 15});
    var file = Paths.get("target", "write_file.txt").toFile();

    assertDoesNotThrow(() -> ByteStreamsUtil.writeBytesToFile(bytes, file));
  }

  @Test
  void appendBytesToFile() {
    var bytes = new ByteArrayInputStream(new byte[] {100, 103, 15});
    var file = Paths.get("target", "write_file.txt").toFile();

    assertDoesNotThrow(() -> ByteStreamsUtil.appendBytesToFile(bytes, file));
  }
}