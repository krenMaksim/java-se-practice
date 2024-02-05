package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/file.html
  https://jenkov.com/tutorials/java-nio/channels.html
*/

public class ByteNioUtil {

  private static final int END_OF_STREAM = -1;

  public static void readBytes(Path file) {
    readBytes(file, 48, buffer -> System.out.print((char) buffer.get()));
  }

  @SneakyThrows
  public static void readBytes(Path file, int bufferCapacityBytes, Consumer<ByteBuffer> bufferProcessor) {
    try (var byteChannel = Files.newByteChannel(file, StandardOpenOption.READ)) {
      var byteBuffer = ByteBuffer.allocate(bufferCapacityBytes);
      int bytesRead;
      while ((bytesRead = byteChannel.read(byteBuffer)) != END_OF_STREAM) {
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
          bufferProcessor.accept(byteBuffer);
        }
        byteBuffer.clear();
      }
    }
  }
}
