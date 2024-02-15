package base;

public class Node {
    private final Vector position;

    // 0 = empty, 1 = obstacle, 2 = path, 3 = visited
    private int type;
    private Node parent;

    //For A* only
    private int gCost;
    private int hCost;
    private int fCost;

    public Node(int y, int x, int type) {
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

    public int getFCost() {
        return fCost;
    }
    
    public void setFCost(int fCost) {
        this.fCost = fCost;
    }
    
    public int getGCost() {
        return gCost;
    }
    
    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
