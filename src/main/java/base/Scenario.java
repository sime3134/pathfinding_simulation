package base;

public class Scenario {
    private final String groupName;
    private final int scenarioId;
    private final int gridSize;
    private final double obstaclePercentage;
    private final int numOfTargets;

    private final int valueBeingComparedIndex;

    public Scenario(String groupName, int scenarioId, int valueBeingComparedIndex, int gridSize,
                    double obstaclePercentage, int numOfTargets) {
        this.groupName = groupName;
        this.scenarioId = scenarioId;
        this.gridSize = gridSize;
        this.obstaclePercentage = obstaclePercentage;
        this.numOfTargets = numOfTargets;
        this.valueBeingComparedIndex = valueBeingComparedIndex;
    }

    public int getGridSize() {
        return gridSize;
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

    public String getGroupName() {
        return groupName;
    }

    public int getValueBeingCompared() {
        return switch (valueBeingComparedIndex) {
            case 0 -> gridSize;
            case 2 -> numOfTargets;
            case 1 -> (int) (obstaclePercentage * 100);
            default -> -1;
        };
    }

    public String getValueBeingComparedText() {
        return switch (valueBeingComparedIndex) {
            case 0 -> "Grid size";
            case 2 -> "Number of targets";
            case 1 -> "Obstacle percentage";
            default -> "Unknown";
        };
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "groupId=" + groupName +
                ", scenarioId=" + scenarioId +
                ", gridSize=" + gridSize +
                ", obstaclePercentage=" + obstaclePercentage +
                ", numOfTargets=" + numOfTargets +
                '}';
    }
}
