package com.kren.java.se.practice.file.upload;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileRestControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void uploadFile() {
    System.out.println("do something");
  }
}