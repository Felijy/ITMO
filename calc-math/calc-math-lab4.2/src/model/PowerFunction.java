package model;

public class PowerFunction implements Function {
    private double a, b;

    @Override
    public double evaluate(double x) {
        return a * Math.pow(x, b);
    }

    @Override
    public void fit(double[] x, double[] y) {
        int n = x.length;
        double[] lnX = new double[n];
        double[] lnY = new double[n];
        for (int i = 0; i < n; i++) {
            lnX[i] = Math.log(x[i]);
            lnY[i] = Math.log(y[i]);
        }

        LinearFunction linear = new LinearFunction();
        linear.fit(lnX, lnY);
        double[] coeffs = linear.getCoefficients();
        b = coeffs[0];
        a = Math.exp(coeffs[1]);
    }

    @Override
    public String getName() {
        return "Степенная";
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b};
    }
}
