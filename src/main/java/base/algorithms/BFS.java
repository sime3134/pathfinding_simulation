package base.algorithms;

import base.Node;
import base.ResultData;
import base.TargetVector;
import base.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class BFS implements Algorithm {

    private static final int DELAY = 5;
    @Override
    public ResultData run(Node[][] grid, Vector startNode, List<TargetVector> targetNodes, boolean visualizationMode) {
        long startTime = System.nanoTime();
        int nodesVisited;

        if(visualizationMode) {
            nodesVisited = withVisualization(grid, startNode, targetNodes);
        } else {
            nodesVisited = noVisualization(grid, startNode, targetNodes);
        }
        long endTime = System.nanoTime();
        long executionTimeInMicros = (endTime - startTime) / 1000; // Convert to milliseconds
        return new ResultData(executionTimeInMicros, nodesVisited);
    }

    private int withVisualization(Node[][] grid, Vector startNode, List<TargetVector> targetNodes) {
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<Node> queue = new LinkedList<>(); // Queue for BFS exploration
        boolean[][] visited = new boolean[rows][cols];
        int nodesVisited = 1; //start node
        queue.add(grid[startNode.getX()][startNode.getY()]);
        visited[startNode.getX()][startNode.getY()] = true;

        List<TargetVector> foundTargets = new ArrayList<>(); // Store found target nodes
        while (!queue.isEmpty() && foundTargets.size() < targetNodes.size()) {
            Node current = queue.poll();
            current.setType(3);
            delay();
            nodesVisited = exploreNeighborsWithVisualization(grid, queue, visited, current, nodesVisited, foundTargets, targetNodes);
        }
        return nodesVisited;
    }

    private int exploreNeighborsWithVisualization(Node[][] grid, Queue<Node> queue, boolean[][] visited,
                                                  Node current, int nodesVisited, List<TargetVector> foundTargets,
                                                  List<TargetVector> targetNodes) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Directions (Up, Down, Left, Right)
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = current.getPosition().getX() + dx[i];
            int newY = current.getPosition().getY() + dy[i];

            nodesVisited = processNeighbor(grid, queue, visited, current, newX, newY, nodesVisited, foundTargets, targetNodes);
        }
        return nodesVisited;
    }

    private int processNeighbor(Node[][] grid, Queue<Node> queue, boolean[][] visited,
                                Node current, int newX, int newY, int nodesVisited,
                                List<TargetVector> foundTargets, List<TargetVector> targetNodes) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Check if valid move and not visited
        if (isValid(newX, newY, grid, rows, cols) && !visited[newX][newY]) {
            Node neighbour = grid[newX][newY];
            neighbour.setParent(grid[current.getPosition().getX()][current.getPosition().getY()]);
            queue.add(neighbour);
            visited[newX][newY] = true;
            grid[newX][newY].setType(3);
            nodesVisited++;
        }

        for (TargetVector target : targetNodes) {
            checkAndProcessTarget(grid, newX, newY, current, target, foundTargets);
        }

        return nodesVisited;
    }

    private void checkAndProcessTarget(Node[][] grid, int newX, int newY, Node current,
                                      TargetVector target, List<TargetVector> foundTargets) {
        if (newX == target.getX() && newY == target.getY()) {
            if (!foundTargets.contains(target)) {
                foundTargets.add(target);
                setParentType(grid, newX, newY, current);
            }
        }
    }

    private void setParentType(Node[][] grid, int x, int y, Node current) {
        Node parent = grid[x][y].getParent();
        current.setType(2);
        while (parent.getParent() != null) {
            grid[parent.getParent().getPosition().getX()][parent.getParent().getPosition().getY()].setType(2);
            parent = parent.getParent();
        }
    }

    private int noVisualization(Node[][] grid, Vector startNode, List<TargetVector> targetNodes) {
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
            exploreNeighbors(queue, grid, visited, current);
            nodesVisited++;

                for (Vector target : targetNodes) {
                    if (isTargetFound(current, target)) {
                        foundTargets.add(target);
                    }
                }
        }
        return nodesVisited;
    }

    private boolean isTargetFound(Node current, Vector target) {
        return current.getPosition().getX() == target.getX() && current.getPosition().getY() == target.getY();
    }

    private void exploreNeighbors(Queue<Node> queue, Node[][] grid, boolean[][] visited, Node current) {

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = current.getPosition().getX() + dx[i];
            int newY = current.getPosition().getY() + dy[i];

            if (isValid(newX, newY, grid, grid.length, grid[0].length) && !visited[newX][newY]) {
                Node neighbour = grid[newX][newY];
                neighbour.setParent(grid[current.getPosition().getX()][current.getPosition().getY()]);
                queue.add(neighbour);
                visited[newX][newY] = true;
            }
        }
    }

    private static void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(int x, int y, Node[][] grid, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y].getType() != 1 ;
    }
}