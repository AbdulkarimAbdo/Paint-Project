package org.example;

import java.awt.*;

class OvalShape extends Shape {
    public OvalShape(Point start, Point end, Color color, boolean filled, boolean dotted, float strokeWidth) {
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

        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        if (filled) {
            g2d.fillOval(x, y, width, height);
        } else {
            g2d.drawOval(x, y, width, height);
        }
    }

    @Override
    public boolean contains(Point point) {
        return isPointInsideOval(startPoint, endPoint, point);
    }

    private boolean isPointInsideOval(Point p1, Point p2, Point point) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);

        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;

        double radiusX = width / 2.0;
        double radiusY = height / 2.0;

        double dx = point.x - centerX;
        double dy = point.y - centerY;
        double result = (dx * dx) / (radiusX * radiusX) + (dy * dy) / (radiusY * radiusY);

        return result <= 1;
    }
}
