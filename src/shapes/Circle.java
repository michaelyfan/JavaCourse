package shapes;

import java.util.Objects;
import shapes.Shape;

public final class Circle implements Shape  {
  private int radius;

  public Circle(int radius) {
    this.radius = radius;
  }

  @Override
  public double area() {
    return Math.PI * radius * radius;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    // TODO: use instanceof pattern-matching
    if (!(o instanceof Circle)) return false;
    Circle co = (Circle) o;
    return co.radius == this.radius;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.radius);
  }

  @Override
  public String toString() {
    return "Circle with radius: " + this.radius;
  }
}