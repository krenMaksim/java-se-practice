package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.function.Consumer;

public class ByteNioUtil {

  private static final int END_OF_STREAM = -1;

  public static void readBytes(Path file) {
    readBytes(file, 48, buffer -> System.out.print((char) buffer.get()));
  }

  @SneakyThrows
  public static void readBytes(Path file, int bufferCapacityBytes, Consumer<ByteBuffer> bufferProcessor) {
    var randomAccessFile = new RandomAccessFile(file.toFile(), "r");
    var fileChannel = randomAccessFile.getChannel();

    try (randomAccessFile; fileChannel) {
      ByteBuffer buf = ByteBuffer.allocate(bufferCapacityBytes);
      int bytesRead;
      while ((bytesRead = fileChannel.read(buf)) != END_OF_STREAM) {
        buf.flip();
        while (buf.hasRemaining()) {
          bufferProcessor.accept(buf);
        }
        buf.clear();
      }
    }
  }
}
