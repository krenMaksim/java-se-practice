package com.kren.java.se.practice.records.shape;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShapeTest {

  @Test
  void calculateAreaForCircle() {
    var circle = new Circle(5);

    var area = Shape.area(circle);

    assertEquals(78.53981633974483, area);
  }

  @Test
  void calculateAreaForRectangle() {
    var rectangle = new Rectangle(5, 10);

    var area = Shape.area(rectangle);

    assertEquals(50, area);
  }

  @Test
  void calculateAreaForUnknownShape() {
    record Square(double side) implements Shape {}
    var square = new Square(10);

    assertThrows(
        IllegalArgumentException.class,
        () -> Shape.area(square));
  }
}