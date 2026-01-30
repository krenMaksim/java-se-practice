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

record User3(int id, String name) {

  // but still it is not possible to hide constructor to force factory method usage

  public static User3 newInstance(int id, String name) {
    return new User3(id, name);
  }
}

record User4(int id, String name) {

  public String name() {
    return String.format("-%s-", name);
  }
}

record User5(int id, String firstName, String lastName) {

  public String fullName() {
    return String.format("%s %s", firstName, lastName);
  }
}

record User6(int id, String name) {

  static class Builder {

    private int id;
    private String name;

    public Builder withId(int id) {
      this.id = id;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public User6 build() {
      return new User6(id, name);
    }
  }
}



