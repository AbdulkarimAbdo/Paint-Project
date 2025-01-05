package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class DrawingCanvas extends JPanel {
    private ArrayList<Shape> shapes;
    private Shape currentShape;
    private Stack<ArrayList<Shape>> undoStack;
    private Stack<ArrayList<Shape>> redoStack;
    private String currentTool;
    private Color currentColor;
    private boolean filled;
    private boolean dotted;
    private Point startPoint;
    private float strokeWidth;
    private int polygonSides;

    public DrawingCanvas() {
        shapes = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        currentColor = Color.BLACK;
        currentTool = "Rectangle";
        strokeWidth = 1.0f;
        polygonSides = 5;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (currentTool.equals("Eraser")) {
                    for (int i = shapes.size() - 1; i >= 0; i--) {
                        if (shapes.get(i).contains(e.getPoint())) {
                            shapes.remove(i);
                            break;
                        }
                    }
                } else if (currentTool.equals("Freehand")) {
                    currentShape = new FreehandShape(startPoint, currentColor, dotted, strokeWidth);
                    shapes.add(currentShape);
                }
                saveState();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentTool.equals("Freehand") && currentShape != null) {
                    ((FreehandShape) currentShape).addPoint(e.getPoint());
                    currentShape = null;
                } else if (!currentTool.equals("Freehand") && !currentTool.equals("Eraser")) {
                    Shape newShape = null;
                    switch (currentTool) {
                        case "Rectangle":
                            newShape = new RectangleShape(startPoint, e.getPoint(), currentColor, filled, dotted, strokeWidth);
                            break;
                        case "Oval":
                            newShape = new OvalShape(startPoint, e.getPoint(), currentColor, filled, dotted, strokeWidth);
                            break;
                        case "Line":
                            newShape = new LineShape(startPoint, e.getPoint(), currentColor, filled, dotted, strokeWidth);
                            break;
                        case "Polygon":
                            newShape = new PolygonShape(startPoint, e.getPoint(), currentColor, filled, dotted, polygonSides, strokeWidth);
                            break;
                        case "Star":
                            newShape = new StarShape(startPoint, e.getPoint(), currentColor, filled, dotted, strokeWidth);
                            break;
                        case "Triangle":
                            newShape = new TriangleShape(startPoint, e.getPoint(), currentColor, filled, dotted, strokeWidth);
                            break;
                    }
                    if (newShape != null) {
                        shapes.add(newShape);
                    }
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentTool.equals("Freehand") && currentShape != null) {
                    ((FreehandShape) currentShape).addPoint(e.getPoint());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape shape : shapes) {
            shape.draw(g2d);
        }
    }

    private void saveState() {
        undoStack.push(new ArrayList<>(shapes));
        redoStack.clear();
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
    }

    public void setPolygonSides(int sides) {
        this.polygonSides = sides;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void saveToFile(File file) throws IOException {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        paint(image.getGraphics());
        ImageIO.write(image, "png", file);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new ArrayList<>(shapes));
            shapes = undoStack.pop();
            repaint();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new ArrayList<>(shapes));
            shapes = redoStack.pop();
            repaint();
        }
    }

    public void setCurrentTool(String tool) {
        this.currentTool = tool;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    public void clearAll() {
        shapes.clear();
        repaint();
    }
}
