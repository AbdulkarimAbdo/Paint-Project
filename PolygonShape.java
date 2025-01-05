package org.example;

import java.awt.*;

class PolygonShape extends Shape {
    private int sides;

    public PolygonShape(Point start, Point end, Color color, boolean filled, boolean dotted, int sides, float strokeWidth) {
        super(start, end, color, filled, dotted, strokeWidth);
        this.sides = sides;
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

        int radius = (int) Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2));
        int[] xPoints = new int[sides];
        int[] yPoints = new int[sides];

        for (int i = 0; i < sides; i++) {
            double angle = Math.PI * 2 * i / sides;
            xPoints[i] = startPoint.x + (int) (radius * Math.cos(angle));
            yPoints[i] = startPoint.y + (int) (radius * Math.sin(angle));
        }

        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, sides);
        } else {
            g2d.drawPolygon(xPoints, yPoints, sides);
        }
    }

    @Override
    public boolean contains(Point point) {
        int radius = (int) Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2));
        int[] xPoints = new int[sides];
        int[] yPoints = new int[sides];

        for (int i = 0; i < sides; i++) {
            double angle = Math.PI * 2 * i / sides;
            xPoints[i] = startPoint.x + (int) (radius * Math.cos(angle));
            yPoints[i] = startPoint.y + (int) (radius * Math.sin(angle));
        }

        Polygon polygon = new Polygon(xPoints, yPoints, sides);
        return polygon.contains(point);
    }
}
