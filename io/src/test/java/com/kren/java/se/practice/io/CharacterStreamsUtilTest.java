package com.kren.java.se.practice.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

class CharacterStreamsUtilTest {

  @Nested
  @TestInstance(PER_CLASS)
  class CharactersReader {

    private File file;

    @BeforeAll
    void setUp() {
      file = Paths.get("src", "test", "resources", "some_file.txt").toFile();
    }

    @Test
    @SneakyThrows
    void readFileAsCharacters() {
      var reader = new FileReader(file);

      assertDoesNotThrow(() -> CharacterStreamsUtil.readCharacters(reader));
    }

    @Test
    @SneakyThrows
    void readFileAsCharactersViaBufferedReader() {
      var reader = new BufferedReader(new FileReader(file));

      assertDoesNotThrow(() -> CharacterStreamsUtil.readCharacters(reader));
    }
  }

  @Nested
  class CharactersWriter {

    private File file;
    private InputStreamReader characters;

    @BeforeEach
    void setUp() {
      file = Paths.get("target", "write_file.txt").toFile();
      characters = new InputStreamReader(new ByteArrayInputStream(new byte[] {100, 103, 15}));
    }

    @Test
    @SneakyThrows
    void writeCharactersToFile() {
      var writer = new FileWriter(file);

      assertDoesNotThrow(() -> CharacterStreamsUtil.writeCharacters(characters, writer));
    }

    @Test
    @SneakyThrows
    void writeCharactersToFileViaBufferedStream() {
      var writer = new BufferedWriter(new FileWriter(file));

      assertDoesNotThrow(() -> CharacterStreamsUtil.writeCharacters(characters, writer));
    }

    @Test
    @SneakyThrows
    void appendCharactersToFile() {
      var writer = new FileWriter(file, true);

      assertDoesNotThrow(() -> CharacterStreamsUtil.writeCharacters(characters, writer));
    }

    @Test
    @SneakyThrows
    void appendCharactersToFileViaBufferedStream() {
      var writer = new BufferedWriter(new FileWriter(file, true));

      assertDoesNotThrow(() -> CharacterStreamsUtil.writeCharacters(characters, writer));
    }
  }
}