package model;

import java.util.ArrayList;
import java.util.List;

public class RungeHelper {
    public static List<Double> buildXGrid(double x0, double xn, double h) {
        List<Double> xs = new ArrayList<>();
        double x = x0;
        xs.add(x);
        while (x < xn - 1e-12) {
            double hCur = Math.min(h, xn - x);
            x += hCur;
            xs.add(x);
        }
        return xs;
    }

    public static List<Double> estimateRungeErrors(List<Double> yH, List<Double> yH2, int order) {
        List<Double> errors = new ArrayList<>();
        for (int i = 0; i < yH.size(); i++) {
            int idxH2 = i * 2;
            double err = Math.abs(yH2.get(idxH2) - yH.get(i)) / (Math.pow(2, order) - 1);
            errors.add(err);
        }
        return errors;
    }

    public static double max(List<Double> list) {
        double max = list.get(0);
        for (double val : list) {
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    public static class RungeResult {
        private final List<Double> xValues;
        private final List<Double> yValues;
        private final List<Double> rungeErrs;
        private final double hUsed;

        public RungeResult(List<Double> xValues, List<Double> yValues, List<Double> rungeErrs, double hUsed) {
            this.xValues = new ArrayList<>(xValues);
            this.yValues = new ArrayList<>(yValues);
            this.rungeErrs = new ArrayList<>(rungeErrs);
            this.hUsed = hUsed;
        }

        public List<Double> getXValues() {
            return xValues;
        }

        public List<Double> getYValues() {
            return yValues;
        }

        public List<Double> getRungeErrs() {
            return rungeErrs;
        }

        public double getHUsed() {
            return hUsed;
        }
    }
}