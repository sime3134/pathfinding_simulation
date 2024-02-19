package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;
import java.util.*;

public class AStar implements Algorithm{
    private static final int DELAY = 5;
    private PriorityQueue<Node> openSet;
    private HashSet<Node> closedSet;
    private int traversedNodes;

    @Override
    public ResultData run(Node[][] grid, Vector startNodePosition, List<Vector> targetNodePositions, boolean visualized) {
        int[] pathLength = new int[targetNodePositions.size()];
        boolean pathExistToAllTargets = true;
        Node startNode = grid[startNodePosition.getX()][startNodePosition.getY()];

        long executionTime = System.nanoTime();
        for(int i = 0; i < targetNodePositions.size(); i++) {
            pathLength[i] = visualized ? findOneTargetWithVisualization(grid, startNode, targetNodePositions.get(i)) : findOneTarget(grid, startNode, targetNodePositions.get(i));
            if(pathLength[i] == -1) {
                pathExistToAllTargets = false;
                System.out.println("No path to target");
                break;
            }
        }
        executionTime = (System.nanoTime() - executionTime) / 1000;

        if(pathExistToAllTargets) {
            ResultData finalResult = new ResultData(0,0);
            for(int nodesVisited : pathLength) {
                finalResult.setNodesVisited(finalResult.getNodesVisited() + nodesVisited);
            }
            finalResult.setExecutionTime(executionTime);
            return finalResult;
        }

        return null;
    }

    private int findOneTarget(Node[][] grid, Node startNode, Vector currentTarget) {
        // Reset data structures and variables
        reset();
        // Setup start node and target
        setup(startNode, currentTarget, openSet);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if(current == null) {
                throw new NullPointerException("Current node is null");
            }

            //if target
            if (current.getPosition().equals(currentTarget)) {
                resetNodes(current, closedSet, openSet);
                return traversedNodes;
            }

            closedSet.add(current);

            // Check all neighbors of the current node.
            for (Node neighbor : getNeighbors(current, grid)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == 1) { // 1 = obstacle
                    continue;
                }

                // tentativeGCost is the distance from the start node to the neighbor through the current node
                int tentativeGCost = current.getDistanceFromStart() + 1;

                if (tentativeGCost < neighbor.getDistanceFromStart()) {
                    int hCost = calculateEstimatedDistanceToTarget(neighbor, currentTarget);
                    neighbor.setParent(current);
                    neighbor.setDistanceFromStart(tentativeGCost);
                    neighbor.setTotalEstimatedCost(neighbor.getDistanceFromStart() + hCost);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        traversedNodes++;
                    }
                }
            }
        }
        return -1;
    }

    private int findOneTargetWithVisualization(Node[][] grid, Node startNode, Vector currentTarget) {
        // Data structures
        reset();

        setup(startNode, currentTarget, openSet);

        while (!openSet.isEmpty()) {
            delay();
            Node current = openSet.poll();

            if(current == null) {
                throw new NullPointerException("Current node is null");
            }

            //if target
            if (current.getPosition().equals(currentTarget)) {
                reconstructPath(current);
                resetNodes(current, closedSet, openSet);
                return traversedNodes;
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current, grid)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == 1) { // 1 = obstacle
                    continue;
                }

                int tentativeGCost = current.getDistanceFromStart() + 1;

                if (tentativeGCost < neighbor.getDistanceFromStart()) {
                    int estimatedDistanceToTarget = calculateEstimatedDistanceToTarget(neighbor, currentTarget);
                    neighbor.setParent(current);
                    neighbor.setDistanceFromStart(tentativeGCost);
                    neighbor.setTotalEstimatedCost(neighbor.getDistanceFromStart() + estimatedDistanceToTarget);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        if(neighbor.getType() != 2) neighbor.setType(3);
                        traversedNodes++;
                    }
                }
            }
        }
        return -1;
    }

    private void setup(Node startNode, Vector currentTarget, PriorityQueue<Node> openSet) {
        startNode.setDistanceFromStart(0); // The cost from the start to the start is 0
        openSet.add(startNode);
    }

    private void reset() {
        openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalEstimatedCost));
        closedSet = new HashSet<>();
        traversedNodes = 0;
    }

    private static void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void reconstructPath(Node current) {
        Node parent = current.getParent();
        while (parent.getParent() != null) {
            parent.setType(2);
            parent = parent.getParent();
        }
    }

    private void resetNodes(Node current, HashSet<Node> closedSet, PriorityQueue<Node> openSet) {
        current.setParent(null);
        current.setDistanceFromStart(Integer.MAX_VALUE);
        for(Node node : closedSet) {
            node.setParent(null);
            node.setDistanceFromStart(Integer.MAX_VALUE);
        }
        for(Node node : openSet) {
            node.setParent(null);
            node.setDistanceFromStart(Integer.MAX_VALUE);
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