package ui;

import model.Function;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartPanel extends JPanel {
    private double[] x, y;
    private List<Function> funcs;
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN.darker(), Color.MAGENTA, Color.ORANGE, Color.CYAN.darker()};
    private static final int MARGIN = 50;

    public void setData(double[] x, double[] y, List<Function> funcs) {
        this.x = x;
        this.y = y;
        this.funcs = funcs;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (x == null || funcs == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        double xmin = x[0], xmax = x[0], ymin = y[0], ymax = y[0];
        for (int i = 1; i < x.length; i++) {
            xmin = Math.min(xmin, x[i]);
            xmax = Math.max(xmax, x[i]);
            ymin = Math.min(ymin, y[i]);
            ymax = Math.max(ymax, y[i]);
        }
        double dx = (xmax - xmin) * 0.1, dy = (ymax - ymin) * 0.1;
        xmin -= dx;
        xmax += dx;
        ymin -= dy;
        ymax += dy;

        int x0 = MARGIN;
        int y0 = h - MARGIN;
        int plotW = w - 2 * MARGIN;
        int plotH = h - 2 * MARGIN;

        g2.setStroke(new BasicStroke(1f));
        g2.setColor(Color.LIGHT_GRAY);
        int ticks = 10;
        for (int i = 0; i <= ticks; i++) {
            int xi = x0 + i * plotW / ticks;
            int yi = y0 - i * plotH / ticks;
            g2.drawLine(xi, MARGIN, xi, y0);
            g2.drawLine(x0, yi, x0 + plotW, yi);
        }

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(x0, y0, x0 + plotW, y0);
        g2.drawLine(x0, y0, x0, y0 - plotH);

        g2.setFont(g2.getFont().deriveFont(14f));
        g2.drawString("X", x0 + plotW + 10, y0 + 5);
        g2.drawString("Y", x0 - 10, y0 - plotH - 10);

        g2.setFont(g2.getFont().deriveFont(10f));
        for (int i = 0; i <= ticks; i++) {
            int xi = x0 + i * plotW / ticks;
            double xv = xmin + i * (xmax - xmin) / ticks;
            g2.drawLine(xi, y0 - 5, xi, y0 + 5);
            String xs = String.format("%.2f", xv);
            g2.drawString(xs, xi - g2.getFontMetrics().stringWidth(xs) / 2, y0 + 20);
            int yi = y0 - i * plotH / ticks;
            double yv = ymin + i * (ymax - ymin) / ticks;
            g2.drawLine(x0 - 5, yi, x0 + 5, yi);
            String ys = String.format("%.2f", yv);
            g2.drawString(ys, x0 - 10 - g2.getFontMetrics().stringWidth(ys), yi + 5);
        }

        g2.setColor(Color.BLACK);
        for (int i = 0; i < x.length; i++) {
            int px = x0 + (int) ((x[i] - xmin) / (xmax - xmin) * plotW);
            int py = y0 - (int) ((y[i] - ymin) / (ymax - ymin) * plotH);
            g2.fillOval(px - 4, py - 4, 8, 8);
        }

        int legendY = MARGIN;
        for (int idx = 0; idx < funcs.size(); idx++) {
            Function f = funcs.get(idx);
            g2.setColor(colors[idx % colors.length]);
            g2.setStroke(new BasicStroke(2f));
            int prevX = x0, prevY = y0 - (int) ((f.evaluate(xmin) - ymin) / (ymax - ymin) * plotH);
            for (int i = 1; i <= plotW; i++) {
                double xv = xmin + i * (xmax - xmin) / plotW;
                double yv = f.evaluate(xv);
                int cx = x0 + i;
                int cy = y0 - (int) ((yv - ymin) / (ymax - ymin) * plotH);
                g2.drawLine(prevX, prevY, cx, cy);
                prevX = cx;
                prevY = cy;
            }
            g2.drawString(f.getName(), x0 + plotW - 80, legendY);
            legendY += 15;
        }
    }
}