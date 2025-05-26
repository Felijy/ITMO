package model;

public class Polynomial2Function implements Function {
    private double a, b, c;

    @Override
    public double evaluate(double x) {
        return a * x * x + b * x + c;
    }

    @Override
    public void fit(double[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumXX = 0, sumXXX = 0, sumXXXX = 0, sumY = 0, sumXY = 0, sumXXY = 0;
        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumXX += x[i] * x[i];
            sumXXX += x[i] * x[i] * x[i];
            sumXXXX += x[i] * x[i] * x[i] * x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumXXY += x[i] * x[i] * y[i];
        }

        double[][] A = {
                {n, sumX, sumXX},
                {sumX, sumXX, sumXXX},
                {sumXX, sumXXX, sumXXXX}
        };
        double[] B = {sumY, sumXY, sumXXY};

        double det = det3(A);
        double det0 = det3(new double[][]{{B[0], A[0][1], A[0][2]},
                {B[1], A[1][1], A[1][2]},
                {B[2], A[2][1], A[2][2]}});
        double det1 = det3(new double[][]{{A[0][0], B[0], A[0][2]},
                {A[1][0], B[1], A[1][2]},
                {A[2][0], B[2], A[2][2]}});
        double det2 = det3(new double[][]{{A[0][0], A[0][1], B[0]},
                {A[1][0], A[1][1], B[1]},
                {A[2][0], A[2][1], B[2]}});

        c = det0 / det;
        b = det1 / det;
        a = det2 / det;
    }

    @Override
    public String getName() {
        return "Функция 2 степени";
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b, c};
    }

    private static double det3(double[][] m) {
        return m[0][0] * m[1][1] * m[2][2]
                + m[0][1] * m[1][2] * m[2][0]
                + m[0][2] * m[1][0] * m[2][1]
                - m[0][2] * m[1][1] * m[2][0]
                - m[0][1] * m[1][0] * m[2][2]
                - m[0][0] * m[1][2] * m[2][1];
    }
}