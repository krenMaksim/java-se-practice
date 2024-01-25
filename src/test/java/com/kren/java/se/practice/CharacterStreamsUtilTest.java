package com.kren.java.se.practice;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CharacterStreamsUtilTest {

  @Test
  void readFileAsCharacters() {
    var file = Paths.get("src", "test", "resources", "some_file.txt").toFile();

    assertDoesNotThrow(() -> CharacterStreamsUtil.readFileAsCharacters(file));
  }

  @Test
  void writeCharactersToFile() {
    var bytes = new ByteArrayInputStream(new byte[] {100, 103, 15});
    var characters = new InputStreamReader(bytes);
    var file = Paths.get("target", "write_file.txt").toFile();

    assertDoesNotThrow(() -> CharacterStreamsUtil.writeCharactersToFile(characters, file));
  }

  @Test
  void appendCharactersToFile() {
    var bytes = new ByteArrayInputStream(new byte[] {100, 103, 15});
    var characters = new InputStreamReader(bytes);
    var file = Paths.get("target", "write_file.txt").toFile();

    assertDoesNotThrow(() -> CharacterStreamsUtil.appendCharactersToFile(characters, file));
  }
}