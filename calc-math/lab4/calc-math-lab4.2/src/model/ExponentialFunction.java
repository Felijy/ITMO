package model;

public class ExponentialFunction implements Function {
    private double a, b;

    @Override
    public double evaluate(double x) {
        return a * Math.exp(b * x);
    }

    @Override
    public void fit(double[] x, double[] y) {
        int n = x.length;
        double[] lnY = new double[n];
        for (int i = 0; i < n; i++) {
            lnY[i] = Math.log(y[i]);
        }

        LinearFunction linear = new LinearFunction();
        linear.fit(x, lnY);
        double[] coeffs = linear.getCoefficients();
        b = coeffs[0];
        a = Math.exp(coeffs[1]);
    }

    @Override
    public String getName() {
        return "Экспоненциальная";
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b};
    }
}