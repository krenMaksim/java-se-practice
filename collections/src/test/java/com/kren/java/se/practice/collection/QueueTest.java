package com.kren.java.se.practice.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

class QueueTest {

  @Test
  void checkArrayDeque() {
    var deque = new ArrayDeque<String>();

    deque.size();
  }

  @Test
  void checkPriorityQueue() {
    var priorityQueue = new PriorityQueue<String>();

    priorityQueue.size();
  }

  @Test
  void checkStack() {
    Deque<String> stack = new ArrayDeque<>();

    stack.push("ttt");
    stack.push("nnn");
    stack.push("ddd");

    while (!stack.isEmpty()) {
      System.out.println(stack.pollFirst());
    }
  }
}