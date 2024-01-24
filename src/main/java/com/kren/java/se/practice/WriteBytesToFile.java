package com.kren.java.se.practice;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

class WriteBytesToFile {

  public static void main(String[] args) throws IOException {
    var bytes = new byte[] {100, 103, 15};
    var writeFile = Paths.get("target", "write_file.txt").toFile();

    try (var in = new ByteArrayInputStream(bytes);
         var out = new FileOutputStream(writeFile)) {
      int i;
      while ((i = in.read()) != -1) {
        out.write(i);
      }
    }
  }
}
