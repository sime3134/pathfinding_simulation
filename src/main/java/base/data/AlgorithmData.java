package base.data;

public class AlgorithmData {
    private long executionTime;
    private int nodesVisited;

    public AlgorithmData(long executionTime, int nodesVisited) {
        this.executionTime = executionTime;
        this.nodesVisited = nodesVisited;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public int getNodesVisited() {
        return nodesVisited;
    }

    public void setNodesVisited(int nodesVisited) {
        this.nodesVisited = nodesVisited;
    }

    @Override
    public String toString() {
        return "Execution time: " + executionTime + "ms, Nodes visited: " + nodesVisited;
    }
}
