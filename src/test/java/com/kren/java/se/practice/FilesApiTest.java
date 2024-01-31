package com.kren.java.se.practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/file.html
  https://www.baeldung.com/java-read-lines-large-file
 */

class FilesApiTest {

  private static final int FILE_SIZE_MB = 1;
  private static final int FILE_SIZE_BYTE = FILE_SIZE_MB * 1_000_000;

  private static Path file;

  @BeforeAll
  static void createFile() {
    file = Paths.get("target", "file_api.txt");
    DataGeneratorUtil.writeRandomBytes(file.toFile(), FILE_SIZE_MB);
  }

  @Test
  @SneakyThrows
  void readInMemory() {
    var bytes = Files.readAllBytes(file);

    assertEquals(FILE_SIZE_BYTE, bytes.length);
  }

  @Test
  @SneakyThrows
  void writeInMemory() {
    var bytes = Files.readAllBytes(file);
    var targetFile = Paths.get("target", "target_file_api.txt");

    Files.write(targetFile, bytes);

    assertEquals(bytes.length, Files.size(targetFile));
    Files.delete(targetFile);
  }

  @Test
  @SneakyThrows
  void createInputStream() {
    var someFile = Paths.get("src", "test", "resources", "some_file.txt");
    var input = Files.newInputStream(someFile);

    assertDoesNotThrow(() -> ByteStreamsUtil.readInputStream(input));
  }

  @Test
  @SneakyThrows
  void readFileLineByLineViaStream() {
    var someFile = Paths.get("src", "test", "resources", "some_file.txt");

    Files.lines(someFile)
        .forEach(System.out::println);
  }

  @Test
  @SneakyThrows
  void readFileLineByLineViaBufferedReader() {
    var someFile = Paths.get("src", "test", "resources", "some_file.txt");
    
    try (var reader = Files.newBufferedReader(someFile)) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }
  }

  @Test
  @SneakyThrows
  void inMemoryReadAllLines() {
    var someFile = Paths.get("src", "test", "resources", "some_file.txt");

    Files.readAllLines(someFile)
        .stream()
        .forEach(System.out::println);
  }

  @Test
  @SneakyThrows
  void readAllAsString() {
    var someFile = Paths.get("src", "test", "resources", "some_file.txt");

    var result = Files.readString(someFile);

    System.out.println(result);
  }

  @Test
  @SneakyThrows
  void readAndWriteCharactersViaBufferedStreams() {
    var someFile = Paths.get("src", "test", "resources", "some_file.txt");
    var targetFile = Paths.get("target", "target_file_api.txt");
    var input = Files.newBufferedReader(someFile);
    var output = Files.newBufferedWriter(targetFile);

    CharacterStreamsUtil.writeCharacters(input, output);

    assertEquals(Files.size(someFile), Files.size(targetFile));
    Files.delete(targetFile);
  }
}
