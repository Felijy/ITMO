package model;

public class LogarithmicFunction implements Function {
    private double a, b;

    @Override
    public double evaluate(double x) {
        return a + b * Math.log(x);
    }

    @Override
    public void fit(double[] x, double[] y) {
        int n = x.length;
        double[] lnX = new double[n];
        for (int i = 0; i < n; i++) {
            lnX[i] = Math.log(x[i]);
        }

        LinearFunction linear = new LinearFunction();
        linear.fit(lnX, y);
        double[] coeffs = linear.getCoefficients();

        b = coeffs[0];
        a = coeffs[1];
    }

    @Override
    public String getName() {
        return "Логарифмическая";
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b};
    }
}
