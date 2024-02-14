
public class Simulation {
    private Node[][] grid;
    private Node startNode;
    private Node[] targetNodes;

    public Simulation(int gridSize, int obstaclePercentage, int numOfTargets, int nodesBetweenTargets,
                      int nodesFromStart) {
        grid = new Node[gridSize][gridSize];
        generateObstacles(obstaclePercentage);
        generateStartNode();
        generateTargetNodes(numOfTargets, nodesBetweenTargets, nodesFromStart);
    }

    public void generateObstacles(int percentage) {
        //generate random obstacles
    }

    public void generateStartNode() {
        //generate random startnode
    }

    public void generateTargetNodes(int numOfTargets, int nodesBetweenTargets, int nodesFromStart) {
        //generate random targetnodes
    }

    public void run () {
        Algorithm currentAlgorithm = Algorithm.BFS;

        //run bfs algorithm
        //retrieve data

        currentAlgorithm = Algorithm.A_STAR;

        //run a* algorithm
        //retrieve data
    }
}
