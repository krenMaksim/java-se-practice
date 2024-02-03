package com.kren.java.se.practice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

class ByteNioUtilTest {

  @Nested
  @TestInstance(PER_CLASS)
  class FileReader {

    private Path file;

    @BeforeAll
    void setUp() {
      file = Paths.get("src", "test", "resources", "some_file.txt");
    }

    @Test
    void readBytes() {
      assertDoesNotThrow(() -> ByteNioUtil.readBytes(file));
    }
  }
}
