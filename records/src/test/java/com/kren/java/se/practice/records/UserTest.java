package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

  @Test
  void newUser() {
    var user = new User(1, "Bob", "New York");

    assertDoesNotThrow(() -> System.out.println(user));
  }

  @Test
  void implementFieldValidation() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new User(-5, "Bob", "New York"));
  }

  @Test
  void implementTelescopicConstructor() {
    var user = new User(1, "Bob");

    assertDoesNotThrow(() -> System.out.println(user));
  }

  // implement telescopic constructor with lombok

  // implement static factory method

  // implement builder

  // use lombok for builder

  // add list field and implement defencive programming
}