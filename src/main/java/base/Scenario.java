package base;

public class Scenario {
    private final int groupId;
    private final int scenarioId;
    private final int gridSize;
    private final double obstaclePercentage;
    private final int numOfTargets;
    private final int nodesBetweenTargets;

    public Scenario(int groupId, int scenarioId, int gridSize, double obstaclePercentage, int numOfTargets, int nodesBetweenTargets) {
        this.groupId = groupId;
        this.scenarioId = scenarioId;
        this.gridSize = gridSize;
        this.obstaclePercentage = obstaclePercentage;
        this.numOfTargets = numOfTargets;
        this.nodesBetweenTargets = nodesBetweenTargets;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getNodesBetweenTargets() {
        return nodesBetweenTargets;
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public double getObstaclePercentage() {
        return obstaclePercentage;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    @Override
    public String toString() {
        return "base.Scenario{" +
                "groupId=" + groupId +
                ", scenarioId=" + scenarioId +
                ", gridSize=" + gridSize +
                ", obstaclePercentage=" + obstaclePercentage +
                ", numOfTargets=" + numOfTargets +
                ", nodesBetweenTargets=" + nodesBetweenTargets +
                '}';
    }
}
