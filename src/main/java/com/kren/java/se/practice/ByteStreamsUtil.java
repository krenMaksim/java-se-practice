package com.kren.java.se.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ByteStreamsUtil {

  private static final int EOF_BYTE = -1;

  public static void readFileAsBytes(File file) throws IOException {
    try (var input = new FileInputStream(file)) {
      System.out.println("Available bytes: " + input.available());
      int byteOfData;
      while ((byteOfData = input.read()) != EOF_BYTE) {
        System.out.print((char) byteOfData);
      }
    }
  }
}
