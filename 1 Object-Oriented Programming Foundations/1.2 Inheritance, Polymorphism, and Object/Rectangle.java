import java.util.Objects;
import java.util.HashSet;

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

  public static void main(String[] args) {
    HashSet<Shape> shapes = new HashSet<Shape>();
    shapes.add(new Circle(5));
    shapes.add(new Circle(5));
    shapes.add(new Circle(6));
    shapes.add(new Rectangle(3,4));
    shapes.add(new Rectangle(2,2));
    shapes.add(new Rectangle(2,2));
    System.out.println(shapes.size());
  }
}

interface Shape {
  public double area();
}

final class Circle implements Shape  {
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

