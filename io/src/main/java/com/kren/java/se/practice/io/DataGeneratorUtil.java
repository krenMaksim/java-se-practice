package com.kren.java.se.practice.io;

import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.stream.IntStream;

public class DataGeneratorUtil {

  private static final int ONE_MB = 1_000_000;

  public static void writeRandomBytes(File file, int sizeMB) {
    var data = generateRandomBytes(ONE_MB);
    IntStream.rangeClosed(1, sizeMB)
        .mapToObj(i -> new ByteArrayInputStream(data))
        .forEach(inputStream -> appendBytesToFile(inputStream, file));
  }

  @SneakyThrows
  private static void appendBytesToFile(InputStream input, File file) {
    ByteStreamsUtil.writeInputToOutput(input, new BufferedOutputStream((new FileOutputStream(file, true))));
  }

  private static byte[] generateRandomBytes(int length) {
    var data = new byte[length];
    new Random().nextBytes(data);
    return data;
  }
}
