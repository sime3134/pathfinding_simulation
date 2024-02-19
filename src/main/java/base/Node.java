package base;

public class Node {
    private final Vector position;

    // 0 = empty, 1 = obstacle, 2 = path, 3 = visited
    private int type;
    private Node parent;

    //For A* only
    private int distanceFromStart;
    private int totalEstimatedCost;

    public Node(int x, int y, int type) {
        this.position = new Vector(x, y);
        this.type = type;
    }

    public Vector getPosition() {
        return position;
    }

    public int getType() {
        return type;
    }

    public void setType(int i) {
        type = i;
    }

    public int getTotalEstimatedCost() {
        return totalEstimatedCost;
    }
    
    public void setTotalEstimatedCost(int fCost) {
        this.totalEstimatedCost = fCost;
    }
    
    public int getDistanceFromStart() {
        return distanceFromStart;
    }
    
    public void setDistanceFromStart(int gCost) {
        this.distanceFromStart = gCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
