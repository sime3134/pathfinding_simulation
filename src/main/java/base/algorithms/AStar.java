package base.algorithms;

import base.Node;
import base.AlgorithmData;
import base.TargetVector;
import base.Vector;
import java.util.*;

import static base.Helpers.delay;

public class AStar implements Algorithm{
    private static final int DELAY = 5;
    private PriorityQueue<Node> openSet;
    private HashSet<Node> closedSet;
    private int traversedNodes;

    @Override
    public AlgorithmData run(Node[][] grid, Vector startNodePosition, List<TargetVector> targetNodePositions, boolean visualized) {
        openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalEstimatedCost));
        closedSet = new HashSet<>();

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
        reset(startNode, openSet);

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

            closedSet.add(current);

            // Check all neighbors of the current node.
            for (Node neighbor : getNeighbors(current, grid)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == 1) { // 1 = obstacle
                    continue;
                }
                if(neighbor.getTargetIndex() != currentTargetIndex) {
                    neighbor.setDistanceFromStart(Integer.MAX_VALUE);
                }

                // tentativeGCost is the distance from the start node to the neighbor through the current node
                int tentativeGCost = current.getDistanceFromStart() + 1;

                if (tentativeGCost < neighbor.getDistanceFromStart()) {
                    int estimatedDistanceToTarget = neighbor.getEstimatedDistanceToTarget();
                    if (estimatedDistanceToTarget == 0 || neighbor.getTargetIndex() != currentTargetIndex) {
                        estimatedDistanceToTarget = calculateEstimatedDistanceToTarget(neighbor, currentTarget);
                        neighbor.setEstimatedDistanceToTarget(estimatedDistanceToTarget);
                    }
                    neighbor.setTargetIndex(currentTargetIndex);
                    neighbor.setParent(current);
                    neighbor.setDistanceFromStart(tentativeGCost);
                    neighbor.setTotalEstimatedCost(neighbor.getDistanceFromStart() + estimatedDistanceToTarget);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return -1;
    }

    private int findOneTargetWithVisualization(Node[][] grid, Node startNode, TargetVector currentTarget,
                                               int currentTargetIndex) {
        reset(startNode, openSet);

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

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current, grid)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == 1) { // 1 = obstacle
                    continue;
                }
                if(neighbor.getTargetIndex() != currentTargetIndex) {
                    neighbor.setDistanceFromStart(Integer.MAX_VALUE);
                }

                int tentativeGCost = current.getDistanceFromStart() + 1;

                if (tentativeGCost < neighbor.getDistanceFromStart()) {
                    int estimatedDistanceToTarget = neighbor.getEstimatedDistanceToTarget();
                    if (estimatedDistanceToTarget == 0 || neighbor.getTargetIndex() != currentTargetIndex) {
                        estimatedDistanceToTarget = calculateEstimatedDistanceToTarget(neighbor, currentTarget);
                        neighbor.setEstimatedDistanceToTarget(estimatedDistanceToTarget);
                    }
                    neighbor.setTargetIndex(currentTargetIndex);
                    neighbor.setParent(current);
                    neighbor.setDistanceFromStart(tentativeGCost);
                    neighbor.setTotalEstimatedCost(neighbor.getDistanceFromStart() + estimatedDistanceToTarget);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        if(neighbor.getType() != 2) neighbor.setType(3);
                    }
                }
            }
        }
        return -1;
    }

    private void reset(Node startNode, PriorityQueue<Node> openSet) {
        openSet.clear();
        closedSet.clear();
        traversedNodes = 0;
        startNode.setDistanceFromStart(0); // The cost from the start to the start is 0
        openSet.add(startNode);
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

        // Check cardinal directions
        if (node.getPosition().getY() > 0) neighbors.add(grid[node.getPosition().getX()][node.getPosition().getY() - 1]);
        if (node.getPosition().getY() < rows - 1) neighbors.add(grid[node.getPosition().getX()][node.getPosition().getY() + 1]);
        if (node.getPosition().getX() > 0) neighbors.add(grid[node.getPosition().getX() - 1][node.getPosition().getY()]);
        if (node.getPosition().getX() < cols - 1) neighbors.add(grid[node.getPosition().getX() + 1][node.getPosition().getY()]);

        return neighbors;
    }

    private int calculateEstimatedDistanceToTarget(Node node, Vector target) {
        return Math.abs(node.getPosition().getX() - target.getX()) + Math.abs(node.getPosition().getY() - target.getY());
    }

}
