package base.algorithms;

import base.Node;
import base.data.AlgorithmData;
import base.TargetVector;
import base.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class BFS implements Algorithm {

    private List<TargetVector> targetNodes;

    private int nodesVisited;

    private int foundTargets;

    private boolean[][] visited;

    private static final int DELAY = 100;
    @Override
    public AlgorithmData run(Node[][] grid, Vector startNode, List<TargetVector> targetNodes, boolean visualizationMode) {
        this.targetNodes = targetNodes;
        long startTime = System.nanoTime();
        nodesVisited = 0;
        foundTargets = 0;
        visited = new boolean[grid.length][grid[0].length];

        if(visualizationMode) {
            withVisualization(grid, startNode);
        } else {
            noVisualization(grid, startNode);
        }
        long endTime = System.nanoTime();
        long executionTimeInMicros = (endTime - startTime) / 1000; // Convert to milliseconds
        return new AlgorithmData(executionTimeInMicros, nodesVisited);
    }

    private void withVisualization(Node[][] grid, Vector startNode) {
        Queue<Node> queue = new LinkedList<>(); // Queue for BFS exploration
        queue.add(grid[startNode.getX()][startNode.getY()]);

        while (!queue.isEmpty() && foundTargets < targetNodes.size()) {
            Node current = queue.poll();
            visited[current.getPosition().getX()][current.getPosition().getY()] = true;
            nodesVisited++;
            if(current.getType() != 4 && current.getType() != 5) current.setType(3);
            delay();
            exploreNeighborsWithVisualization(grid, queue, current);
        }
    }

    private void exploreNeighborsWithVisualization(Node[][] grid, Queue<Node> queue,
                                                  Node current) {
        // 4 way
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = current.getPosition().getX() + dx[i];
            int newY = current.getPosition().getY() + dy[i];

            processNeighbor(grid, queue, current, newX, newY);
        }
    }

    private void processNeighbor(Node[][] grid, Queue<Node> queue,
                                Node current, int newX, int newY) {
        // Check if valid move and not visited
        if (isValid(newX, newY, grid) && !visited[newX][newY]) {
            Node neighbour = grid[newX][newY];
            visited[newX][newY] = true;
            neighbour.setParent(current);
            if (neighbour.getType() == 4) {
                foundTargets++;
                retractPath(current);
            } else {
                queue.add(neighbour);
            }
        }
    }

    private void retractPath(Node current) {
        if(current.getType() != 4 && current.getType() != 5) current.setType(2);
        Node parent = current.getParent();
        while (parent.getParent() != null) {
            if(parent.getType() != 4 && parent.getType() != 5) parent.setType(2);
            parent = parent.getParent();
        }
    }

    private void noVisualization(Node[][] grid, Vector startNode) {

        Queue<Node> queue = new LinkedList<>(); // Queue for BFS exploration
        queue.add(grid[startNode.getX()][startNode.getY()]);

        while (!queue.isEmpty() && foundTargets < targetNodes.size()) {
            Node current = queue.poll();
            visited[current.getPosition().getX()][current.getPosition().getY()] = true;
            nodesVisited++;
            exploreNeighbors(queue, grid, current);
        }
    }

    private void exploreNeighbors(Queue<Node> queue, Node[][] grid, Node current) {
        //4 way
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = current.getPosition().getX() + dx[i];
            int newY = current.getPosition().getY() + dy[i];

            if (isValid(newX, newY, grid) && !visited[newX][newY]) {
                Node neighbour = grid[newX][newY];
                visited[newX][newY] = true;
                if (neighbour.getType() == 4) {
                    foundTargets++;
                } else {
                    queue.add(neighbour);
                }
                neighbour.setParent(current);
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

    private boolean isValid(int x, int y, Node[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y].getType() != 1 ;
    }
}