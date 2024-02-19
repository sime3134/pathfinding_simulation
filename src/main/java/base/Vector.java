package base;

public class Vector {
    int x, y;

    int distanceToStart;

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
    public boolean equals(Object obj) {
        if (this == obj) { // Check if it's the same object reference
            return true;
        }
        if (obj == null) { // Check for null and class type
            return false;
        }
        Vector other = (Vector) obj; // Cast to Vector
        return this.x == other.x && this.y == other.y; // Compare x and y values
    }
}
