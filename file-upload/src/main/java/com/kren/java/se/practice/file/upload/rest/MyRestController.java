package com.kren.java.se.practice.file.upload.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
  https://docs.spring.io/spring-framework/reference/core/resources.html
  https://spring.io/guides/gs/uploading-files
 */

@RestController
public class MyRestController {

  @GetMapping("/do-something")
  String doSomething() {
    return "hi";
  }
}
