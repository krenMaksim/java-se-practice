package com.kren.java.se.practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;

import static com.kren.java.se.practice.ByteStreamsUtil.readInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BufferedVsNotBufferedStreamsTest {

  @Nested
  class ReadingOperation {

    private static final int FILE_SIZE_MB = 10;
    private static final int FILE_SIZE_BYTE = FILE_SIZE_MB * 1_000_000;

    private static File file;

    @BeforeAll
    static void createFile() {
      file = Paths.get("src", "test", "resources", "buffered_vs_not_buffered_streams.txt").toFile();
      DataGeneratorUtil.writeRandomBytes(file, FILE_SIZE_MB);
    }

    private List<Integer> data;
    IntConsumer byteProcessor;

    @BeforeEach
    void setUp() {
      data = new LinkedList<>();
      byteProcessor = byteOfData -> data.add(byteOfData);
    }

    @Test
    @SneakyThrows
    void readFile() {
      var input = new FileInputStream(file);

      readInputStream(input, byteProcessor);

      assertEquals(FILE_SIZE_BYTE, data.size());
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(ints = {1024, 4096, 8192, 16384, 32768, 65536})
    void readFileBuffered(int dufferSizeBytes) {
      var input = new BufferedInputStream(new FileInputStream(file), dufferSizeBytes);

      readInputStream(input, byteProcessor);

      assertEquals(FILE_SIZE_BYTE, data.size());
    }

    @AfterAll
    static void deleteFile() {
      file.delete();
    }
  }
}
