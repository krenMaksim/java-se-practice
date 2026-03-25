package com.kren.java.se.practice.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

class ListTest {

  @Test
  void checkArrayList() {
    var arrayList = new ArrayList<String>();

    arrayList.size(); // see that Integer.MIN_VALUE is max number of elements
    arrayList.add(1, "434");
    arrayList.add("d");
    arrayList.set(4, "test");
  }

  @Test
  void checkLinkedList() {
    var linkedList = new LinkedList<String>();

    linkedList.size();
  }

  @Test
  void checkArrayDeque() {
    var deque = new ArrayDeque<String>();

    deque.size(); // it's strange that it has limit.
  }
}