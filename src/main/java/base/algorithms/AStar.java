package base.algorithms;

import base.CustomPriorityQueue;
import base.Node;
import base.data.AlgorithmData;
import base.TargetVector;
import base.Vector;
import java.util.*;

import static base.Helpers.delay;

public class AStar implements Algorithm{
    private static final int DELAY = 10000;
    private CustomPriorityQueue<Node> openSet;
    private HashMap<Node, Integer> costSoFar;
    private int traversedNodes;

    @Override
    public AlgorithmData run(Node[][] grid, Vector startNodePosition, List<TargetVector> targetNodePositions, boolean visualized) {
        openSet = new CustomPriorityQueue<>();
        costSoFar = new HashMap<>();

        int[] pathLength = new int[targetNodePositions.size()];
        boolean pathExistToAllTargets = true;
        Node startNode = grid[startNodePosition.getX()][startNodePosition.getY()];

        long executionTime = System.nanoTime();
        for(int i = 0; i < targetNodePositions.size(); i++) {
            pathLength[i] = visualized ? findOneTargetWithVisualization(grid, startNode,
                    targetNodePositions.get(i), i) : findOneTarget(grid, startNode,
                    targetNodePositions.get(i), i);
            if(pathLength[i] == -1) {
                pathExistToAllTargets = false;
                //System.out.println("No path to target");
                break;
            }
        }
        executionTime = (System.nanoTime() - executionTime) / 1000;

        if(pathExistToAllTargets) {
            AlgorithmData finalResult = new AlgorithmData(0,0);
            for(int nodesVisited : pathLength) {
                finalResult.setNodesVisited(finalResult.getNodesVisited() + nodesVisited);
            }
            finalResult.setExecutionTime(executionTime);
            return finalResult;
        }

        return null;
    }


    private int findOneTarget(Node[][] grid, Node startNode, Vector currentTarget, int currentTargetIndex) {
        // Reset data structures and variables
        reset(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            traversedNodes++;

            if(current == null) {
                throw new NullPointerException("Current node is null");
            }

            //if target
            if (current.getPosition().equalCoordinates(currentTarget)) {
                return traversedNodes;
            }

            // Check all neighbors of the current node.
            for (Node neighbor : getNeighbors(current, grid)) {
                if (costSoFar.containsKey(neighbor)) {
                    continue;
                }

                // tentativeGCost is the distance from the start node to the neighbor through the current node
                int tentativeGCost = costSoFar.get(current) + 1;

                if (!costSoFar.containsKey(neighbor) || tentativeGCost < costSoFar.get(neighbor)) {
                    int estimatedDistanceToTarget = neighbor.getEstimatedDistanceToTarget();
                    if (estimatedDistanceToTarget == 0 || neighbor.getTargetIndex() != currentTargetIndex) {
                        estimatedDistanceToTarget = calculateEstimatedDistanceToTarget(neighbor, currentTarget);
                        neighbor.setEstimatedDistanceToTarget(estimatedDistanceToTarget);
                    }
                    neighbor.setTargetIndex(currentTargetIndex);
                    neighbor.setParent(current);
                    costSoFar.put(neighbor, tentativeGCost);
                    neighbor.setTotalEstimatedCost(tentativeGCost + estimatedDistanceToTarget);
                    openSet.add(neighbor, neighbor.getTotalEstimatedCost());
                }
            }
        }
        return -1;
    }

    private int findOneTargetWithVisualization(Node[][] grid, Node startNode, TargetVector currentTarget,
                                               int currentTargetIndex) {
        reset(startNode);

        while (!openSet.isEmpty()) {
            delay(DELAY);
            Node current = openSet.poll();
            traversedNodes++;

            if(current == null) {
                throw new NullPointerException("Current node is null");
            }

            //if target
            if (current.getPosition().equalCoordinates(currentTarget)) {
                reconstructPath(current);
                return traversedNodes;
            }

            for (Node neighbor : getNeighbors(current, grid)) {
                if (costSoFar.containsKey(neighbor)) {
                    continue;
                }

                // tentativeGCost is the distance from the start node to the neighbor through the current node
                int tentativeGCost = costSoFar.get(current) + 1;

                if (!costSoFar.containsKey(neighbor) || tentativeGCost < costSoFar.get(neighbor)) {
                    int estimatedDistanceToTarget = neighbor.getEstimatedDistanceToTarget();
                    if (estimatedDistanceToTarget == 0 || neighbor.getTargetIndex() != currentTargetIndex) {
                        estimatedDistanceToTarget = calculateEstimatedDistanceToTarget(neighbor, currentTarget);
                        neighbor.setEstimatedDistanceToTarget(estimatedDistanceToTarget);
                    }
                    neighbor.setTargetIndex(currentTargetIndex);
                    neighbor.setParent(current);
                    costSoFar.put(neighbor, tentativeGCost);
                    neighbor.setTotalEstimatedCost(tentativeGCost + estimatedDistanceToTarget);
                    openSet.add(neighbor, neighbor.getTotalEstimatedCost());
                    if(neighbor.getType() != 2 && neighbor.getType() != 1) neighbor.setType(3);
                }
            }
        }
        return -1;
    }

    private void reset(Node startNode) {
        openSet.clear();
        traversedNodes = 0;
        costSoFar.clear();
        openSet.add(startNode, 0);
        costSoFar.put(startNode, 0);
    }

    private void reconstructPath(Node current) {
        Node parent = current.getParent();
        while (parent.getParent() != null) {
            parent.setType(2);
            parent = parent.getParent();
        }
    }

    private List<Node> getNeighbors(Node node, Node[][] grid) {
        List<Node> neighbors = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;
        int x = node.getPosition().getX();
        int y = node.getPosition().getY();

        // North
        if (y > 0 && node.getType() != 1) neighbors.add(grid[x][y - 1]);
        // South
        if (y < rows - 1 && node.getType() != 1) neighbors.add(grid[x][y + 1]);
        // West
        if (x > 0 && node.getType() != 1) neighbors.add(grid[x - 1][y]);
        // East
        if (x < cols - 1 && node.getType() != 1) neighbors.add(grid[x + 1][y]);

        return neighbors;
    }

    private int calculateEstimatedDistanceToTarget(Node node, Vector target) {
        return Math.abs(node.getPosition().getX() - target.getX()) + Math.abs(node.getPosition().getY() - target.getY());
    }

}
