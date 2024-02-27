package com.kren.java.se.practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static com.kren.java.se.practice.ByteNioUtil.readBytes;
import static com.kren.java.se.practice.ByteStreamsUtil.readInputStream;
import static com.kren.java.se.practice.ByteStreamsUtil.writeInputToOutput;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BufferedVsNotBufferedStreamsTest {

  private static final int FILE_SIZE_MB = 100;
  private static final int FILE_SIZE_BYTE = FILE_SIZE_MB * 1_000_000;

  private static File file;

  @BeforeAll
  static void createFile() {
    file = Paths.get("target", "buffered_vs_not_buffered_streams.txt").toFile();
    DataGeneratorUtil.writeRandomBytes(file, FILE_SIZE_MB);
  }

  @AfterAll
  static void deleteFile() {
    file.delete();
  }

  @Nested
  class ReadingOperation {

    private List<Integer> data;
    private IntConsumer byteProcessor;

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

    @ParameterizedTest
    @ValueSource(ints = {32, 64, 128, 256, 512, 1024, 2048})
    void readFileNio(int bufferCapacityBytes) {
      Consumer<ByteBuffer> bufferProcessor = byteBuffer -> data.add((int) byteBuffer.get());

      readBytes(file.toPath(), bufferCapacityBytes, bufferProcessor);

      assertEquals(FILE_SIZE_BYTE, data.size());
    }
  }

  @Nested
  class WritingOperation {

    private File targetFile;
    private InputStream input;

    @BeforeEach
    @SneakyThrows
    void setUp() {
      targetFile = Paths.get("target", "target_buffered_vs_not_buffered_streams.txt").toFile();
      input = new BufferedInputStream(new FileInputStream(file));
    }

    @Test
    @SneakyThrows
    void writeToFile() {
      var output = new FileOutputStream(targetFile);

      writeInputToOutput(input, output);

      assertEquals(FILE_SIZE_BYTE, targetFile.length());
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(ints = {1024, 4096, 8192, 16384, 32768, 65536})
    void writeToFileBuffered(int dufferSizeBytes) {
      var output = new BufferedOutputStream(new FileOutputStream(targetFile), dufferSizeBytes);

      writeInputToOutput(input, output);

      assertEquals(FILE_SIZE_BYTE, targetFile.length());
    }

    @AfterEach
    void tearDown() {
      targetFile.delete();
    }
  }
}
