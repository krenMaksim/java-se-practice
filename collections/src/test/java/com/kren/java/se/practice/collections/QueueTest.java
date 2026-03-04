package com.kren.java.se.practice.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

class QueueTest {

  @Test
  void checkArrayDeque() {
    var deque = new ArrayDeque<String>();

    deque.size(); // it's strange that it has limit.
  }

  @Test
  void checkPriorityQueue() {
    var priorityQueue = new PriorityQueue<String>();

    priorityQueue.size();
  }
}