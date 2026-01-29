package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

  @Test
  void newUser() {
    var user = new User1(1, "Bob", "New York");

    assertDoesNotThrow(() -> System.out.println(user));
  }

  @Test
  void implementFieldValidation() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new User1(-5, "Bob", "New York"));
  }

  @Test
  void implementTelescopicConstructor() {
    var user = new User1(1, "Bob");

    assertDoesNotThrow(() -> System.out.println(user));
  }

  @Test
  void implementImmutableList() {
    var mutableProducts = new ArrayList<>(List.of("product 1", "product 2"));
    var user = new User2(1, mutableProducts);

    var products = user.products();

    assertThrows(
        UnsupportedOperationException.class,
        () -> products.add("product 3"));
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