package com.kren.java.se.practice;

import java.io.FileInputStream;
import java.io.IOException;

class ReadSomeFileAsBytes {

  public static void main(String[] args) throws IOException {
    var filePath = getFilePath("some_file.txt");
    readFileAsBytes(filePath);
  }

  private static void readFileAsBytes(String filePath) throws IOException {
    try (var input = new FileInputStream(filePath)) {
      System.out.println("Available bytes: " + input.available());
      var i = input.read();
      while (i != -1) {
        System.out.print((char) i);
        i = input.read();
      }
    }
  }

  private static String getFilePath(String fileName) {
    return ReadSomeFileAsBytes.class.getClassLoader()
        .getResource(fileName)
        .getFile();
  }

  private static String toBinary(int readByte) {
    // https://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to-its-binary-string-representation
    var b1 = (byte) readByte;
    return String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
  }
}
