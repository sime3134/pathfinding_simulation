public class Scenario {
    private int groupId;
    private int scenarioId;
    private int gridSize;
    private int obstaclePercentage;
    private int numOfTargets;
    private int nodesBetweenTargets;
    private int nodesFromStart;

    public Scenario(int groupId, int scenarioId, int gridSize, int obstaclePercentage, int numOfTargets, int nodesBetweenTargets, int nodesFromStart) {
        this.groupId = groupId;
        this.scenarioId = scenarioId;
        this.gridSize = gridSize;
        this.obstaclePercentage = obstaclePercentage;
        this.numOfTargets = numOfTargets;
        this.nodesBetweenTargets = nodesBetweenTargets;
        this.nodesFromStart = nodesFromStart;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getNodesFromStart() {
        return nodesFromStart;
    }

    public int getNodesBetweenTargets() {
        return nodesBetweenTargets;
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public int getObstaclePercentage() {
        return obstaclePercentage;
    }

    public int getScenarioId() {
        return scenarioId;
    }
}
