public class Checker {
    public static boolean checkDiagonal(double[][] matrix) {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    rowSum += Math.abs(matrix[i][j]);
                }
            }
            if (Math.abs(matrix[i][i]) < rowSum) {
                return false;
            }
        }
        return true;
    }
}

