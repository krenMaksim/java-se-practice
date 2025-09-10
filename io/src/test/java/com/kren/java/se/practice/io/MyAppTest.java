package com.kren.java.se.practice.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MyAppTest {

  @Test
  void doSomething() {
    assertDoesNotThrow(MyApp::doSomething);
  }
}
