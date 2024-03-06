package com.kren.java.se.practice.file.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class FileUploadApplication {

  /*
    java -XshowSettings
    java.io.tmpdir = C:\Users\MAKSIM~1\AppData\Local\Temp\
   */

  /*
  Some commands

    curl POST -iSv 'http://localhost:8080/upload-file-form' \
  --form file=@/C:/Users/Maksim_Kren/Desktop/random_bytes_data.txt


  curl POST -iSv 'http://localhost:8080/upload-file-form' \
  --form file=@/C:/Users/Maksim_Kren/Documents/PersonalWorkspace/java-se-practice/io/target/random_bytes_data.txt


  curl POST --trace - --trace-time 'http://localhost:8080/upload-file-form' \
  --form file=@/C:/Users/Maksim_Kren/Desktop/random_bytes_data.txt



  curl POST --trace - --trace-time 'http://localhost:8080/upload-file-form' \
  --form file=@/C:/Users/Maksim_Kren/Documents/PersonalWorkspace/java-se-practice/io/target/random_bytes_data.txt


   */

  public static void main(String[] args) {
    SpringApplication.run(FileUploadApplication.class, args);
  }
}
