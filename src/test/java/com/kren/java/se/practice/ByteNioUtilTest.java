package com.kren.java.se.practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.kren.java.se.practice.ByteStreamsUtil.writeInputToOutput;
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
      var output = new FileOutputStream(file);

      assertDoesNotThrow(() -> ByteNioUtil.writeBytes(file.toPath(), new byte[] {100, 103, 15}));
    }

    @Test
    @SneakyThrows
    void writeBytesToFileViaBufferedStream() {
      var output = new BufferedOutputStream(new FileOutputStream(file));

      assertDoesNotThrow(() -> writeInputToOutput(bytes, output));
    }

    @Test
    @SneakyThrows
    void appendBytesToFile() {
      var output = new FileOutputStream(file, true);

      assertDoesNotThrow(() -> writeInputToOutput(bytes, output));
    }
  }
}
