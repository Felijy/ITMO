package data;

import java.util.Objects;

public class Coordinates {
    private float x; //Максимальное значение поля: 766
    private Float y; //Поле не может быть null

    /**
     * Вспомогательный класс, в котором хранятся координаты
     * @param x значение по оси x. Value <= 766
     * @param y значение по оси y. Not null
     */
    public Coordinates(float x, Float y) {
        this.setX(x);
        this.setY(y);
    }

    public Coordinates(){

    }

    /**
     * Валидирует все значения класса Coordinates на корректность
     * @return true если данные верны, false в обратном случае
     */
    public boolean validateValues() {
        return (getX() > 766) || (getY() != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(getX(), that.getX()) == 0 && Objects.equals(getY(), that.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "(" + getX() + "; " + getY() + ")";
    }

    public float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
