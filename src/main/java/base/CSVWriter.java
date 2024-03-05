package base;

import base.data.DeviationData;
import base.data.ScenarioData;
import base.data.ScenarioGroupData;
import base.data.SimulationData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    private CSVWriter() {}

    public static void writeSingleScenario(ScenarioData scenarioData) {
        try {
            FileWriter writer =
                    new FileWriter("src/main/resources/single_scenarios/" + scenarioData.getScenario().getScenarioId() + ".csv", false);
            writer.append("A* Execution Time,BFS Execution Time,A* Nodes Visited,BFS Nodes Visited");
            writer.append("\n");
            for (SimulationData simulationData : scenarioData.getSimulationData()) {
                writer.append(String.valueOf(simulationData.getAStarData().getExecutionTime()));
                writer.append(",");
                writer.append(String.valueOf(simulationData.getBfsData().getExecutionTime()));
                writer.append(",");
                writer.append(String.valueOf(simulationData.getAStarData().getNodesVisited()));
                writer.append(",");
                writer.append(String.valueOf(simulationData.getBfsData().getNodesVisited()));
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeScenarioGroupData(ScenarioGroupData scenarioGroupData) {
        System.out.println("Writing scenario group data");
        String groupName = scenarioGroupData.getData().get(0).getScenario().getGroupName();
        try {
            FileWriter writer = new FileWriter("src/main/resources/scenario_groups/" + groupName + ".csv", false);
            writer.append("ScenarioId,").append(scenarioGroupData.getValueBeingComparedText()).append(",A* Execution ").append("Time,BFS ").append("Execution Time,").append("A* ").append("Nodes ").append("Visited,").append(
                    "BFS " +
                            "Nodes Visited");
            writer.append("\n");
            for (ScenarioData scenarioData : scenarioGroupData.getData()) {
                writer.append(String.valueOf(scenarioData.getScenario().getScenarioId()));
                writer.append(",");
                writer.append(String.valueOf(scenarioData.getScenario().getValueBeingCompared()));
                writer.append(",");
                writer.append(String.valueOf(scenarioData.getAverageAStarExecutionTime()));
                writer.append(",");
                writer.append(String.valueOf(scenarioData.getAverageBFSExecutionTime()));
                writer.append(",");
                writer.append(String.valueOf(scenarioData.getAverageAStarNodesVisited()));
                writer.append(",");
                writer.append(String.valueOf(scenarioData.getAverageBFSNodesVisited()));
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDeviationData(List<DeviationData> deviationDataList) {
        System.out.println("Writing deviation data");
        try {
            FileWriter writer = new FileWriter("src/main/resources/deviation_data/deviation_data.csv", false);
            writer.append("ScenarioId,A* Execution Time Deviation, BFS Execution time, A* Nodes Visited " +
                    "Deviation, BFS Nodes Visited Deviation");
            writer.append("\n");
            for (DeviationData deviationData : deviationDataList) {
                writer.append(String.valueOf(deviationData.getScenarioId()));
                writer.append(",");
                writer.append(String.valueOf(deviationData.getaStarExecutionTimeDeviation()));
                writer.append(",");
                writer.append(String.valueOf(deviationData.getBfsExecutionTimeDeviation()));
                writer.append(",");
                writer.append(String.valueOf(deviationData.getaStarNodesVisitedDeviation()));
                writer.append(",");
                writer.append(String.valueOf(deviationData.getBfsNodesVisitedDeviation()));
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}