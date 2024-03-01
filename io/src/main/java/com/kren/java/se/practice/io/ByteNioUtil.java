package com.kren.java.se.practice.io;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/file.html
  https://jenkov.com/tutorials/java-nio/channels.html
  https://www.baeldung.com/java-filechannel
*/

public class ByteNioUtil {

  private static final int END_OF_STREAM = -1;

  public static void readBytes(Path file) {
    readBytes(file, 48, buffer -> System.out.print((char) buffer.get()));
  }

  @SneakyThrows
  public static void readBytes(Path file, int bufferCapacityBytes, Consumer<ByteBuffer> bufferProcessor) {
    readBytes(Files.newByteChannel(file, StandardOpenOption.READ), bufferCapacityBytes, bufferProcessor);
  }

  @SneakyThrows
  public static void readBytes(ReadableByteChannel byteChannel, int bufferCapacityBytes,
      Consumer<ByteBuffer> bufferProcessor) {
    try (byteChannel) {
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

  public static void writeBytesToFile(Path file, byte[] data) {
    writeBytes(file, data, StandardOpenOption.WRITE);
  }

  public static void appendBytesToFile(Path file, byte[] data) {
    writeBytes(file, data, StandardOpenOption.APPEND);
  }

  @SneakyThrows
  private static void writeBytes(Path file, byte[] data, OpenOption option) {
    try (var byteChannel = Files.newByteChannel(file, option)) {
      var byteBuffer = ByteBuffer.wrap(data);
      byteChannel.write(byteBuffer);
    }
  }

  @SneakyThrows
  public static void writeBytes(Path file, FileInputStream data) {
    var inputChannel = data.getChannel();
    var outputChannel = FileChannel.open(file, StandardOpenOption.WRITE);
    try (inputChannel; outputChannel) {
      outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    }
  }
}
