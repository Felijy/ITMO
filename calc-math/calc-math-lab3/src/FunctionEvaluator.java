import java.util.function.Function;

public interface FunctionEvaluator {
    double evaluate(double x);
    String getDisplayName();

    enum StandardFunction implements FunctionEvaluator {
        QUADRATIC("3x^3 - 4x^2 + 5x - 16", x -> 3*x*x*x - 4*x*x + 5*x - 16),
        SINUS("x + sin(x)", x -> Math.sin(x) + x),
        HYPERBOLA("4x^3 - 5x^2 + 6x - 7", x -> 4*x*x*x - 5*x*x + 6*x - 7);

        private final String displayName;
        private final Function<Double, Double> function;

        StandardFunction(String displayName, Function<Double, Double> function) {
            this.displayName = displayName;
            this.function = function;
        }

        public double evaluate(double x) {
            return function.apply(x);
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}