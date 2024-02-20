package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Launcher {

    private static final int SIMULATIONS_PER_SCENARIO = 1000;

    public static void main(String[] args) {
        System.out.println("base.Simulation Launched");
        ScenarioReader scenarioReader = new ScenarioReader();
        ArrayList<Scenario> scenarios = scenarioReader.readFromFile("src/main/resources/Scenarios.txt" );
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

    private static void runData(ArrayList<Scenario> scenarios, HashMap<Integer, ScenarioGroupData> groups) {
        for (Scenario scenario : scenarios) {
            ScenarioGroupData scenarioGroupData = getScenarioGroupData(groups, scenario);
            ScenarioData scenarioData = new ScenarioData(scenario);
            System.out.println(scenario);
            for(int i = 0; i < SIMULATIONS_PER_SCENARIO; i++) {
                Simulation currentSimulation = new Simulation(scenario);
                SimulationData data = currentSimulation.run(false);
                if(data == null) {
                    i--;
                } else {
                    scenarioData.addSimulationData(data);
                }
            }
            scenarioData.calculateAverages();
            scenarioGroupData.addScenarioData(scenarioData);
        }
        CSVWriter.write(groups);
    }

    private static void runVisualized(ArrayList<Scenario> scenarios) {
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
