package model;

public interface ODESystem {
    double derivative(double x, double y);

    double exactSolution(double x, double x0, double y0);

    String getDescription();
}
