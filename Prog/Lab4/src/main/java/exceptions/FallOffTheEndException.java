package exceptions;

public class FallOffTheEndException extends RuntimeException{
    public FallOffTheEndException(String message) {
        super(message);
    }
}
