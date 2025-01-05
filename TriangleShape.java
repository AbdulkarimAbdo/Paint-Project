package org.example;

import java.awt.*;

class TriangleShape extends Shape {
    public TriangleShape(Point start, Point end, Color color, boolean filled, boolean dotted, float strokeWidth) {
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

        int[] xPoints = {startPoint.x, endPoint.x, (startPoint.x + endPoint.x) / 2};
        int[] yPoints = {startPoint.y, startPoint.y, endPoint.y};

        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    @Override
    public boolean contains(Point point) {
        int[] xPoints = {startPoint.x, endPoint.x, (startPoint.x + endPoint.x) / 2};
        int[] yPoints = {startPoint.y, startPoint.y, endPoint.y};

        Polygon triangle = new Polygon(xPoints, yPoints, 3);

        return triangle.contains(point);
    }
}
