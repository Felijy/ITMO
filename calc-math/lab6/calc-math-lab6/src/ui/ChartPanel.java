package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Панель для рисования графиков:
 *  - точное решение (синяя линия) — соединяем точки точного решения, вычисленные на объединённой сетке узлов из всех методов;
 *  - метод Эйлера (красные точки) — точки ровно в тех координатах, что и в таблице Эйлера;
 *  - улучшенный метод Эйлера (зелёные точки) — точки ровно в тех координатах, что и в таблице улучшенного Эйлера;
 *  - метод Милна (оранжевые точки) — точки ровно в тех координатах, что и в таблице Милна.
 */
public class ChartPanel extends JPanel {
    
    private List<Double> exactXUnion;
    private List<Double> exactYUnion;

    
    private List<Double> xEuler;
    private List<Double> exactEuler;   
    private List<Double> yEuler;

    
    private List<Double> xImproved;
    private List<Double> exactImproved; 
    private List<Double> yImproved;

    
    private List<Double> xMilne;
    private List<Double> exactMilne; 
    private List<Double> yMilne;

    private static final int MARGIN = 50;

    /**
     * Устанавливает все необходимые данные для графика:
     * - Для построения «точной» кривой: exactXUnion / exactYUnion (объединённая сетка узлов всех методов).
     * - Для рисования точек Эйлера: xEuler / exactEuler / yEuler.
     * - Для рисования точек улучшенного Эйлера: xImproved / exactImproved / yImproved.
     * - Для рисования точек Милна: xMilne / exactMilne / yMilne.
     */
    public void setData(
            List<Double> exactXUnion, List<Double> exactYUnion,
            List<Double> xEuler, List<Double> exactEuler, List<Double> yEuler,
            List<Double> xImproved, List<Double> exactImproved, List<Double> yImproved,
            List<Double> xMilne, List<Double> exactMilne, List<Double> yMilne) {

        this.exactXUnion = exactXUnion;
        this.exactYUnion = exactYUnion;

        this.xEuler = xEuler;
        this.exactEuler = exactEuler;
        this.yEuler = yEuler;

        this.xImproved = xImproved;
        this.exactImproved = exactImproved;
        this.yImproved = yImproved;

        this.xMilne = xMilne;
        this.exactMilne = exactMilne;
        this.yMilne = yMilne;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (exactXUnion == null || exactXUnion.isEmpty()) {
            g.setColor(Color.GRAY);
            g.drawString("График будет отображён после расчёта",
                    getWidth() / 2 - 100, getHeight() / 2);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        
        double xmin = exactXUnion.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double xmax = exactXUnion.stream().mapToDouble(Double::doubleValue).max().orElse(1);
        double ymin = exactYUnion.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double ymax = exactYUnion.stream().mapToDouble(Double::doubleValue).max().orElse(1);

        if (yEuler != null) {
            ymin = Math.min(ymin, yEuler.stream().mapToDouble(Double::doubleValue).min().orElse(ymin));
            ymax = Math.max(ymax, yEuler.stream().mapToDouble(Double::doubleValue).max().orElse(ymax));
        }
        if (yImproved != null) {
            ymin = Math.min(ymin, yImproved.stream().mapToDouble(Double::doubleValue).min().orElse(ymin));
            ymax = Math.max(ymax, yImproved.stream().mapToDouble(Double::doubleValue).max().orElse(ymax));
        }
        if (yMilne != null) {
            ymin = Math.min(ymin, yMilne.stream().mapToDouble(Double::doubleValue).min().orElse(ymin));
            ymax = Math.max(ymax, yMilne.stream().mapToDouble(Double::doubleValue).max().orElse(ymax));
        }

        
        double dx = (xmax - xmin) * 0.1;
        double dy = (ymax - ymin) * 0.1;
        xmin -= dx; xmax += dx;
        ymin -= dy; ymax += dy;

        int x0 = MARGIN, y0 = h - MARGIN;
        int plotW = w - 2 * MARGIN, plotH = h - 2 * MARGIN;

        
        drawGrid(g2, x0, y0, plotW, plotH, xmin, xmax, ymin, ymax);

        
        drawAxes(g2, x0, y0, plotW, plotH);

        
        drawAxisLabels(g2, x0, y0, plotW, plotH, xmin, xmax, ymin, ymax);

        
        if (exactXUnion != null && !exactXUnion.isEmpty()) {
            drawContinuousLine(g2, exactXUnion, exactYUnion, x0, y0, plotW, plotH,
                    xmin, xmax, ymin, ymax, Color.BLUE, 2.0f);
        }

        
        if (xEuler != null && !xEuler.isEmpty()) {
            drawPoints(g2, xEuler, yEuler, x0, y0, plotW, plotH,
                    xmin, xmax, ymin, ymax, Color.RED, 6);
        }

        
        if (xImproved != null && !xImproved.isEmpty()) {
            drawPoints(g2, xImproved, yImproved, x0, y0, plotW, plotH,
                    xmin, xmax, ymin, ymax, Color.GREEN, 6);
        }

        
        if (xMilne != null && !xMilne.isEmpty()) {
            drawPoints(g2, xMilne, yMilne, x0, y0, plotW, plotH,
                    xmin, xmax, ymin, ymax, Color.ORANGE, 6);
        }

        
        drawLegend(g2, x0, MARGIN);
    }

    private void drawGrid(Graphics2D g2, int x0, int y0, int plotW, int plotH,
                          double xmin, double xmax, double ymin, double ymax) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(0.5f));

