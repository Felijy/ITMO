package model;

public interface Function {
    double evaluate(double x);
    void fit(double[] x, double[] y);
    String getName();
    double[] getCoefficients();
}