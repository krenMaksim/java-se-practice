package com.kren.java.se.practice.file.upload.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MyRestControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void doSomething() {
    String result = restTemplate.getForObject("/do-something", String.class);

    assertEquals("hi", result);
  }
}