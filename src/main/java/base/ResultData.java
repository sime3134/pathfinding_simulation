package base;

public class ResultData {
    private long executionTime;
    private int nodesVisited;

    public ResultData(long executionTime, int nodesVisited) {
        this.executionTime = executionTime;
        this.nodesVisited = nodesVisited;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getNodesVisited() {
        return nodesVisited;
    }
}
