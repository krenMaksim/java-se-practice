package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

  @Test
  void newUser() {
    var user = new User(1, "Bob", "New York");

    assertDoesNotThrow(() -> System.out.println(user.toString()));
  }

  @Test
  void validateId() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new User(-5, "Bob", "New York"));
  }

  // implement telescopic constructor

  // implement static factory method

  // implement builder

  // use lombok for builder
}