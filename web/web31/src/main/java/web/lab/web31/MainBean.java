package web.lab.web31;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import web.lab.entity.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
@Getter
@Setter
public class MainBean implements Serializable {
    @Getter
    @Setter
    private Integer x;
    @Getter
    @Setter
    private double y;
    @Getter
    @Setter
    private double r = 1.0;
    @Getter
    @Setter
    private double hiddenX;
    @Getter
    @Setter
    private double hiddenY;

    @Inject
    private ResultsBean resultsBean;

    // Метод для получения доступных значений X
    public List<Integer> getxValues() {
        List<Integer> availableXValues = new ArrayList<>();
        for (int i = -5; i <= 1; i++) {
            availableXValues.add(i);
        }
        return availableXValues;
    }

    // Метод для получения доступных значений R
    public List<Double> getAvailableRValues() {
        return Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);
    }

    // Метод для установки R
    public void setR(double rValue) {
        this.r = rValue;
        System.out.println("Установлено R: " + r);
    }

    public void checkPoint() {
        boolean hit = isHit(x, y, r);
        Point point = new Point(x, y, r, hit);
        resultsBean.addResult(point);
        System.out.println("FROM mainBean:  ->>>" + resultsBean.getResults());
    }

    private boolean isHit(double x, double y, double r) {
        return x >= 0 && y >= 0 && x * x + y * y <= r * r;
    }

    public void addPointFromGraph() {
        System.out.println("Полученные координаты: X = " + hiddenX + ", Y = " + hiddenY + ", R = " + r);
        boolean hit = isHit(hiddenX, hiddenY, r);
        Point point = new Point(hiddenX, hiddenY, r, hit);
        resultsBean.addResult(point);
    }


}
