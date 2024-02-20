package base;

import java.util.ArrayList;
import java.util.List;

public class ScenarioData {
    private final List<SimulationData> data;
    private long averageAStarExecutionTime;
    private long averageBFSExecutionTime;
    private int averageAStarNodesVisited;
    private int averageBFSNodesVisited;

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
        int totalAStarNodesVisited = 0;
        int totalBFSNodesVisited = 0;
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

    public Scenario getScenario() {
        return scenario;
    }

    public long getAverageAStarExecutionTime() {
        return averageAStarExecutionTime;
    }

    public long getAverageBFSExecutionTime() {
        return averageBFSExecutionTime;
    }

    public int getAverageAStarNodesVisited() {
        return averageAStarNodesVisited;
    }

    public int getAverageBFSNodesVisited() {
        return averageBFSNodesVisited;
    }
}
