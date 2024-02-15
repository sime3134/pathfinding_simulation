package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;

import java.util.*;

import static java.lang.Thread.sleep;

public class AStar implements Algorithm{

    @Override
    public ResultData run(Node[][] grid, Vector startNodePosition, List<Vector> targetNodePositions) {
        System.out.println("Running A*");
        int[] pathLength = new int[targetNodePositions.size()];
        boolean pathExistToAllTargets = true;
        Node startNode = grid[startNodePosition.getX()][startNodePosition.getY()];

        long executionTime = System.nanoTime();
        for(int i = 0; i < targetNodePositions.size(); i++) {
            pathLength[i] = findOneTarget(grid, startNode, targetNodePositions.get(i));
            if(pathLength[i] == -1) {
                pathExistToAllTargets = false;
                System.out.println("No path to target");
                break;
            }
        }
        executionTime = System.nanoTime() - executionTime;

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
        // Data structures
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFCost));
        HashSet<Node> closedSet = new HashSet<>();
        int traversedNodes = 0;

        startNode.setGCost(0); // The cost from the start to the start is 0
        startNode.setHCost(calculateHeuristic(startNode, currentTarget)); // Estimate cost to the current target
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Node current = openSet.poll();

            //if target
            if (current.getPosition().getX() == currentTarget.getX() && current.getPosition().getY() == currentTarget.getY()) {
                reconstructPath(current);
                resetNodes(current, closedSet, openSet);
                return traversedNodes;
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current, grid)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == 1) { // 1 = obstacle
                    continue;
                }

                int tentativeGCost = current.getGCost() + 1;

                if (tentativeGCost < neighbor.getGCost()) {
                    int hCost = calculateHeuristic(neighbor, currentTarget);
                    neighbor.setParent(current);
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setFCost(neighbor.getGCost() + hCost);

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

    private void reconstructPath(Node current) {
        Node parent = current.getParent();
        while (parent.getParent() != null) {
            parent.setType(2);
            parent = parent.getParent();
        }
    }

    private void resetNodes(Node current, HashSet<Node> closedSet, PriorityQueue<Node> openSet) {
        current.setParent(null);
        current.setGCost(Integer.MAX_VALUE);
        for(Node node : closedSet) {
            node.setParent(null);
            node.setGCost(Integer.MAX_VALUE);
        }
        for(Node node : openSet) {
            node.setParent(null);
            node.setGCost(Integer.MAX_VALUE);
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

    private int calculateHeuristic(Node node, Vector target) {
        return Math.abs(node.getPosition().getX() - target.getX()) + Math.abs(node.getPosition().getY() - target.getY());
    }

}
