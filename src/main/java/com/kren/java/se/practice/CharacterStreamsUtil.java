package com.kren.java.se.practice;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/*
  https://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
*/

public class CharacterStreamsUtil {

  static final int EOF_CHARACTER = -1;

  public static void readFileAsCharacters(File file) throws IOException {
    try (var reader = new FileReader(file)) {
      int character;
      while ((character = reader.read()) != EOF_CHARACTER) {
        System.out.print((char) character);
      }
    }
  }

  public static void writeCharactersToFile(Reader reader, File file) throws IOException {
    var writer = new FileWriter(file);
    try (reader; writer) {
      int i;
      while ((i = reader.read()) != EOF_CHARACTER) {
        writer.write(i);
      }
    }
  }
}
