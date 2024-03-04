package base;

import base.algorithms.AStar;
import base.algorithms.Algorithm;
import base.algorithms.BFS;
import base.data.AlgorithmData;
import base.data.SimulationData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static base.Helpers.delay;

public class Simulation {
    private final Node[][] grid;
    private Vector startNodePos;
    private List<TargetVector> targetNodes;

    private final Random random = new Random();

    public Simulation(Scenario scenario) {
        grid = new Node[scenario.getGridSize()][scenario.getGridSize()];
        initiateGrid(true);
        generateObstacles(scenario.getObstaclePercentage());
        generateStartNode();
        generateTargetNodes(scenario.getNumOfTargets());
    }

    private void initiateGrid(boolean firstTime) {
    for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int type = 0;
                if(!firstTime) type = grid[i][j].getType();

                grid[i][j] = new Node(i, j, 0);
                if(type == 1 || type == 4 || type == 5) grid[i][j].setType(type);
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
        grid[startNodePos.getX()][startNodePos.getY()].setType(5);
    }

    public void generateTargetNodes(int numOfTargets) {
        targetNodes = new ArrayList<>();
        for(int i = 0; i < numOfTargets; i++) {
            TargetVector position = new TargetVector(random.nextInt(0, grid.length), random.nextInt(0, grid.length));
            if(position.equalCoordinates(startNodePos) || targetNodes.contains(position) || grid[position.getX()][position.getY()].getType() == 1){
                i--;
                continue;
            }
            int distanceToStart = Math.abs(position.getX() - startNodePos.getX()) + Math.abs(position.getY() - startNodePos.getY());
            position.setDistanceToStart(distanceToStart);
            targetNodes.add(position);
            grid[position.getX()][position.getY()].setType(4);
        }
        Collections.sort(targetNodes);
    }

    public SimulationData run (boolean visualizationMode) {

        Algorithm currentAlgorithm = new AStar();
        AlgorithmData algorithmDataAStar = currentAlgorithm.run(grid, startNodePos, targetNodes, visualizationMode);
        if(algorithmDataAStar == null) {
            return null;
        }
        if(visualizationMode) {
            System.out.println("A*:   " + algorithmDataAStar);
            delay(5000);
        }

        initiateGrid(false);

        currentAlgorithm = new BFS();
        AlgorithmData algorithmDataBFS = currentAlgorithm.run(grid, startNodePos, targetNodes, visualizationMode);
        if(algorithmDataBFS == null) {
            return null;
        }
        if(visualizationMode) {
            System.out.println("BFS:  " + algorithmDataBFS);
            delay(5000);
        }

        return new SimulationData(algorithmDataAStar, algorithmDataBFS);
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
