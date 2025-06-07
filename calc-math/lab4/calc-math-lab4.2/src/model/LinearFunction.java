package model;

public class LinearFunction implements Function {
    private double a, b;
    private double pearson;

    @Override
    public double evaluate(double x) {
        return a * x + b;
    }

    @Override
    public void fit(double[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumXX += x[i] * x[i];
        }
        a = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        b = (sumY - a * sumX) / n;

        double midX = sumX / n;
        double midY = sumY / n;

        double sumOne = 0, sumTwo = 0, sumThree = 0;

        for (int i = 0; i < n; i++) {
            sumOne += (x[i] - midX) * (y[i] - midY);
            sumTwo += (x[i] - midX) * (x[i] - midX);
            sumThree += (y[i] - midY) * (y[i] - midY);
        }
        pearson = sumOne / Math.sqrt(sumTwo * sumThree);
    }

    public double getPearson() {
        return pearson;
    }

    @Override
    public String getName() {
        return "Линейная";
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b};
    }
}