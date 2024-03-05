package base.data;

import base.Scenario;

import java.util.ArrayList;
import java.util.List;

public class ScenarioData {
    private final List<SimulationData> data;
    private long averageAStarExecutionTime;
    private long averageBFSExecutionTime;
    private long averageAStarNodesVisited;
    private long averageBFSNodesVisited;

    private final Scenario scenario;

    public ScenarioData(Scenario scenario) {
        this.data = new ArrayList<>();
        this.scenario = scenario;
    }

    public void addSimulationData(SimulationData simulationData) {
        data.add(simulationData);
    }

    public void calculateAverages() {
        long totalAStarExecutionTime = 0;
        long totalBFSExecutionTime = 0;
        long totalAStarNodesVisited = 0;
        long totalBFSNodesVisited = 0;
        for (SimulationData simulationData : data) {
            totalAStarExecutionTime += simulationData.getAStarData().getExecutionTime();
            totalBFSExecutionTime += simulationData.getBfsData().getExecutionTime();
            totalAStarNodesVisited += simulationData.getAStarData().getNodesVisited();
            totalBFSNodesVisited += simulationData.getBfsData().getNodesVisited();
        }
        averageAStarExecutionTime = totalAStarExecutionTime / data.size();
        averageBFSExecutionTime = totalBFSExecutionTime / data.size();
        averageAStarNodesVisited = totalAStarNodesVisited / data.size();
        averageBFSNodesVisited = totalBFSNodesVisited / data.size();
    }

    public List<SimulationData> getSimulationData() {
        return data;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public long getAverageAStarExecutionTime() {
        return averageAStarExecutionTime;
    }

    public long getAverageBFSExecutionTime() {
        return averageBFSExecutionTime;
    }

    public long getAverageAStarNodesVisited() {
        return averageAStarNodesVisited;
    }

    public long getAverageBFSNodesVisited() {
        return averageBFSNodesVisited;
    }

    public DeviationData standardDeviation() {
        double aStarExecutionTimeVariance = 0;
        double bfsExecutionTimeVariance = 0;
        double aStarNodesVisitedVariance = 0;
        double bfsNodesVisitedVariance = 0;
        for (SimulationData simulationData : data) {
            aStarExecutionTimeVariance += Math.pow(simulationData.getAStarData().getExecutionTime() - (double) averageAStarExecutionTime, 2);
            bfsExecutionTimeVariance += Math.pow(simulationData.getBfsData().getExecutionTime() - (double) averageBFSExecutionTime, 2);
            aStarNodesVisitedVariance += Math.pow(simulationData.getAStarData().getNodesVisited() - (double) averageAStarNodesVisited, 2);
            bfsNodesVisitedVariance += Math.pow(simulationData.getBfsData().getNodesVisited() - (double) averageBFSNodesVisited, 2);
        }
        int aStarExecutionTimeStandardDeviation =
                (int) Math.round(Math.sqrt( aStarExecutionTimeVariance / data.size()));
        int bfsExecutionTimeStandardDeviation =
                (int) Math.round(Math.sqrt(bfsExecutionTimeVariance / data.size()));
        int aStarNodesVisitedStandardDeviation =
                (int) Math.round(Math.sqrt(aStarNodesVisitedVariance / data.size()));
        int bfsNodesVisitedStandardDeviation = (int) Math.round(Math.sqrt(bfsNodesVisitedVariance / data.size()));
        return new DeviationData(scenario.getScenarioId(), aStarExecutionTimeStandardDeviation,
                bfsExecutionTimeStandardDeviation,
                aStarNodesVisitedStandardDeviation, bfsNodesVisitedStandardDeviation);
    }
}
