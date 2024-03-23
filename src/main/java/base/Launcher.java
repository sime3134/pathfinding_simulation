package base;

import base.data.DeviationData;
import base.data.ScenarioData;
import base.data.ScenarioGroupData;
import base.data.SimulationData;
import java.util.*;

public class Launcher {
    private static final int SIMULATIONS_PER_SCENARIO = 100000;
    private static int totalNumberOfSimulations = 0;
    private static final String FILE_NAME = "Scenarios2.txt";

    public static void main(String[] args) {
        System.out.println("Simulation Launched");
        ScenarioReader scenarioReader = new ScenarioReader();
        List<Scenario> scenarios = scenarioReader.readFromFile("src/main/resources/" + FILE_NAME );
        totalNumberOfSimulations = scenarios.size() * SIMULATIONS_PER_SCENARIO;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the BFS vs A* simulation");
        System.out.println("Press 1 to run a visualization of the simulation");
        System.out.println("Press 2 to run the simulation to get data");
        System.out.println("Press 3 exit the system");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1")) {
            runVisualized(scenarios);
        } else if (Objects.equals(input, "2")) {
            runData(scenarios);
        }
        else {
            System.out.println("Thank you, come again!");
        }
    }

    private static void runData(List<Scenario> scenarios) {
        HashMap<String, ScenarioGroupData> groups = new HashMap<>();
        int progressPercentage = 0;
        String currentGroup;
        List<DeviationData> deviationDataList = new ArrayList<>();

        for (int i = 0; i < scenarios.size(); i++) {
            ScenarioGroupData scenarioGroupData = getScenarioGroupData(groups, scenarios.get(i));
            ScenarioData scenarioData = new ScenarioData(scenarios.get(i));
            currentGroup = scenarios.get(i).getGroupName();
            System.out.println(scenarios.get(i));
            for(int j = 0; j < SIMULATIONS_PER_SCENARIO; j++) {
                Simulation currentSimulation = new Simulation(scenarios.get(i));
                SimulationData data = currentSimulation.run(false);
                if(data == null) {
                    j--;
                } else {
                    scenarioData.addSimulationData(data);
                }
                int newProgressPercentage =
                        (int) (((double) (i * SIMULATIONS_PER_SCENARIO + j) / totalNumberOfSimulations) * 100);
                if(newProgressPercentage > progressPercentage) {
                    progressPercentage = newProgressPercentage;
                    System.out.println(progressPercentage + "%");
                }
            }
            CSVWriter.writeSingleScenario(scenarioData);
            scenarioData.calculateAverages();
            DeviationData deviationData = scenarioData.standardDeviation();
            deviationDataList.add(deviationData);
            scenarioGroupData.addScenarioData(scenarioData);
            // If the next scenario is from a different group, write the current group data to a file
            if (i == scenarios.size() - 1 || !scenarios.get(i + 1).getGroupName().equals(currentGroup)) {
                CSVWriter.writeScenarioGroupData(scenarioGroupData);
            }
        }
        CSVWriter.writeDeviationData(deviationDataList);
    }

    private static void runVisualized(List<Scenario> scenarios) {
        Frame frame = new Frame();

        for (Scenario scenario : scenarios) {
            System.out.println(scenario);
            for(int i = 1; i <= SIMULATIONS_PER_SCENARIO; i++) {
                System.out.println("Simulation number: " + i);
                Simulation currentSimulation = new Simulation(scenario);
                frame.setCurrentSimulation(currentSimulation);
                SimulationData data = currentSimulation.run(true);
                if(data == null) {
                    i--;
                }
            }
        }
    }

    private static ScenarioGroupData getScenarioGroupData(HashMap<String, ScenarioGroupData> groups,
                                                          Scenario scenario) {
        if(groups.containsKey(scenario.getGroupName())) {
            return groups.get(scenario.getGroupName());
        } else {
            ScenarioGroupData scenarioGroupData = new ScenarioGroupData();
            groups.put(scenario.getGroupName(), scenarioGroupData);
            return scenarioGroupData;
        }
    }
}
