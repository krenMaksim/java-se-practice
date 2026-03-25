package com.kren.java.se.practice.map;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

class MapTest {

  @Test
  void tryEnumMap() {
    enum Numbers {
      ONE, TWO, THREE, FOUR, FIVE
    }

    Map<Numbers, Integer> map = new EnumMap<>(Numbers.class);
    map.put(Numbers.TWO, 2);
    map.put(Numbers.THREE, 3);

    System.out.println(map);
  }
}
