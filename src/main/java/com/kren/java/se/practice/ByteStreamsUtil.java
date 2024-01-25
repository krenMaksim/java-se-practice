package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/bytestreams.html
*/

public class ByteStreamsUtil {

  static final int EOF_BYTE = -1;

  @SneakyThrows
  public static void readFileAsBytes(File file) {
    try (var input = new FileInputStream(file)) {
      System.out.println("Available bytes: " + input.available());
      int byteOfData;
      while ((byteOfData = input.read()) != EOF_BYTE) {
        System.out.print((char) byteOfData);
      }
    }
  }

  public static void writeBytesToFile(InputStream input, File file) {
    writeBytesToFile(input, file, false);
  }

  public static void appendBytesToFile(InputStream input, File file) {
    writeBytesToFile(input, file, true);
  }

  @SneakyThrows
  private static void writeBytesToFile(InputStream input, File file, boolean append) {
    var output = new FileOutputStream(file, append);
    try (input; output) {
      int byteOfData;
      while ((byteOfData = input.read()) != EOF_BYTE) {
        output.write(byteOfData);
      }
    }
  }

  private static String toBinary(int byteOfData) {
    // https://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to-its-binary-string-representation
    var b1 = (byte) byteOfData;
    return String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
  }
}
