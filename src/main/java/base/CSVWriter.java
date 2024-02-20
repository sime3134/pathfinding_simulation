package base;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriter {
    public static void write(Map<Integer, ScenarioGroupData> groups) {
        System.out.println("Writing to CSV");
        for (Integer key : groups.keySet()) {
            ScenarioGroupData scenarioGroupData = groups.get(key);
            writeScenarioGroupData(scenarioGroupData, key);
        }

    }

    private static void writeScenarioGroupData(ScenarioGroupData scenarioGroupData, int groupId) {
        try {
            FileWriter writer = new FileWriter("src/main/resources/scenario_group" + groupId + ".csv", true);
            writer.append("ScenarioId,AStarExecutionTime,BFSExecutionTime,AStarNodesVisited,BFSNodesVisited");
            writer.append("\n");
            for (ScenarioData scenarioData : scenarioGroupData.getData()) {
                writer.append(String.valueOf(scenarioData.getScenario().getScenarioId()));
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