package base;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Launcher {

    private static final int SIMULATIONS_PER_SCENARIO = 1000;

    public static void main(String[] args) {
        System.out.println("base.Simulation Launched");
        ArrayList<Scenario> scenarios;
        Scanner scanner = new Scanner(System.in);
        boolean visualizationMode = false;
        System.out.println("Welcome to the motherfucking BFS vs A* simulation");
        System.out.println("Press 1 to run a visualization of the simulation");
        System.out.println("Press 2 to run the simulation to get hardcore data");
        System.out.println("Press 3 exit the system");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1")) {
            visualizationMode = true;
            Frame frame = new Frame();

            ScenarioReader scenarioReader = new ScenarioReader();
            scenarios = scenarioReader.readFromFile("src/main/resources/Scenarios.txt" );
            for (Scenario scenario : scenarios) {
                System.out.println(scenario.toString());
                for(int i = 1; i <= SIMULATIONS_PER_SCENARIO; i++) {
                    System.out.println("Simulation number: " + i);
                    Simulation currentSimulation = new Simulation(scenario);
                    frame.setCurrentSimulation(currentSimulation);
                    boolean validGrid = currentSimulation.run(visualizationMode);
                    if(!validGrid) {
                        i--;
                    }
                }
            }
        } else if (Objects.equals(input, "2")) {
            ScenarioReader scenarioReader = new ScenarioReader();
            scenarios = scenarioReader.readFromFile("src/main/resources/Scenarios.txt" );
            for (Scenario scenario : scenarios) {
                System.out.println(scenario.toString());
                for(int i = 0; i < SIMULATIONS_PER_SCENARIO; i++) {
                    Simulation currentSimulation = new Simulation(scenario);
                    currentSimulation.run(visualizationMode);
                }
            }

        }
        else {
            System.out.println("Thank you, come again!");
        }
    }
}