        int ticks = 10;
        for (int i = 0; i <= ticks; i++) {
            
            int xi = x0 + i * plotW / ticks;
            g2.drawLine(xi, y0 - plotH, xi, y0);

            
            int yi = y0 - i * plotH / ticks;
            g2.drawLine(x0, yi, x0 + plotW, yi);
        }
    }

    private void drawAxes(Graphics2D g2, int x0, int y0, int plotW, int plotH) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2f));

        
        g2.drawLine(x0, y0, x0 + plotW, y0); 
        g2.drawLine(x0, y0, x0, y0 - plotH); 

        
        g2.drawLine(x0 + plotW, y0, x0 + plotW - 5, y0 - 3);
        g2.drawLine(x0 + plotW, y0, x0 + plotW - 5, y0 + 3);
        g2.drawLine(x0, y0 - plotH, x0 - 3, y0 - plotH + 5);
        g2.drawLine(x0, y0 - plotH, x0 + 3, y0 - plotH + 5);
    }

    private void drawAxisLabels(Graphics2D g2, int x0, int y0, int plotW, int plotH,
                                double xmin, double xmax, double ymin, double ymax) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        FontMetrics fm = g2.getFontMetrics();

        int ticks = 5;

        
        for (int i = 0; i <= ticks; i++) {
            int xi = x0 + i * plotW / ticks;
            double xv = xmin + i * (xmax - xmin) / ticks;
            String xs = String.format("%.2f", xv);
            g2.drawString(xs, xi - fm.stringWidth(xs) / 2, y0 + 15);
        }

        
        for (int i = 0; i <= ticks; i++) {
            int yi = y0 - i * plotH / ticks;
            double yv = ymin + i * (ymax - ymin) / ticks;
            String ys = String.format("%.2f", yv);
            g2.drawString(ys, x0 - fm.stringWidth(ys) - 8, yi + 4);
        }

        
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2.drawString("x", x0 + plotW + 10, y0 + 5);
        g2.drawString("y", x0 - 15, y0 - plotH - 10);
    }

    private void drawContinuousLine(Graphics2D g2, List<Double> xVals, List<Double> yVals,
                                    int x0, int y0, int plotW, int plotH,
                                    double xmin, double xmax, double ymin, double ymax,
                                    Color color, float thickness) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));

        for (int i = 0; i < xVals.size() - 1; i++) {
            int x1 = x0 + (int) ((xVals.get(i) - xmin) / (xmax - xmin) * plotW);
            int y1 = y0 - (int) ((yVals.get(i) - ymin) / (ymax - ymin) * plotH);
            int x2 = x0 + (int) ((xVals.get(i + 1) - xmin) / (xmax - xmin) * plotW);
            int y2 = y0 - (int) ((yVals.get(i + 1) - ymin) / (ymax - ymin) * plotH);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawPoints(Graphics2D g2, List<Double> xVals, List<Double> yVals,
                            int x0, int y0, int plotW, int plotH,
                            double xmin, double xmax, double ymin, double ymax,
                            Color color, int size) {
        g2.setColor(color);

        for (int i = 0; i < xVals.size(); i++) {
            int px = x0 + (int) ((xVals.get(i) - xmin) / (xmax - xmin) * plotW);
            int py = y0 - (int) ((yVals.get(i) - ymin) / (ymax - ymin) * plotH);

            g2.fillOval(px - size / 2, py - size / 2, size, size);
            g2.setColor(Color.BLACK);
            g2.drawOval(px - size / 2, py - size / 2, size, size);
            g2.setColor(color);
        }
    }

    private void drawLegend(Graphics2D g2, int x0, int margin) {
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        int lx = x0 + 10;
        int ly = margin + 10;

        
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(lx, ly, lx + 20, ly);
        g2.setColor(Color.BLACK);
        g2.drawString("Точное решение", lx + 25, ly + 4);

        
        ly += 20;
        g2.setColor(Color.RED);
        g2.fillOval(lx + 5, ly - 3, 6, 6);
        g2.setColor(Color.BLACK);
        g2.drawOval(lx + 5, ly - 3, 6, 6);
        g2.drawString("Метод Эйлера", lx + 25, ly + 4);

        
        ly += 20;
        g2.setColor(Color.GREEN);
        g2.fillOval(lx + 5, ly - 3, 6, 6);
        g2.setColor(Color.BLACK);
        g2.drawOval(lx + 5, ly - 3, 6, 6);
        g2.drawString("Мод. Эйлер", lx + 25, ly + 4);

        
        ly += 20;
        g2.setColor(Color.ORANGE);
        g2.fillOval(lx + 5, ly - 3, 6, 6);
        g2.setColor(Color.BLACK);
        g2.drawOval(lx + 5, ly - 3, 6, 6);
        g2.drawString("Метод Милна", lx + 25, ly + 4);
    }
}