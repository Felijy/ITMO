package func.base;

import func.api.MathModule;

public class CosSeries implements MathModule {
    private final double eps;

    public CosSeries(double eps) { this.eps = eps; }

    @Override
    public double calculate(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) return Double.NaN;
        double term = 1.0, sum = 1.0;
        int n = 1;
        while (Math.abs(term) > eps && n < 10_000) {
            term *= -x * x / ((2.0 * n - 1.0) * (2.0 * n));
            sum += term;
            n++;
        }
        return sum;
    }
}
