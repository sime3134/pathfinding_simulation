package base;

import base.algorithms.AStar;
import base.algorithms.Algorithm;
import base.algorithms.BFS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static base.Helpers.delay;

public class Simulation {
    private final Node[][] grid;
    private Vector startNodePos;
    private List<TargetVector> targetNodes;

    Random random = new Random();

    public Simulation(Scenario scenario) {
        grid = new Node[scenario.getGridSize()][scenario.getGridSize()];
        initiateGrid(true);
        generateObstacles(scenario.getObstaclePercentage());
        generateStartNode();
        generateTargetNodes(scenario.getNumOfTargets(), scenario.getNodesBetweenTargets());
    }

    private void initiateGrid(boolean firstTime) {
    for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int type = 0;
                if(!firstTime) type = grid[i][j].getType();

                grid[i][j] = new Node(i, j, 0);
                if(type == 1) grid[i][j].setType(type);
                grid[i][j].setDistanceFromStart(Integer.MAX_VALUE);
            }
        }
    }

    public void generateObstacles(double ratio) {
        int numberOfNodes = grid.length * grid[0].length;

        int numberOfObstacles = (int) (numberOfNodes * ratio);

        for(int i = 0; i < numberOfObstacles; i++) {
            int x = random.nextInt(0, grid.length);
            int y = random.nextInt(0, grid.length);
            if(grid[x][y].getType() == 1) {
                i--;
                continue;
            }
            grid[x][y].setType(1);
        }
    }

    public void generateStartNode() {
        //Randomize start node
        startNodePos = new Vector(random.nextInt(0, grid.length), random.nextInt(0, grid.length));
        if(grid[startNodePos.getX()][startNodePos.getY()].getType() == 1) {
            generateStartNode();
        }
    }

    public void generateTargetNodes(int numOfTargets, int nodesBetweenTargets) {
        targetNodes = new ArrayList<>();
        for(int i = 0; i < numOfTargets; i++) {
            TargetVector position = new TargetVector(random.nextInt(0, grid.length), random.nextInt(0, grid.length));
            if(position.equals(startNodePos) || targetNodes.contains(position) || grid[position.getX()][position.getY()].getType() == 1){
                i--;
                continue;
            }
            int distanceToStart = Math.abs(position.getX() - startNodePos.getX()) + Math.abs(position.getY() - startNodePos.getY());
            position.setDistanceToStart(distanceToStart);
            targetNodes.add(position);
        }
        Collections.sort(targetNodes);
    }

    public boolean run (boolean visualizationMode) {
        Algorithm currentAlgorithm = new AStar();
        ResultData resultData = currentAlgorithm.run(grid, startNodePos, targetNodes, visualizationMode);
        if(resultData == null) {
            return false;
        }
        System.out.println("A*:   " + resultData);
        if(visualizationMode) delay(2000);

        initiateGrid(false);

        currentAlgorithm = new BFS();
        ResultData resultDataBFS = currentAlgorithm.run(grid, startNodePos, targetNodes, visualizationMode);
        if(resultDataBFS == null) {
            return false;
        }
        System.out.println("BFS:   " + resultDataBFS);
        if(visualizationMode) delay(2000);

        //Do something with the data
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

    public List<TargetVector> getTargetNodePositions() {
        return targetNodes;
    }
}
