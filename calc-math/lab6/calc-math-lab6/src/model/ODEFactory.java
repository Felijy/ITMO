package model;

public class ODEFactory {

    public static ODESystem createODE(int index) {
        switch (index) {
            case 0:
                return new ODEXPlusY();
            case 1:
                return new ODEYDivX();
            case 2:
                return new ODEExpX();
            default:
                throw new IllegalArgumentException("Неизвестный индекс ОДУ: " + index);
        }
    }

    private static class ODEXPlusY implements ODESystem {
        @Override
        public double derivative(double x, double y) {
            return x + y;
        }

        @Override
        public double exactSolution(double x, double x0, double y0) {
            return Math.exp(x - x0) * (y0 + x0 + 1) - x - 1;
        }

        @Override
        public String getDescription() {
            return "y' = x + y";
        }
    }

    private static class ODEYDivX implements ODESystem {
        @Override
        public double derivative(double x, double y) {
            return Math.sin(x) - y;
        }

        @Override
        public double exactSolution(double x, double x0, double y0) {
            return (2*Math.exp(x0)* y0-Math.exp(x0)*Math.sin(x0)+Math.exp(x0)*Math.cos(x0)) / (2*Math.exp(x)) + (Math.sin(x)) / 2 - (Math.cos(x)) / 2;
        }

        @Override
        public String getDescription() {
            return "y' = sin(x) - y";
        }
    }

    private static class ODEExpX implements ODESystem {
        @Override
        public double derivative(double x, double y) {
            return Math.exp(x);
        }

        @Override
        public double exactSolution(double x, double x0, double y0) {
            return y0 - Math.exp(x0) + Math.exp(x);
        }

        @Override
        public String getDescription() {
            return "y' = e^x";
        }
    }
}
