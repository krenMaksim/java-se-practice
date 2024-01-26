package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/bytestreams.html
*/

public class ByteStreamsUtil {

  static final int EOF_BYTE = -1;

  @SneakyThrows
  public static void readFileAsBytes(File file) {
    readFileAsBytes(new FileInputStream(file));
  }

  @SneakyThrows
  public static void readFileAsBytesViaBufferedStream(File file) {
    readFileAsBytes(new BufferedInputStream(new FileInputStream(file)));
  }

  @SneakyThrows
  private static void readFileAsBytes(InputStream input) {
    try (input) {
      System.out.println("Available bytes: " + input.available());
      int byteOfData;
      while ((byteOfData = input.read()) != EOF_BYTE) {
        System.out.print((char) byteOfData);
      }
    }
  }

  @SneakyThrows
  public static void writeBytesToFile(InputStream input, File file) {
    writeBytesToFile(input, new FileOutputStream(file, false));
  }

  @SneakyThrows
  public static void writeBytesToFileViaBufferedStream(InputStream input, File file) {
    writeBytesToFile(input, new BufferedOutputStream(new FileOutputStream(file, false)));
  }

  @SneakyThrows
  public static void appendBytesToFile(InputStream input, File file) {
    writeBytesToFile(input, new FileOutputStream(file, true));
  }

  @SneakyThrows
  public static void appendBytesToFileViaBufferedStream(InputStream input, File file) {
    writeBytesToFile(input, new BufferedOutputStream(new FileOutputStream(file, true)));
  }

  @SneakyThrows
  private static void writeBytesToFile(InputStream input, OutputStream output) {
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
