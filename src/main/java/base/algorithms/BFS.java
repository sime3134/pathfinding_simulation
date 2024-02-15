package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS implements Algorithm {
    @Override
    public ResultData run(Node[][] grid, Vector startNode, List<Vector> targetNodes) {
        long startTime = System.nanoTime();
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<Vector> queue = new LinkedList<>(); // Queue for BFS exploration
        boolean[][] visited = new boolean[rows][cols];
        int nodesVisited = 1; //start node

        queue.add(startNode);
        visited[startNode.getX()][startNode.getY()] = true;

        List<Vector> foundTargets = new ArrayList<>(); // Store found target nodes
        while (!queue.isEmpty() && foundTargets.size() != targetNodes.size() ) {
            Vector current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            if (foundTargets.size() != targetNodes.size()) {
                for (int x2 = -1; x2 <= 1; x2++) {
                    for (int y2 = -1; y2 <= 1; y2++) {
                        int newX = x + x2;
                        int newY = y + y2;

                        // Check if valid move and not visited
                        if (isValid(newX, newY, rows, cols) && !visited[newX][newY]) {
                            grid[newX][newY].setParent(grid[x][y]);
                            queue.add(new Vector(newX, newY));
                            visited[newX][newY] = true;
                            grid[newX][newY].setType(3);
                            nodesVisited++;
                        }
                        for (Vector target : targetNodes) {
                            if (newX == target.getX() && newY == target.getY()) {
                                foundTargets.add(new Vector(newX, newY));
                                Node parent = grid[newX][newY].getParent();
                                while(parent.getParent() != null) {
                                    grid[parent.getParent().getPosition().getX()][parent.getParent().getPosition().getY()].setType(2);
                                    parent = parent.getParent();
                                }
                            }
                        }
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        long executionTimeInMillis = (endTime - startTime) / 1000000; // Convert to milliseconds

        return new ResultData(executionTimeInMillis, nodesVisited);
    }

    private boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
}
