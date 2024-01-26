package com.kren.java.se.practice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

class ByteStreamsUtilTest {

  @Nested
  @TestInstance(PER_CLASS)
  class FileReader {

    private File file;

    @BeforeAll
    void setUp() {
      file = Paths.get("src", "test", "resources", "some_file.txt").toFile();
    }

    @Test
    void readFileAsBytes() {
      assertDoesNotThrow(() -> ByteStreamsUtil.readFileAsBytes(file));
    }

    @Test
    void readFileAsBytesViaBufferedStream() {
      assertDoesNotThrow(() -> ByteStreamsUtil.readFileAsBytesViaBufferedStream(file));
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
    void writeBytesToFile() {
      assertDoesNotThrow(() -> ByteStreamsUtil.writeBytesToFile(bytes, file));
    }

    @Test
    void writeBytesToFileViaBufferedStream() {
      assertDoesNotThrow(() -> ByteStreamsUtil.writeBytesToFileViaBufferedStream(bytes, file));
    }

    @Test
    void appendBytesToFile() {
      assertDoesNotThrow(() -> ByteStreamsUtil.appendBytesToFile(bytes, file));
    }

    @Test
    void appendBytesToFileViaBufferedStream() {
      assertDoesNotThrow(() -> ByteStreamsUtil.appendBytesToFileViaBufferedStream(bytes, file));
    }
  }
}