package base;

public class Node {
    private final Vector position;

    // 0 = empty, 1 = obstacle, 2 = path, 3 = visited
    private int type;
    private Node parent;

    //For A* only
    private int distanceFromStart;
    private int totalEstimatedCost;

    private int estimatedDistanceToTarget;

    private int targetIndex;

    public Node(int x, int y, int type) {
        this.position = new Vector(x, y);
        this.type = type;
        this.estimatedDistanceToTarget = 0;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.targetIndex = 0;
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

    public int getEstimatedDistanceToTarget() {
        return estimatedDistanceToTarget;
    }

    public void setEstimatedDistanceToTarget(int hCost) {
        this.estimatedDistanceToTarget = hCost;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
