package base.data;

public class DeviationData {

    private final int scenarioId;
    private final long aStarExecutionTimeDeviation;
    private final long bfsExecutionTimeDeviation;
    private final long aStarNodesVisitedDeviation;
    private final long bfsNodesVisitedDeviation;

    public DeviationData(int scenarioId, long aStarExecutionTimeDeviation, long bfsExecutionTimeDeviation,
                         long aStarNodesVisitedDeviation, long bfsNodesVisitedDeviation) {
        this.scenarioId = scenarioId;
        this.aStarExecutionTimeDeviation = aStarExecutionTimeDeviation;
        this.bfsExecutionTimeDeviation = bfsExecutionTimeDeviation;
        this.aStarNodesVisitedDeviation = aStarNodesVisitedDeviation;
        this.bfsNodesVisitedDeviation = bfsNodesVisitedDeviation;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public double getaStarExecutionTimeDeviation() {
        return aStarExecutionTimeDeviation;
    }

    public double getaStarNodesVisitedDeviation() {
        return aStarNodesVisitedDeviation;
    }

    public double getBfsExecutionTimeDeviation() {
        return bfsExecutionTimeDeviation;
    }

    public double getBfsNodesVisitedDeviation() {
        return bfsNodesVisitedDeviation;
    }
}
