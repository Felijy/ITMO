package exceptions;

public class TooManyPointsException extends RuntimeException {
    public TooManyPointsException() {
        super("Слишком много точек, должно быть от 8 до 12");
    }
}
