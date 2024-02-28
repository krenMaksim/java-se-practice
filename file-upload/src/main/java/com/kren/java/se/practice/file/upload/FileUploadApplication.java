package com.kren.java.se.practice.file.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileUploadApplication {

  /*
    java -XshowSettings
    java.io.tmpdir = C:\Users\MAKSIM~1\AppData\Local\Temp\
   */

  public static void main(String[] args) {
    SpringApplication.run(FileUploadApplication.class, args);
  }
}
