package org.example;

import java.awt.*;
import java.util.ArrayList;

class FreehandShape extends Shape {
    private ArrayList<Point> points;

    public FreehandShape(Point startPoint, Color color, boolean dotted, float strokeWidth) {
        super(startPoint, color, false, dotted, strokeWidth);
        points = new ArrayList<>();
        points.add(startPoint);
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);

        if (dotted) {
            float[] dash = {5.0f};
            g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        } else {
            g2d.setStroke(new BasicStroke(strokeWidth));
        }

        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public boolean contains(Point point) {
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            if (isPointNearLineSegment(p1, p2, point)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPointNearLineSegment(Point p1, Point p2, Point point) {
        final int tolerance = 5;

        double distance = distanceToLineSegment(p1, p2, point);
        return distance <= tolerance;
    }

    private double distanceToLineSegment(Point p1, Point p2, Point point) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double lengthSquared = dx * dx + dy * dy;

        if (lengthSquared == 0) {
            return p1.distance(point);
        }

        double t = ((point.x - p1.x) * dx + (point.y - p1.y) * dy) / lengthSquared;
        t = Math.max(0, Math.min(1, t));

        double closestX = p1.x + t * dx;
        double closestY = p1.y + t * dy;
        return point.distance(closestX, closestY);
    }
}
