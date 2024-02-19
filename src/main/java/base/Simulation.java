package base;

import base.algorithms.AStar;
import base.algorithms.Algorithm;
import base.algorithms.BFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final Node[][] grid;
    private Vector startNodePos;
    private List<Vector> targetNodes;

    Random random = new Random();

    public Simulation(Scenario scenario) {
        grid = new Node[scenario.getGridSize()][scenario.getGridSize()];
        initiateGrid();
        generateObstacles(scenario.getObstaclePercentage());
        generateStartNode();
        generateTargetNodes(scenario.getNumOfTargets(), scenario.getNodesBetweenTargets());
    }

    private void initiateGrid() {
    for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Node(i, j, 0);
                grid[i][j].setDistanceFromStart(Integer.MAX_VALUE);
            }
        }
    }

    public void generateObstacles(int percentage) {
        //generate random obstacles
    }

    public void generateStartNode() {
        //Randomize start node
        startNodePos = new Vector(random.nextInt(0, grid.length), random.nextInt(0, grid.length));
    }

    public void generateTargetNodes(int numOfTargets, int nodesBetweenTargets) {
        targetNodes = new ArrayList<>();
        for(int i = 0; i < numOfTargets; i++) {
            Vector position = new Vector(random.nextInt(0, grid.length), random.nextInt(0, grid.length));
            if(position.equals(startNodePos) || targetNodes.contains(position)) {
                i--;
                continue;
            }
            targetNodes.add(position);
        }
    }

    public boolean run (boolean visualizationMode) {
            Algorithm currentAlgorithm = new AStar();
            ResultData resultData = currentAlgorithm.run(grid, startNodePos, targetNodes, visualizationMode);
            if(resultData == null) {
                return false;
            }
            System.out.println("A*:   " + resultData);

            initiateGrid();

            currentAlgorithm = new BFS();
            ResultData resultDataBFS = currentAlgorithm.run(grid, startNodePos, targetNodes, visualizationMode);
            if(resultDataBFS == null) {
                return false;
            }
            System.out.println("BFS:   " + resultDataBFS);
        return true;
        }

    public int getGridSize() {
        return grid.length;
    }

    public Node getNode(int row, int col) {
        return grid[row][col];
    }

    public Vector getStartNodePosition() {
        return startNodePos;
    }

    public List<Vector> getTargetNodePositions() {
        return targetNodes;
    }
}
