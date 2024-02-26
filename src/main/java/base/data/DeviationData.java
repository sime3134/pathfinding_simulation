package base.data;

public class DeviationData {

    private final int scenarioId;
    private final double aStarExecutionTimeDeviation;
    private final double bfsExecutionTimeDeviation;
    private final double aStarNodesVisitedDeviation;
    private final double bfsNodesVisitedDeviation;

    public DeviationData(int scenarioId, double aStarExecutionTimeDeviation, double bfsExecutionTimeDeviation,
                         double aStarNodesVisitedDeviation, double bfsNodesVisitedDeviation) {
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
