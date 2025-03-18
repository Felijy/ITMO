import java.util.Arrays;

public class Converter {
    public static RearrangedSystem rearrangeMatrix(double[][] matrix, double[] vector) {
        int size = matrix.length;
        int[] rowIndexes = new int[size];
        for (int i = 0; i < size; i++) {
            rowIndexes[i] = i;
        }

        return permuteRows(matrix, vector, rowIndexes, 0);
    }

    private static RearrangedSystem permuteRows(double[][] matrix, double[] vector, int[] rowIndexes, int currentIndex) {
        int size = matrix.length;
        if (currentIndex == size) {
            double[][] newMatrix = new double[size][size];
            double[] newVector = new double[size];

            for (int i = 0; i < size; i++) {
                newMatrix[i] = Arrays.copyOf(matrix[rowIndexes[i]], size);
                newVector[i] = vector[rowIndexes[i]];
            }

            return Checker.checkDiagonal(newMatrix) ? new RearrangedSystem(newMatrix, newVector) : null;
        }

        for (int i = currentIndex; i < size; i++) {
            swap(rowIndexes, i, currentIndex);
            RearrangedSystem result = permuteRows(matrix, vector, rowIndexes, currentIndex + 1);
            if (result != null) {
                return result;
            }
            swap(rowIndexes, i, currentIndex);
        }
        return null;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static class RearrangedSystem {
        public final double[][] matrix;
        public final double[] vector;

        public RearrangedSystem(double[][] matrix, double[] vector) {
            this.matrix = matrix;
            this.vector = vector;
        }
    }
}
