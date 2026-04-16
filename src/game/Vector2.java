package game;

//Basic 2D vector (useful for future physics upgrades)
public class Vector2 {

 public double x, y;

 public Vector2(double x, double y) {
     this.x = x;
     this.y = y;
 }

 public void add(Vector2 other) {
     // Adds another vector to this one
     this.x += other.x;
     this.y += other.y;
 }

 public Vector2 multiply(double scalar) {
     // Scales the vector
     return new Vector2(x * scalar, y * scalar);
 }
}

