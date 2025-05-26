import java.util.Scanner;

public class Main {
    private static double readDoubleWithComma(Scanner scanner, String prompt) {
        try {
            System.out.print(prompt);
            String input = scanner.next().replace(',', '.');
            return Double.parseDouble(input);
        } catch (Exception e) {
            scanner.nextLine();
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        while (continueCalculation) {
            try {
                printFunctionMenu();
                FunctionEvaluator.StandardFunction function = selectFunction(scanner);

                printMethodMenu();
                IntegrationMethods.MethodType method = selectMethod(scanner);

                double a = readDoubleWithComma(scanner, "\nВведите нижний предел интегрирования (a): ");
                double b = readDoubleWithComma(scanner, "Введите верхний предел интегрирования (b): ");
                double epsilon = readDoubleWithComma(scanner, "Введите требуемую точность (ε): ");

                IntegrationResult result = RungeRuleEstimator.calculate(
                        function,
                        method,
                        a,
                        b,
                        epsilon,
                        4
                );

                printResults(function, method, result, epsilon, a, b);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }

            continueCalculation = askForContinue(scanner);
            System.out.println();
        }

        scanner.close();
        System.out.println("Пока-пока :)");
    }

    private static void printResults(FunctionEvaluator.StandardFunction function,
                                     IntegrationMethods.MethodType method,
                                     IntegrationResult result, double epsilon,
                                     double a, double b) {
        System.out.println("\n════════ Результаты вычислений ════════");
        System.out.printf("Выбранная функция: %s\n", function.getDisplayName());
        System.out.printf("Метод интегрирования: %s\n", method.getDisplayName());
        System.out.printf("Нижняя граница: %s\n", a);
        System.out.printf("Верхняя граница: %s\n", b);
        System.out.printf("Значение интеграла: %.8f\n", result.value());
        System.out.printf("Достигнутая точность: %.8f\n", epsilon);
        System.out.printf("Число разбиений интервала: %d\n", result.partitions());
        System.out.println("═══════════════════════════════════════");
    }

    private static boolean askForContinue(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\nХотите продолжить? (да/нет): ");
                String answer = scanner.next().trim().toLowerCase();
                scanner.nextLine();

                if (answer.startsWith("д") || answer.startsWith("y")) {
                    return true;
                } else if (answer.startsWith("н") || answer.startsWith("n")) {
                    return false;
                }
                throw new IllegalArgumentException("Некорректный ответ");

            } catch (Exception e) {
                System.out.println("Только 'да' или 'нет'");
            }
        }
    }

    private static void printFunctionMenu() {
        System.out.println("════════ Доступные функции ════════");
        int i = 1;
        for (FunctionEvaluator.StandardFunction func :
                FunctionEvaluator.StandardFunction.values()) {
            System.out.printf("%2d. %s\n", i++, func.getDisplayName());
        }
        System.out.print("Ваш выбор: ");
    }

    private static void printMethodMenu() {
        System.out.println("\n════════ Методы интегрирования ════════");
        int i = 1;
        for (IntegrationMethods.MethodType method :
                IntegrationMethods.MethodType.values()) {
            System.out.printf("%2d. %s\n", i++, method.getDisplayName());
        }
        System.out.print("Ваш выбор: ");
    }

    private static FunctionEvaluator.StandardFunction selectFunction(Scanner s) {
        int choice;
        while (true) {
            try {
                choice = s.nextInt();
                System.out.println("═══════════════════════════════════");
                return FunctionEvaluator.StandardFunction.values()[choice - 1];
            } catch (Exception e) {
                System.out.print("Некорректный ввод, попробуйте снова: ");
                s.nextLine();
            }
        }
    }

    private static IntegrationMethods.MethodType selectMethod(Scanner s) {
        int choice;
        while (true) {
            try {
                choice = s.nextInt();
                System.out.println("═══════════════════════════════════════");
                return IntegrationMethods.MethodType.values()[choice - 1];
            } catch (Exception e) {
                System.out.print("Некорректный ввод, попробуйте снова: ");
                s.nextLine();
            }
        }
    }
}