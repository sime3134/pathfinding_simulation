package base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Simon Jern
 * The window frame that the simulations are displayed in.
 */
public class Frame extends JFrame {
    private final Canvas canvas;
    private Simulation currentSimulation;

    private final int frameSize = 768;

    public void setCurrentSimulation(Simulation newSimulation) {
        this.currentSimulation = newSimulation;
    }

    public Frame() {
        setTitle("Simulations");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);

        addListener();

        canvas = new Canvas();
        setPreferredSize(new Dimension(frameSize + 2, frameSize + 2));
        canvas.setPreferredSize(getContentPane().getPreferredSize());
        canvas.setFocusable(false);
        add(canvas);
        addKeyListener(Input.getInstance());
        pack();

        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }

    private void start() {
        Thread drawThread = new Thread(() -> {
            while (true) {  // A basic loop
                draw();

                try {
                    Thread.sleep(1); // Adjust sleep time as needed
                } catch (InterruptedException ex) {
                    // Handle the exception properly
                }
            }
        });
        drawThread.start();
    }

    private void addListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPreferredSize(e.getComponent().getSize());
                pack();
                canvas.setPreferredSize(getContentPane().getPreferredSize());
            }
        });
    }

    public void draw(){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.decode("#262626"));
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphics.setColor(Color.WHITE);
        //Draw grid, tiles etc.
        graphics.drawString("Press <space> to run all simulations actively or <enter> to run them one by one.", 50, 50);

        if (currentSimulation != null) {
            drawGrid(graphics);
            drawOther(graphics);
            drawStartNode(graphics);
            drawTargetNodes(graphics);
        }

        graphics.dispose();
        bufferStrategy.show();
    }

    private void drawGrid(Graphics graphics) {
        graphics.setColor(Color.decode("#D0D0D0"));
        int gridSize = currentSimulation.getGridSize();

        int cellSize = frameSize / gridSize;

        // Draw vertical lines
        for (int x = 0; x <= gridSize + 1; x++) {
            int lineX = x * cellSize;
            graphics.drawLine(lineX, 0, lineX, frameSize);
        }

        // Draw horizontal lines
        for (int y = 0; y <= gridSize + 1; y++) {
            int lineY = y * cellSize;
            graphics.drawLine(0, lineY, frameSize, lineY);
        }
    }

    private void drawOther(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        for (int row = 0; row < currentSimulation.getGridSize(); row++) {
            for (int col = 0; col < currentSimulation.getGridSize(); col++) {
                if (currentSimulation.getNode(row, col).getType() == 1) {
                    drawCell(graphics, row, col, Color.GRAY);
                }
                if (currentSimulation.getNode(row, col).getType() == 2) {
                    drawCell(graphics, row, col, Color.BLUE);
                }
                if (currentSimulation.getNode(row, col).getType() == 3) {
                    drawCell(graphics, row, col, Color.YELLOW);
                }
            }
        }
    }

    private void drawStartNode(Graphics graphics) {
        graphics.setColor(Color.decode("#006400"));
        Vector startPos = currentSimulation.getStartNodePosition();
        drawCell(graphics, startPos.getX(), startPos.getY(), Color.decode("#006400"));
    }

    private void drawTargetNodes(Graphics graphics) {
        graphics.setColor(Color.RED);
        for (Vector target : currentSimulation.getTargetNodePositions()) {
            drawCell(graphics, target.getX(), target.getY(), Color.RED);
        }
    }

    private void drawCell(Graphics graphics, int row, int col, Color baseColor) {
        int cellSize = (frameSize / currentSimulation.getGridSize());
        int x = col * cellSize;
        int y = row * cellSize;

        Graphics2D g2d = (Graphics2D) graphics;

        // Calculate Gradient Colors
        Color lighterColor = baseColor.brighter();
        Color darkerColor = baseColor.darker();

        GradientPaint gradient = new GradientPaint(
                x, y, lighterColor,
                x + cellSize, y + cellSize, darkerColor);

        g2d.setPaint(gradient);

        g2d.fillRect(x + 1, y + 1,
                cellSize - 1, cellSize - 1);
    }
}
