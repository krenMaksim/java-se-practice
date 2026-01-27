package com.kren.java.se.practice.records;

import java.util.List;

record User(int id, String name, String address, List<String> products) {

  User {
    if (id <= 0) {
      throw new IllegalArgumentException("User id must be positive integer");
    }
  }

  User(int id, String name) {
    this(id, name, null, List.of());
  }
}
