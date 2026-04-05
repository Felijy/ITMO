package func.base;

import func.api.MathModule;

public class LnSeries implements MathModule {
    private final double eps;

    public LnSeries(double eps) { this.eps = eps; }

    @Override
    public double calculate(double x) {
        if (x <= 0 || Double.isNaN(x) || Double.isInfinite(x)) return Double.NaN;
        double z = (x - 1) / (x + 1);
        double zPow = z;
        double sum = 0.0;
        int k = 1;
        while (Math.abs(zPow / k) > eps && k < 1_000_000) {
            sum += zPow / k;
            zPow *= z * z;
            k += 2;
        }
        return 2 * sum;
    }
}
