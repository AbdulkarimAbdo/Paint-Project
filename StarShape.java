package org.example;

import java.awt.*;

class StarShape extends Shape {
    public StarShape(Point start, Point end, Color color, boolean filled, boolean dotted, float strokeWidth) {
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

        int radius = (int) Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2));
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];

        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 2 + Math.PI * 2 * i / 10;
            int r = (i % 2 == 0) ? radius : radius / 2;
            xPoints[i] = startPoint.x + (int) (r * Math.cos(angle));
            yPoints[i] = startPoint.y + (int) (r * Math.sin(angle));
        }

        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, 10);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 10);
        }
    }

    @Override
    public boolean contains(Point point) {
        int radius = (int) Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + Math.pow(endPoint.y - startPoint.y, 2));
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];

        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 2 + Math.PI * 2 * i / 10;
            int r = (i % 2 == 0) ? radius : radius / 2;
            xPoints[i] = startPoint.x + (int) (r * Math.cos(angle));
            yPoints[i] = startPoint.y + (int) (r * Math.sin(angle));
        }

        Polygon starPolygon = new Polygon(xPoints, yPoints, 10);

        return starPolygon.contains(point);
    }
}
