package com.kren.java.se.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

  public static void writeBytesToFile(InputStream input, File file) throws IOException {
    var output = new FileOutputStream(file);
    try (input; output) {
      int i;
      while ((i = input.read()) != EOF_BYTE) {
        output.write(i);
      }
    }
  }

  private static String toBinary(int oneByte) {
    // https://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to-its-binary-string-representation
    var b1 = (byte) oneByte;
    return String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
  }
}
