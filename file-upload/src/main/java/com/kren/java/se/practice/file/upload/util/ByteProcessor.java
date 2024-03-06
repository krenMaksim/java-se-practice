package com.kren.java.se.practice.file.upload.util;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class ByteProcessor {

  private final List<Number> data;

  public ByteProcessor() {
    data = new LinkedList<>();
  }

  public void handleByte(int byteOfData) {
    data.add(byteOfData);
  }

  public void handleBuffer(ByteBuffer buffer) {
    data.add(buffer.get());
  }

  public int getReceivedBytes() {
    return data.size();
  }
}
