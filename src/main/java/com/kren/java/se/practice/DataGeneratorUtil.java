package com.kren.java.se.practice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Random;
import java.util.stream.IntStream;

public class DataGeneratorUtil {

  private static final int ONE_MB = 1_000_000;

  public static void writeRandomBytes(File file, int sizeMB) {
    var data = generateRandomBytes(ONE_MB);
    IntStream.rangeClosed(1, sizeMB)
        .mapToObj(i -> new ByteArrayInputStream(data))
        .forEach(inputStream -> ByteStreamsUtil.appendBytesToFile(inputStream, file));
  }

  private static byte[] generateRandomBytes(int length) {
    var data = new byte[length];
    new Random().nextBytes(data);
    return data;
  }
}
