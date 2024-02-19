package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.util.*;

import static java.lang.Thread.sleep;

public class BFS implements Algorithm {
    @Override
    public ResultData run(Node[][] grid, Vector startNode, List<Vector> targetNodes) {
        long startTime = System.nanoTime();
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<Node> queue = new LinkedList<>(); // Queue for BFS exploration
        boolean[][] visited = new boolean[rows][cols];
        int nodesVisited = 1; //start node

        queue.add(grid[startNode.getX()][startNode.getY()]);
        visited[startNode.getX()][startNode.getY()] = true;

        List<Vector> foundTargets = new ArrayList<>(); // Store found target nodes
        while (!queue.isEmpty() && foundTargets.size() < targetNodes.size()) {

            Node current = queue.poll();
            current.setType(3);
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //int x = current.getX();
            //int y = current.getY();
                for (int x2 = -1; x2 <= 1; x2++) {
                    for (int y2 = -1; y2 <= 1; y2++) {
                        int newX = current.getPosition().getX() + x2;
                        int newY = current.getPosition().getY() + y2;

                        // Check if valid move and not visited
                        if (isValid(newX, newY, rows, cols) && !visited[newX][newY]) {
                            Node neighbour = grid[newX][newY];
                            neighbour.setParent(grid[current.getPosition().getX()][current.getPosition().getY()]);
                            queue.add(neighbour);
                            visited[newX][newY] = true;
                            grid[newX][newY].setType(3);
                            nodesVisited++;
                        }

                            for (Vector target : targetNodes) {
                                if (newX == target.getX() && newY == target.getY()) {
                                    if (!foundTargets.contains(target)) {
                                        foundTargets.add(target);
                                        Node parent = grid[newX][newY].getParent();
                                        current.setType(2);
                                        while (parent.getParent() != null) {
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
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResultData(executionTimeInMillis, nodesVisited);
    }

    private boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
}