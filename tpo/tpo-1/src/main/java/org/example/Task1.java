package org.example;

public class Task1 {
    private static final double EPS = 1e-15;
    private static final int MAX_ITER = 20;

    public static double arctg_series(double x) {
        if (x < 0) {
            return -arctg_series(-x);
        }

        if (x > 1.0) {
            return Math.PI / 2 - arctg_series(1.0 / x);
        }

        if (x > 0.6) {
            return Math.PI / 4 + arctg_series((x - 1.0) / (1.0 + x));
        }

        double term = x;
        double sum = x;
        int n = 0;

        while (Math.abs(term) > EPS && n < MAX_ITER) {
            term *= -x * x * (2.0 * n + 1) / (2.0 * n + 3);
            sum += term;
            n++;
        }

        return sum;
    }
}