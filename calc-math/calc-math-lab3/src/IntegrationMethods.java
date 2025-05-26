public class IntegrationMethods {
    public enum MethodType {
        RECTANGLE_LEFT("Метод левых прямоугольников", 2),
        RECTANGLE_RIGHT("Метод правых прямоугольников", 2),
        RECTANGLE_MID("Метод средних прямоугольников", 2),
        TRAPEZOID("Метод трапеций", 2),
        SIMPSON("Метод Симпсона", 4);

        private final String displayName;
        private final int rungeOrder;

        MethodType(String displayName, int rungeOrder) {
            this.displayName = displayName;
            this.rungeOrder = rungeOrder;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getRungeOrder() {
            return rungeOrder;
        }
    }

    public static double integrate(FunctionEvaluator func, double a, double b,
                                   int n, MethodType method) {
        return switch (method) {
            case RECTANGLE_LEFT -> rectangleLeft(func, a, b, n);
            case RECTANGLE_RIGHT -> rectangleRight(func, a, b, n);
            case RECTANGLE_MID -> rectangleMid(func, a, b, n);
            case TRAPEZOID -> trapezoid(func, a, b, n);
            case SIMPSON -> simpson(func, a, b, n);
            default -> throw new IllegalArgumentException();
        };
    }

    private static double rectangleLeft(FunctionEvaluator func,
                                        double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double x = a + i * h;
            sum += func.evaluate(x);
        }

        return sum * h;
    }

    private static double rectangleRight(FunctionEvaluator func,
                                         double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 1; i <= n; i++) {
            double x = a + i * h;
            sum += func.evaluate(x);
        }

        return sum * h;
    }

    private static double rectangleMid(FunctionEvaluator func,
                                       double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double x = a + (i + 0.5) * h;
            sum += func.evaluate(x);
        }

        return sum * h;
    }

    private static double trapezoid(FunctionEvaluator func,
                                    double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += func.evaluate(x);
        }

        return h / 2 * (func.evaluate(a) + func.evaluate(b) + 2 * sum);
    }

    private static double simpson(FunctionEvaluator func,
                                  double a, double b, int n) {
        double h = (b - a) / n;
        double sum = func.evaluate(a) + func.evaluate(b);

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += (i % 2 == 0) ? 2 * func.evaluate(x) : 4 * func.evaluate(x);
        }

        return sum * h / 3;
    }
}