package base;

import java.util.*;

public class Launcher {

    private static final int SIMULATIONS_PER_SCENARIO = 10000;
    private static int totalNumberOfSimulations = 0;

    public static void main(String[] args) {
        System.out.println("base.Simulation Launched");
        ScenarioReader scenarioReader = new ScenarioReader();
        List<Scenario> scenarios = scenarioReader.readFromFile("src/main/resources/Scenarios.txt" );
        totalNumberOfSimulations = scenarios.size() * SIMULATIONS_PER_SCENARIO;
        HashMap<Integer, ScenarioGroupData> groups = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the motherfucking BFS vs A* simulation");
        System.out.println("Press 1 to run a visualization of the simulation");
        System.out.println("Press 2 to run the simulation to get hardcore data");
        System.out.println("Press 3 exit the system");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1")) {
            runVisualized(scenarios);
        } else if (Objects.equals(input, "2")) {
            runData(scenarios, groups);
        }
        else {
            System.out.println("Thank you, come again!");
        }
    }

    private static void runData(List<Scenario> scenarios, HashMap<Integer, ScenarioGroupData> groups) {
        int progressPercentage = 0;
        for (int i = 0; i < scenarios.size(); i++) {
            ScenarioGroupData scenarioGroupData = getScenarioGroupData(groups, scenarios.get(i));
            ScenarioData scenarioData = new ScenarioData(scenarios.get(i));
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
            scenarioData.calculateAverages();
            scenarioGroupData.addScenarioData(scenarioData);
        }
        CSVWriter.write(groups);
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

    private static ScenarioGroupData getScenarioGroupData(HashMap<Integer, ScenarioGroupData> groups,
                                                          Scenario scenario) {
        if(groups.containsKey(scenario.getGroupId())) {
            return groups.get(scenario.getGroupId());
        } else {
            ScenarioGroupData scenarioGroupData = new ScenarioGroupData();
            groups.put(scenario.getGroupId(), scenarioGroupData);
            return scenarioGroupData;
        }
    }
}
