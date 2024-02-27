package com.kren.java.se.practice;

import lombok.SneakyThrows;

import java.io.Reader;
import java.io.Writer;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
*/

public class CharacterStreamsUtil {

  static final int EOF_CHARACTER = -1;

  @SneakyThrows
  public static void readCharacters(Reader reader) {
    try (reader) {
      int character;
      while ((character = reader.read()) != EOF_CHARACTER) {
        System.out.print((char) character);
      }
    }
  }

  @SneakyThrows
  public static void writeCharacters(Reader reader, Writer writer) {
    try (reader; writer) {
      int i;
      while ((i = reader.read()) != EOF_CHARACTER) {
        writer.write(i);
      }
    }
  }
}
