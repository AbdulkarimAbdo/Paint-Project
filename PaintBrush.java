package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class PaintBrush extends JFrame {
    private DrawingCanvas canvas;

    public PaintBrush() {
        setTitle("Paint Brush");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new DrawingCanvas();
        canvas.setBackground(Color.WHITE);

        // Create main toolbar
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());

        // Shape buttons panel
        JPanel shapesPanel = new JPanel();
        shapesPanel.setBorder(BorderFactory.createTitledBorder("Shapes"));
        String[] shapes = {"Rectangle", "Oval", "Line", "Polygon", "Freehand", "Eraser", "Star", "Triangle"};
        ButtonGroup shapeGroup = new ButtonGroup();
        for (String shape : shapes) {
            JToggleButton button = new JToggleButton(shape);
            button.addActionListener(e -> canvas.setCurrentTool(shape));
            shapeGroup.add(button);
            shapesPanel.add(button);
        }

        // Color selection panel
        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createTitledBorder("Colors"));

        // Advanced color picker
        JButton colorPicker = new JButton("Color Palette");
        colorPicker.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Color", canvas.getCurrentColor());
            if (newColor != null) {
                canvas.setCurrentColor(newColor);
            }
        });
        colorPanel.add(colorPicker);

        // Quick color buttons
        Color[] quickColors = {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA,
                Color.BLACK, Color.WHITE, Color.GRAY, Color.LIGHT_GRAY
        };

        for (Color color : quickColors) {
            JButton quickColorBtn = new JButton();
            quickColorBtn.setBackground(color);
            quickColorBtn.setPreferredSize(new Dimension(24, 24));
            quickColorBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            quickColorBtn.addActionListener(e -> canvas.setCurrentColor(color));
            colorPanel.add(quickColorBtn);
        }

        // Stroke width panel
        JPanel strokePanel = new JPanel();
        strokePanel.setBorder(BorderFactory.createTitledBorder("Stroke Width"));
        JSpinner strokeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        strokeSpinner.addChangeListener(e -> canvas.setStrokeWidth(((Number) strokeSpinner.getValue()).floatValue()));
        strokePanel.add(strokeSpinner);

        // Polygon sides panel
        JPanel polygonPanel = new JPanel();
        polygonPanel.setBorder(BorderFactory.createTitledBorder("Polygon Sides"));
        JSpinner sidesSpinner = new JSpinner(new SpinnerNumberModel(5, 3, 12, 1));
        sidesSpinner.addChangeListener(e -> canvas.setPolygonSides((Integer) sidesSpinner.getValue()));
        polygonPanel.add(sidesSpinner);

        // Style panel
        JPanel stylePanel = new JPanel();
        stylePanel.setBorder(BorderFactory.createTitledBorder("Style"));
        JCheckBox filledBox = new JCheckBox("Filled");
        filledBox.addActionListener(e -> canvas.setFilled(filledBox.isSelected()));
        JCheckBox dottedBox = new JCheckBox("Dotted");
        dottedBox.addActionListener(e -> canvas.setDotted(dottedBox.isSelected()));
        stylePanel.add(filledBox);
        stylePanel.add(dottedBox);

        // Add all panels to toolbar
        toolbar.add(shapesPanel);
        toolbar.add(colorPanel);
        toolbar.add(strokePanel);
        toolbar.add(polygonPanel);
        toolbar.add(stylePanel);

        // Add utility buttons
        JPanel utilityPanel = new JPanel();
        utilityPanel.setBorder(BorderFactory.createTitledBorder("Utilities"));

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> canvas.clearAll());

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> canvas.undo());

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> canvas.redo());  // Add action for redo

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    canvas.saveToFile(fileChooser.getSelectedFile());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
                }
            }
        });

        utilityPanel.add(clearButton);
        utilityPanel.add(undoButton);
        utilityPanel.add(redoButton);
        utilityPanel.add(saveButton);
        toolbar.add(utilityPanel);

        // Layout setup
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    // Main method moved to PaintBrush class
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaintBrush paintBrush = new PaintBrush();
            paintBrush.setVisible(true);
        });
    }
}
