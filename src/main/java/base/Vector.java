package base;

public class Vector implements Comparable<Vector> {
    int x, y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Vector o) {
        if (this.x == o.x) {
            return Integer.compare(this.y, o.y);
        }
        return Integer.compare(this.x, o.x);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // Check if it's the same object reference
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) { // Check for null and class type
            return false;
        }
        Vector other = (Vector) obj; // Cast to Vector
        return this.x == other.x && this.y == other.y; // Compare x and y values
    }
}
