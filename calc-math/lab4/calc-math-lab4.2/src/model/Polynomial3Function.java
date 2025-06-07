package model;

public class Polynomial3Function implements Function {
    private double a, b, c, d;

    @Override
    public double evaluate(double x) {
        return a * x * x * x + b * x * x + c * x + d;
    }

    @Override
    public void fit(double[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumXX = 0, sumXXX = 0, sumXXXX = 0;
        double sumXXXXX = 0, sumXXXXXX = 0;
        double sumY = 0, sumXY = 0, sumXXY = 0, sumXXXY = 0;

        for (int i = 0; i < n; i++) {
            double xi = x[i];
            double yi = y[i];
            double xi2 = xi * xi;
            double xi3 = xi2 * xi;
            double xi4 = xi3 * xi;
            double xi5 = xi4 * xi;
            double xi6 = xi5 * xi;

            sumX += xi;
            sumXX += xi2;
            sumXXX += xi3;
            sumXXXX += xi4;
            sumXXXXX += xi5;
            sumXXXXXX += xi6;

            sumY += yi;
            sumXY += xi * yi;
            sumXXY += xi2 * yi;
            sumXXXY += xi3 * yi;
        }

        double[][] A = {
                {n, sumX, sumXX, sumXXX},
                {sumX, sumXX, sumXXX, sumXXXX},
                {sumXX, sumXXX, sumXXXX, sumXXXXX},
                {sumXXX, sumXXXX, sumXXXXX, sumXXXXXX}
        };
        double[] B = {sumY, sumXY, sumXXY, sumXXXY};

        double det = det4(A);
        double det0 = det4(new double[][]{
                {B[0], A[0][1], A[0][2], A[0][3]},
                {B[1], A[1][1], A[1][2], A[1][3]},
                {B[2], A[2][1], A[2][2], A[2][3]},
                {B[3], A[3][1], A[3][2], A[3][3]}
        });
        double det1 = det4(new double[][]{
                {A[0][0], B[0], A[0][2], A[0][3]},
                {A[1][0], B[1], A[1][2], A[1][3]},
                {A[2][0], B[2], A[2][2], A[2][3]},
                {A[3][0], B[3], A[3][2], A[3][3]}
        });
        double det2 = det4(new double[][]{
                {A[0][0], A[0][1], B[0], A[0][3]},
                {A[1][0], A[1][1], B[1], A[1][3]},
                {A[2][0], A[2][1], B[2], A[2][3]},
                {A[3][0], A[3][1], B[3], A[3][3]}
        });
        double det3 = det4(new double[][]{
                {A[0][0], A[0][1], A[0][2], B[0]},
                {A[1][0], A[1][1], A[1][2], B[1]},
                {A[2][0], A[2][1], A[2][2], B[2]},
                {A[3][0], A[3][1], A[3][2], B[3]}
        });

        d = det0 / det;
        c = det1 / det;
        b = det2 / det;
        a = det3 / det;
    }

    @Override
    public String getName() {
        return "Функция 3 степени";
    }

    @Override
    public double[] getCoefficients() {
        return new double[]{a, b, c, d};
    }

    private static double det3(double[][] m) {
        return m[0][0] * m[1][1] * m[2][2]
                + m[0][1] * m[1][2] * m[2][0]
                + m[0][2] * m[1][0] * m[2][1]
                - m[0][2] * m[1][1] * m[2][0]
                - m[0][1] * m[1][0] * m[2][2]
                - m[0][0] * m[1][2] * m[2][1];
    }

    private static double det4(double[][] m) {
        double res = 0;
        for (int c = 0; c < 4; c++) {
            double[][] minor = new double[3][3];
            for (int i = 1; i < 4; i++) {
                int mi = i - 1;
                int mj = 0;
                for (int j = 0; j < 4; j++) {
                    if (j == c) continue;
                    minor[mi][mj++] = m[i][j];
                }
            }
            res += ((c % 2) == 0 ? 1 : -1) * m[0][c] * det3(minor);
        }
        return res;
    }
}