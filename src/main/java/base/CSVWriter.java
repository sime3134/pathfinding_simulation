package base;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriter {
    public static void write(ScenarioGroupData group) {
        System.out.println("Writing to CSV");
        String groupName = group.getData().get(0).getScenario().getGroupName();
        writeScenarioGroupData(group, groupName);
    }

    private static void writeScenarioGroupData(ScenarioGroupData scenarioGroupData, String groupName) {
        try {
            FileWriter writer = new FileWriter("src/main/resources/" + groupName + ".csv", true);
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
}