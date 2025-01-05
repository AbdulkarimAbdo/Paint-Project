package org.example;

import java.awt.*;

abstract class Shape {
    protected Point startPoint;
    protected Point endPoint;
    protected Color color;
    protected boolean filled;
    protected boolean dotted;
    protected float strokeWidth;

    public Shape(Point start, Point end, Color color, boolean filled, boolean dotted, float strokeWidth) {
        this.startPoint = start;
        this.endPoint = end;
        this.color = color;
        this.filled = filled;
        this.dotted = dotted;
        this.strokeWidth = strokeWidth;
    }

    public Shape(Point start, Color color, boolean filled, boolean dotted, float strokeWidth) {
        this.startPoint = start;
        this.color = color;
        this.filled = filled;
        this.dotted = dotted;
        this.strokeWidth = strokeWidth;
        this.endPoint = start;
    }

    public abstract void draw(Graphics2D g2d);

    public abstract boolean contains(Point point);
}
