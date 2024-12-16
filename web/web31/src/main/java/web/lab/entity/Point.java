package web.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Point {

    private final double x;
    private final double y;
    private final double r;
    private final boolean hit;

    // Дополнительные методы для вычислений
    public double getXPosition() {
        return (x + 1) * 50;
    }

    public double getYPosition() {
        return (1 - y) * 50;
    }
}
