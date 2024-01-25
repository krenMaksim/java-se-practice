package com.kren.java.se.practice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class DataGeneratorUtil {

  private static final int ONE_MB = 1_000_000;

  public static void writeRandomBytes(File file, int sizeMB) {
    var data = generateRandomBytes(ONE_MB);
    IntStream.rangeClosed(1, sizeMB)
        .forEach(i -> writeBytesToFile(data, file));
  }

  private static byte[] generateRandomBytes(int length) {
    var data = new byte[length];
    new Random().nextBytes(data);
    return data;
  }

  private static void writeBytesToFile(byte[] data, File file) {
    try (var input = new ByteArrayInputStream(data);
         var output = new FileOutputStream(file, true)) {
      int byteOfData;
      while ((byteOfData = input.read()) != -1) {
        output.write(byteOfData);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
