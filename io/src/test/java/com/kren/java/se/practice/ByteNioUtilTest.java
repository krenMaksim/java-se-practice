package com.kren.java.se.practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.File;
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

  @Nested
  class FileWriter {

    private ByteArrayInputStream bytes;
    private File file;

    @BeforeEach
    void setUp() {
      bytes = new ByteArrayInputStream(new byte[] {100, 103, 15});
      file = Paths.get("target", "write_file.txt").toFile();
    }

    @Test
    @SneakyThrows
    void writeBytesToFile() {
      assertDoesNotThrow(() -> ByteNioUtil.writeBytesToFile(file.toPath(), new byte[] {100, 103, 15}));
    }

    @Test
    @SneakyThrows
    void appendBytesToFile() {
      assertDoesNotThrow(() -> ByteNioUtil.appendBytesToFile(file.toPath(), new byte[] {100, 103, 15}));
    }
  }
}
