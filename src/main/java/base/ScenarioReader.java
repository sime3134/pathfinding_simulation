package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioReader {
    private final List<Scenario> scenarios = new ArrayList<>();

    public List<Scenario> readFromFile(String textFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Scenario currentScenario = getScenario(values);
                scenarios.add(currentScenario);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + textFile);
            e.printStackTrace();
        }
        return scenarios;
    }

    private static Scenario getScenario(String[] values) {
        String groupName = values[0].trim();
        int scenarioId = Integer.parseInt(values[1].trim());
        int valueBeingCompared = Integer.parseInt(values[2].trim());
        int gridSize = Integer.parseInt(values[3].trim());
        double obstaclePercentage = Double.parseDouble(values[4].trim());
        int numOfTargets = Integer.parseInt(values[5].trim());

        return new Scenario(groupName, scenarioId, valueBeingCompared, gridSize, obstaclePercentage,
                numOfTargets);
    }
}
