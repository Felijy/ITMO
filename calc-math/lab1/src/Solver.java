import java.util.Arrays;

public class Solver {
    private static double epsilon = 1e-6;

    public static void solve(double[][] A, double[] b) {
        int size = A.length;
        double[][] B = new double[size][size];
        double[] c = new double[size];

        for (int i = 0; i < size; i++) {
            if (A[i][i] == 0) {
                System.out.println("Невозможно решить: на диагонали нулевой элемент");
                return;
            }
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    B[i][j] = -A[i][j] / A[i][i];
                }
            }
            c[i] = b[i] / A[i][i];
        }

        double norm = calculateNorm(B);
        System.out.println("Норма матрицы B: " + norm);
        if (norm >= 1) {
            System.out.println("Метод может не сходиться, так как норма B >= 1.");
            return;
        }

        double[] x = new double[size];
        double[] prevX = new double[size];
        int iterations = 1;
        double accuracyNorm = 1;

        while (accuracyNorm >= epsilon) {
            System.arraycopy(x, 0, prevX, 0, size);

            for (int i = 0; i < size; i++) {
                x[i] = c[i];
                for (int j = 0; j < size; j++) {
                    x[i] += B[i][j] * prevX[j];
                }
            }

            double[] accuracyVector = new double[size];
            for (int i = 0; i < size; i++) {
                accuracyVector[i] = Math.abs(x[i] - prevX[i]);
            }
            accuracyNorm = calculateNorm(accuracyVector);
            System.out.println("Итерация " + iterations + ", Вектор погрешности: " + Arrays.toString(accuracyVector));

            iterations++;
        }

        System.out.println("Решение найдено за " + (iterations - 1) + " итераций");
        System.out.println("Вектор неизвестных x: " + Arrays.toString(x));
    }

    private static double calculateNorm(double[][] matrix) {
        double maxSum = 0;
        for (double[] row : matrix) {
            double sum = 0;
            for (double val : row) {
                sum += Math.abs(val);
            }
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }

    private static double calculateNorm(double[] vector) {
        double max = 0;
        for (double v : vector) {
            max = Math.max(max, Math.abs(v));
        }
        return max;
    }

    public static void setEpsilon(double epsilon) {
        Solver.epsilon = epsilon;
    }
}
