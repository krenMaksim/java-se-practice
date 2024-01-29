package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.IntConsumer;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/bytestreams.html
*/

public class ByteStreamsUtil {

  static final int EOF_BYTE = -1;

  public static void readInputStream(InputStream input) {
    readInputStream(input, byteOfData -> System.out.print((char) byteOfData));
  }

  @SneakyThrows
  public static void readInputStream(InputStream input, IntConsumer byteProcessor) {
    try (input) {
      int byteOfData;
      while ((byteOfData = input.read()) != EOF_BYTE) {
        byteProcessor.accept(byteOfData);
      }
    }
  }

  @SneakyThrows
  public static void writeInputToOutput(InputStream input, OutputStream output) {
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
