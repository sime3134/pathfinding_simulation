package base;

public class TargetVector extends Vector implements Comparable<TargetVector> {
    public TargetVector(int x, int y) {
        super(x, y);
    }

    public void setDistanceToStart(int distanceToStart) {
        this.distanceToStart = distanceToStart;
    }

    @Override
    public int compareTo(TargetVector o) {
        return Integer.compare(this.distanceToStart, o.distanceToStart);
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
