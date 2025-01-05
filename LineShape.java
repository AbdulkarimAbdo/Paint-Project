package org.example;

import java.awt.*;

class LineShape extends Shape {
    public LineShape(Point start, Point end, Color color, boolean filled, boolean dotted, float strokeWidth) {
        super(start, end, color, filled, dotted, strokeWidth);
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

        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    @Override
    public boolean contains(Point point) {
        return isPointNearLineSegment(startPoint, endPoint, point);
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
