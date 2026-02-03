package com.kren.java.se.practice.records;

interface Shape {

  // Given approach is chosen to use instanceof
  static double area(Shape shape) {
    if (shape instanceof Circle circle) {
      return Math.PI * Math.pow(circle.radius(), 2);
    } else if (shape instanceof Rectangle rectangle) {
      return rectangle.height() * rectangle.width();
    } else {
      throw new IllegalArgumentException("Unknown shape");
    }
  }
}

record Rectangle(double width, double height) implements Shape {
}

record Circle(double radius) implements Shape {
}
