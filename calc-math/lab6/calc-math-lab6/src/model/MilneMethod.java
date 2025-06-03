package model;

import java.util.ArrayList;
import java.util.List;

public class MilneMethod {

    public static List<Double> solve(ODESystem system, double x0, double xn, double y0, double h, double eps) {
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();

        double x = x0;
        while (x <= xn + 1e-12) {
            xs.add(x);
            x += h;
            if (x > xn + 1e-12) {
                xs.add(xn);
                break;
            }
        }

        ys.add(y0);
        for (int i = 1; i < Math.min(4, xs.size()); i++) {
            double xi = xs.get(i - 1);
            double yi = ys.get(i - 1);
            double hi = xs.get(i) - xi;

            double k1 = hi * system.derivative(xi, yi);
            double k2 = hi * system.derivative(xi + hi / 2, yi + k1 / 2);
            double k3 = hi * system.derivative(xi + hi / 2, yi + k2 / 2);
            double k4 = hi * system.derivative(xi + hi, yi + k3);

            double yNext = yi + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            ys.add(yNext);
        }

        for (int i = 4; i < xs.size(); i++) {
            double xi = xs.get(i);
            double hi = xs.get(i) - xs.get(i - 1);

            double f1 = system.derivative(xs.get(i - 3), ys.get(i - 3));
            double f2 = system.derivative(xs.get(i - 2), ys.get(i - 2));
            double f3 = system.derivative(xs.get(i - 1), ys.get(i - 1));

            double yPredictor = ys.get(i - 4) + 4 * hi * (2 * f1 - f2 + 2 * f3) / 3;

            double yCorrector = yPredictor;
            int maxIterations = 100;
            int iteration = 0;

            while (iteration < maxIterations) {
                double f0 = system.derivative(xs.get(i - 2), ys.get(i - 2));
                double f1_corr = system.derivative(xs.get(i - 1), ys.get(i - 1));
                double f2_corr = system.derivative(xi, yCorrector);

                double yNew = ys.get(i - 2) + hi * (f0 + 4 * f1_corr + f2_corr) / 3;

                if (Math.abs(yNew - yCorrector) < eps) {
                    yCorrector = yNew;
                    break;
                }
                yCorrector = yNew;
                iteration++;
            }

            ys.add(yCorrector);
        }

        return ys;
    }

    public static List<Double> calculateGlobalErrors(ODESystem system,
                                                     List<Double> xValues,
                                                     List<Double> numericalValues,
                                                     double x0, double y0) {
        List<Double> errors = new ArrayList<>();

        for (int i = 0; i < xValues.size(); i++) {
            double exactValue = system.exactSolution(xValues.get(i), x0, y0);
            double numericalValue = numericalValues.get(i);
            errors.add(Math.abs(exactValue - numericalValue));
        }

        return errors;
    }
}