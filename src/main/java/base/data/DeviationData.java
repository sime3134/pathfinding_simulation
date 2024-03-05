package base.data;

public record DeviationData(int scenarioId, int aStarExecutionTimeDeviation, int bfsExecutionTimeDeviation,
                            int aStarNodesVisitedDeviation, int bfsNodesVisitedDeviation) {

}
