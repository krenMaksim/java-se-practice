package com.kren.java.se.practice.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

  private Path content;
  private Path targetFile;

  private long contentSizeBytes;

  @BeforeEach
  @SneakyThrows
  void setUp() {
    content = Paths.get("src", "test", "resources", "some_file.txt");
    targetFile = Paths.get("target", "target_file_api.txt");
    contentSizeBytes = Files.size(content);
  }

  @AfterEach
  @SneakyThrows
  void tearDown() {
    Files.deleteIfExists(targetFile);
  }

  @Test
  @SneakyThrows
  void readContentAsBytesInMemory() {
    var bytes = Files.readAllBytes(content);

    assertEquals(contentSizeBytes, bytes.length);
  }

  @Test
  @SneakyThrows
  void readContentAsLinesInMemory() {
    assertDoesNotThrow(() -> {
      Files.readAllLines(content)
          .stream()
          .forEach(System.out::println);
    });
  }

  @Test
  @SneakyThrows
  void readContentAsSingleString() {
    var str = Files.readString(content);

    assertEquals(contentSizeBytes, str.length());
  }

  @Test
  @SneakyThrows
  void writeContentAsBytesToFile() {
    var bytes = Files.readAllBytes(content);

    Files.write(targetFile, bytes);

    assertEquals(contentSizeBytes, Files.size(targetFile));
  }

  @Test
  @SneakyThrows
  void readContentAsInputStream() {
    var input = Files.newInputStream(content);

    assertDoesNotThrow(() -> ByteStreamsUtil.readInputStream(input));
  }

  @Test
  @SneakyThrows
  void readContentLineByLineViaStreamApi() {
    assertDoesNotThrow(() -> {
      Files.lines(content)
          .forEach(System.out::println);
    });
  }

  @Test
  @SneakyThrows
  void readContentLineByLineViaBufferedReader() {
    assertDoesNotThrow(() -> {
      try (var reader = Files.newBufferedReader(content)) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      }
    });
  }

  @Test
  @SneakyThrows
  void readAndWriteContentViaBufferedStreams() {
    var reader = Files.newBufferedReader(content);
    var writer = Files.newBufferedWriter(targetFile);

    CharacterStreamsUtil.writeCharacters(reader, writer);

    assertEquals(contentSizeBytes, Files.size(targetFile));
  }
}
