public class ScenarioGroup {
    private final int groupId;
    private final Scenario[] scenarios;

    public ScenarioGroup(int groupId, Scenario[] scenarios) {
        this.groupId = groupId;
        this.scenarios = scenarios;
    }

    public int getGroupId() {
        return groupId;
    }

    public Scenario[] getScenarios() {
        return scenarios;
    }

    public void runGroup() {
        for (Scenario scenario : scenarios) {
            Simulation simulation = new Simulation(scenario.getGridSize(), scenario.getObstaclePercentage(), scenario.getNumOfTargets(), scenario.getNodesBetweenTargets(), scenario.getNodesFromStart());
            simulation.run();
        }
    }
}
