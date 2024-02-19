package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ScenarioReader {
    private ArrayList<Scenario> scenarios = new ArrayList<>();
    public ScenarioReader() {
    }

    public ArrayList<Scenario> readFromFile(String textFile) {
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
        int groupId = Integer.parseInt(values[0].trim());
        int scenarioId = Integer.parseInt(values[1].trim());
        int gridSize = Integer.parseInt(values[2].trim());
        double obstaclePercentage = Double.parseDouble(values[3].trim());
        int numOfTargets = Integer.parseInt(values[4].trim());
        int nodesBetweenTargets = Integer.parseInt(values[5].trim());

        Scenario currentScenario = new Scenario(groupId, scenarioId, gridSize, obstaclePercentage,
                numOfTargets, nodesBetweenTargets);
        return currentScenario;
    }
}
