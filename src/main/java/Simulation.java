import static java.lang.Thread.sleep;

public class Simulation {
    private Node[][] grid;
    private Node startNode;
    private Node[] targetNodes;

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
                grid[i][j] = new Node(i, j);
            }
        }
    }

    public void generateObstacles(int percentage) {
        //generate random obstacles
    }

    public void generateStartNode() {
        startNode = grid[0][1];
    }

    public void generateTargetNodes(int numOfTargets, int nodesBetweenTargets, int nodesFromStart) {
        targetNodes = new Node[numOfTargets];
        for(int i = 0; i < numOfTargets; i++) {
            targetNodes[i] = grid[i*2+1][i*2+1];
        }
    }

    public void run () {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Algorithm currentAlgorithm = Algorithm.BFS;

        //run bfs algorithm
        //retrieve data

        currentAlgorithm = Algorithm.A_STAR;

        //run a* algorithm
        //retrieve data
    }

    public int getGridSize() {
        return grid.length;
    }

    public Node getNode(int row, int col) {
        return grid[row][col];
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node[] getTargetNodes() {
        return targetNodes;
    }
}
