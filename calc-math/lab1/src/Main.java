import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = null;
        boolean fromFile = false;
        Random random = new Random();

        boolean isRandom = false;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("random")) {
                isRandom = true;
                System.out.println("Генерация случайной матрицы и вектора.");
                scanner = new Scanner(System.in);
            } else {
                try {
                    scanner = new Scanner(new File(args[0]));
                    fromFile = true;
                    System.out.println("Чтение данных из файла: " + args[0]);
                } catch (FileNotFoundException e) {
                    System.out.println("Ошибка: файл не найден!");
                    return;
                }
            }
        } else {
            scanner = new Scanner(System.in);
        }

        try {
            if (!isRandom) {
                System.out.println("Введите необходимую точность (степень 10, не больше -1, не меньше -15), по умолчанию: -6");
                String epsilonInput = scanner.nextLine().trim();
                if (!epsilonInput.isEmpty()) {
                    try {
                        int epsilon = Integer.parseInt(epsilonInput);
                        if (epsilon > -1 || epsilon < -15) {
                            System.out.println("Ошибка: степень должна быть не больше -1 и не меньше -15");
                            return;
                        }
                        Solver.setEpsilon(Math.pow(10, epsilon));
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введено некорректное число для epsilon");
                        return;
                    }
                }
            }

            System.out.print("Введите размерность матрицы (n <= 20): ");
            int n = scanner.nextInt();
            if (n < 1 || n > 20) {
                System.out.println("Ошибка: размерность матрицы должна быть от 1 до 20");
                return;
            }

            double[][] matrix = new double[n][n];
            double[] vector = new double[n];

            if (isRandom) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        matrix[i][j] = random.nextDouble() * 5;
                    }
                }

                for (int i = 0; i < n; i++) {
                    vector[i] = random.nextDouble() * 5;
                }

                System.out.println("Сгенерированная матрица коэффициентов:");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }

                System.out.println("Сгенерированные свободные члены:");
                for (int i = 0; i < n; i++) {
                    System.out.print(vector[i] + " ");
                }
                System.out.println();

            } else {
                System.out.println("Введите коэффициенты матрицы построчно (без свободных членов!):");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (!scanner.hasNextDouble()) {
                            System.out.println("Ошибка: ожидалось число в матрице (строка " + (i + 1) + ", столбец " + (j + 1) + ")");
                            return;
                        }
                        matrix[i][j] = scanner.nextDouble();
                    }
                }

                System.out.println("Введенная матрица коэффициентов:");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }

                System.out.println("Введите свободные члены:");
                for (int i = 0; i < n; i++) {
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Ошибка: ожидалось число для свободного члена (элемент " + (i + 1) + ")");
                        return;
                    }
                    vector[i] = scanner.nextDouble();
                }
            }

            if (Checker.checkDiagonal(matrix)) {
                System.out.println("Матрица имеет диагональное преобладание");
            } else {
                System.out.println("Попытка преобразовать матрицу...");
                Converter.RearrangedSystem rearranged = Converter.rearrangeMatrix(matrix, vector);

                if (rearranged == null) {
                    System.out.println("Невозможно достичь диагонального преобладания.");
                    return;
                }

                System.out.println("Новая матрица с диагональным преобладанием:");
                matrix = rearranged.matrix;
                vector = rearranged.vector;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }
            }

            Solver.solve(matrix, vector);
        } catch (Exception e) {
            System.out.println("Ошибка: некорректный ввод данных");
        } finally {
            if (fromFile) {
                scanner.close();
            }
        }
    }
}
