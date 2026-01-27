package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

  @Test
  void newUser() {
    var user = new User(1, "Bob", "New York", List.of("product 1", "product 2"));

    assertDoesNotThrow(() -> System.out.println(user));
  }

  @Test
  void implementFieldValidation() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new User(-5, "Bob", "New York", List.of("product 1", "product 2")));
  }

  @Test
  void implementTelescopicConstructor() {
    var user = new User(1, "Bob");

    assertDoesNotThrow(() -> System.out.println(user));
  }

  @Test
  void implementImmutableList() {

  }

  // add list field and implement defencive programming

  // implement telescopic constructor with lombok

  // implement static factory method

  // implement builder

  // use lombok for builder

  // try to override getter

  // try to add new method

  // try to make constructor private and leave only static factory method
}