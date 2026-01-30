package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @Test
  void implementStaticFactoryMethod() {
    assertDoesNotThrow(() -> User3.newInstance(1, "Bob"));
  }

  @Test
  void tryOverrideGetter() {
    var user = new User4(1, "Bob");

    assertEquals("-Bob-", user.name());
  }

  @Test
  void addNewMethod() {
    var user = new User5(1, "Bob", "Dilan");

    assertEquals("Bob Dilan", user.fullName());
  }

  @Test
  void implementBuilder() {
    var user = new User6.Builder()
        .withId(1)
        .withName("Bob")
        .build();

    assertEquals(new User6(1, "Bob"), user);
  }

  @Test
  void useBuilderToCreateNullUser() {
    var user = new User6.Builder()
        .build();

    assertEquals(new User6(0, null), user);
  }

  @Test
  void useLombokBuilder() {
    var user = User7.builder()
        .id(1)
        .name("Bob")
        .build();

    assertEquals(new User7(1, "Bob"), user);
  }
}