package exceptions;

public class TooLittlePointsException extends Exception {
    public TooLittlePointsException() {
        super("Слишком мало точек. Должно быть от 8 до 12");
    }
}
