package com.kren.java.se.practice.records;

record User(int id, String name, String address) {

  User {
    if (id <= 0) {
      throw new IllegalArgumentException("User id must be positive integer");
    }
  }

  User(int id, String name) {
    this(id, name, null);
  }
}
