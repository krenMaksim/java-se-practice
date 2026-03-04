package com.kren.java.se.practice.collections;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

class SetTest {

  @Test
  void checkHashSet() {
    Set<Integer> set = new HashSet<>();

    set.add(1);
    set.add(11);
    set.add(111);
    set.add(4);
    set.add(0);

    System.out.println(set);
  }

  @Test
  void checkLinkedHashSet() {
    Set<Integer> set = new LinkedHashSet<>();

    set.add(1);
    set.add(11);
    set.add(111);
    set.add(4);
    set.add(0);

    System.out.println(set);

    set.add(111);

    System.out.println(set);
  }

  @Test
  void checkEnumSet() {
    enum Numbers {
      ONE, TWO, THREE, FOUR, FIVE
    }

    Set<Numbers> set = EnumSet.noneOf(Numbers.class);

    set.add(Numbers.ONE);
    set.add(Numbers.FOUR);

    System.out.println(set);
  }
}