import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        Scanner scanner = null;
        boolean fromFile = false;
        Random random = new Random();

        boolean isRandom = false;

        try {
            scanner = new Scanner(System.in);
            System.out.println("Выберите режим работы:\n1 - Ввод вручную\n2 - Ввод из файла\n3 - Рандомный ввод");
            int num = scanner.nextInt();
            if (num == 3) {
                isRandom = true;
                scanner = new Scanner(System.in);
            } else if (num == 2) {
                System.out.println("Введите имя файла:");
                try {
                    String input = scanner.next();
                    scanner = new Scanner(new File(input));
                } catch (FileNotFoundException e) {
                    System.out.println("Указанный файл не удалось найти");
                    return;
                }
            } else {
                scanner = new Scanner(System.in);
            }
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
                        try {
                            matrix[i][j] = parseDouble(scanner);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: ожидалось число в матрице (строка " + (i + 1) + ", столбец " + (j + 1) + ")");
                        }
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

    private static double parseDouble(Scanner input) {
        String line = input.next();
        return Double.parseDouble(line.replaceAll(",", "."));
    }
}
