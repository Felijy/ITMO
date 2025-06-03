package model;

import java.util.ArrayList;
import java.util.List;
import model.RungeHelper.RungeResult;


public class EulerMethod {

    public static List<Double> solve(ODESystem system, double x0, double xn, double y0, double h) {
        List<Double> ys = new ArrayList<>();
        double x = x0;
        double y = y0;
        ys.add(y);

        while (x < xn - 1e-12) {
            double hCur = Math.min(h, xn - x);
            y += hCur * system.derivative(x, y);
            x += hCur;
            ys.add(y);
        }
        return ys;
    }

    public static RungeResult solveWithRunge(ODESystem system,
                                             double x0, double xn,
                                             double y0, double eps,
                                             double h) {
        double hCur = h;

        while (true) {
            List<Double> xH = RungeHelper.buildXGrid(x0, xn, hCur);
            List<Double> yH = solve(system, x0, xn, y0, hCur);

            double hHalf = hCur / 2.0;
            List<Double> yH2 = solve(system, x0, xn, y0, hHalf);

            List<Double> rungeErrs = RungeHelper.estimateRungeErrors(yH, yH2, 1);
            double maxErr = RungeHelper.max(rungeErrs);

            if (maxErr <= eps) {
                return new RungeResult(xH, yH, rungeErrs, hCur);
            } else {
                hCur /= 2.0;
            }
        }
    }
}
