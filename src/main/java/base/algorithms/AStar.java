package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar implements Algorithm{

    @Override
    public ResultData run(Node[][] grid, Vector startNodePosition, List<Vector> targetNodePositions) {
        boolean pathExistToAllTargets = true;
        Node startNode = grid[startNodePosition.getX()][startNodePosition.getY()];

        for(Vector currentTarget : targetNodePositions) {
            if(!findOneTarget(grid, startNode, currentTarget)) {
                pathExistToAllTargets = false;
                break;
            }

        }

        return new ResultData(1, 1);
    }

    private boolean findOneTarget(Node[][] grid, Node startNode, Vector currentTarget) {
        // Data structures
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFCost));
        List<Node> closedSet = new ArrayList<>();

        startNode.setGCost(0); // The cost from the start to the start is 0
        startNode.setHCost(calculateHeuristic(startNode, currentTarget)); // Estimate cost to the current target
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            //if target
            if (current.getPosition().getX() == currentTarget.getX() && current.getPosition().getY() == currentTarget.getY()) {
                Node parent = current.getParent();
                while (parent.getParent() != null) {
                    parent.setType(2);
                    parent = parent.getParent();
                }
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
                return true;
            }

            closedSet.add(current);
            current.setType(3);

            for (Node neighbor : getNeighbors(current, grid)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == 1) { // 1 = obstacle
                    continue;
                }

                int tentativeGCost = current.getGCost() + 1;

                if (tentativeGCost < neighbor.getGCost()) {
                    neighbor.setParent(current);
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setHCost(calculateHeuristic(neighbor, currentTarget));
                    neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return false;
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

    private int calculateMovementCost(Node current, Node neighbor) {
        int dx = Math.abs(current.getPosition().getX() - neighbor.getPosition().getX());
        int dy = Math.abs(current.getPosition().getY() - neighbor.getPosition().getY());

        if (dx == 1 && dy == 1) {
            // Diagonal movement
            return 14; // Assuming diagonal movement cost is higher than cardinal movement (1)
        } else {
            // Cardinal movement
            return 10;
        }
    }

}
