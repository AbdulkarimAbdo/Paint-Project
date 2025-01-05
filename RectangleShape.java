package org.example;

import java.awt.*;

class RectangleShape extends Shape {
    public RectangleShape(Point start, Point end, Color color, boolean filled, boolean dotted, float strokeWidth) {
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
            g2d.fillRect(x, y, width, height);
        } else {
            g2d.drawRect(x, y, width, height);
        }
    }

    @Override
    public boolean contains(Point point) {
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        Rectangle rectangle = new Rectangle(x, y, width, height);
        return rectangle.contains(point);
    }
}
