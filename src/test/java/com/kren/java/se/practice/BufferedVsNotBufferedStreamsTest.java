package com.kren.java.se.practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

import static com.kren.java.se.practice.ByteStreamsUtil.readInputStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

class BufferedVsNotBufferedStreamsTest {

  @Nested
  @TestInstance(PER_CLASS)
  class ReadingOperation {

    private File file;

    @BeforeAll
    void setUp() {
      file = Paths.get("src", "test", "resources", "buffered_vs_not_buffered_streams.txt").toFile();
      DataGeneratorUtil.writeRandomBytes(file, 10);
    }

    @Test
    @SneakyThrows
    void readFile() {
      var input = new FileInputStream(file);

      assertDoesNotThrow(() -> readInputStream(input));
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(ints = {1024, 4096, 8192, 16384, 32768, 65536})
    void readFileBuffered(int dufferSizeBytes) {
      var input = new BufferedInputStream(new FileInputStream(file), dufferSizeBytes);

      assertDoesNotThrow(() -> readInputStream(input));
    }

    @AfterAll
    void tearDown() {
      file.delete();
    }
  }
}