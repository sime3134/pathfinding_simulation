package base;

import java.util.ArrayList;

public class Launcher {

    private static final int SIMULATIONS_PER_SCENARIO = 20;

    public static void main(String[] args) {
        System.out.println("base.Simulation Launched");
        ArrayList<Scenario> scenarios;
        Frame frame = new Frame();

        ScenarioReader scenarioReader = new ScenarioReader();
        scenarios = scenarioReader.readFromFile("src/main/resources/Scenarios.txt" );
        for (Scenario scenario : scenarios) {
            System.out.println(scenario.toString());
            for(int i = 0; i < SIMULATIONS_PER_SCENARIO; i++) {
                Simulation currentSimulation = new Simulation(scenario);
                currentSimulation.run();
                frame.setCurrentSimulation(currentSimulation);
            }
        }
    }
}
