public class RungeRuleEstimator {
    public static IntegrationResult calculate(
            FunctionEvaluator func,
            IntegrationMethods.MethodType method,
            double a,
            double b,
            double epsilon,
            int initialN
    ) {
        if (a >= b) throw new IllegalArgumentException("a должно быть меньше b");
        if (epsilon <= 0) throw new IllegalArgumentException("Точность должна быть > 0");

        int n = initialN;
        double result = 0;
        double prevResult;
        double error;

        do {
            prevResult = IntegrationMethods.integrate(func, a, b, n, method);
            result = IntegrationMethods.integrate(func, a, b, n * 2, method);
            error = estimateError(prevResult, result, method);
            n *= 2;

            if (n > 1_000_000_000) {
                throw new RuntimeException("Не удалось достичь заданной точности");
            }
        } while (error > epsilon && n < 1_000_000_000);

        return new IntegrationResult(result, n);
    }

    private static double estimateError(double prev, double curr,
                                        IntegrationMethods.MethodType method) {
        int order = method.getRungeOrder();
        return Math.abs(curr - prev) / (Math.pow(2, order) - 1);
    }
}