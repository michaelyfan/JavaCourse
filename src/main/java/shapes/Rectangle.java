package shapes;

import java.util.Objects;
import shapes.Shape;

public final class Rectangle implements Shape  {
  private int length;
  private int width;

  public Rectangle(int length, int width) {
    this.length = length;
    this.width = width;
  }

  @Override
  public double area() {
    return length * width;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    // TODO: use instanceof pattern-matching
    if (!(o instanceof Rectangle)) return false;
    Rectangle co = (Rectangle) o;
    return co.length == this.length && co.width == this.width;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.length, this.width);
  }

  @Override
  public String toString() {
    return String.format("Rectangle with length %s and width %s", this.length, this.width);
  }
}