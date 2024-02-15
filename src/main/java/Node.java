public class Node {
    private int row, col;
    private boolean isObstacle;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        isObstacle = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isObstacle() {
        return isObstacle;
    }
}
