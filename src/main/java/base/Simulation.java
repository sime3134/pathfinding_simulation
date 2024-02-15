package base;

import base.algorithms.AStar;
import base.algorithms.BFS;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Simulation {
    private final Node[][] grid;
    private Vector startNodePos;
    private List<Vector> targetNodes;
    private static final boolean SLOW_MODE = true;

    public Simulation(Scenario scenario) {
        grid = new Node[scenario.getGridSize()][scenario.getGridSize()];
        initiateGrid();
        generateObstacles(scenario.getObstaclePercentage());
        generateStartNode();
        generateTargetNodes(scenario.getNumOfTargets(), scenario.getNodesBetweenTargets(), scenario.getNodesFromStart());
    }

    private void initiateGrid() {
    for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Node(i, j, 0);
                grid[i][j].setGCost(Integer.MAX_VALUE);
            }
        }
    }

    public void generateObstacles(int percentage) {
        //generate random obstacles
    }

    public void generateStartNode() {
        startNodePos = new Vector(0, 1);
    }

    public void generateTargetNodes(int numOfTargets, int nodesBetweenTargets, int nodesFromStart) {
        targetNodes = new ArrayList<>();
        for(int i = 0; i < numOfTargets; i++) {
            targetNodes.add(new Vector(i*5+3, i*5+3));
        }
    }

    public void run () {
        if(SLOW_MODE) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //AStar aStar = new AStar();
        //ResultData resultData = aStar.run(grid, startNodePos, targetNodes);

        //run bfs algorithm
        //retrieve data
        BFS bfs = new BFS();
        ResultData resultData1 = bfs.run(grid, startNodePos, targetNodes);

        //run a* algorithm
        //retrieve data
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
