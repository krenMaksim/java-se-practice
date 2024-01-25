package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
*/

public class CharacterStreamsUtil {

  static final int EOF_CHARACTER = -1;

  @SneakyThrows
  public static void readFileAsCharacters(File file) {
    try (var reader = new FileReader(file)) {
      int character;
      while ((character = reader.read()) != EOF_CHARACTER) {
        System.out.print((char) character);
      }
    }
  }

  public static void writeCharactersToFile(Reader reader, File file) {
    writeCharactersToFile(reader, file, false);
  }

  public static void appendCharactersToFile(Reader reader, File file) {
    writeCharactersToFile(reader, file, true);
  }

  @SneakyThrows
  private static void writeCharactersToFile(Reader reader, File file, boolean append) {
    var writer = new FileWriter(file, append);
    try (reader; writer) {
      int i;
      while ((i = reader.read()) != EOF_CHARACTER) {
        writer.write(i);
      }
    }
  }
}
