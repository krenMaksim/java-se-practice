package com.kren.java.se.practice.file.upload;

import java.util.LinkedList;
import java.util.List;

class ByteProcessor {

  private final List<Integer> data;

  public ByteProcessor() {
    data = new LinkedList<>();
  }

  public void handle(int byteOfData) {
    data.add(byteOfData);
  }

  public int getReceivedBytes() {
    return data.size();
  }
}
