package com.kren.java.se.practice.records;

import java.util.List;

record User1(int id, String name, String address) {

  User1 {
    if (id <= 0) {
      throw new IllegalArgumentException("User id must be positive integer");
    }
  }

  User1(int id, String name) {
    this(id, name, null);
  }
}

record User2(int id, List<String> products) {

  User2 {
    products = List.copyOf(products);
  }
}

