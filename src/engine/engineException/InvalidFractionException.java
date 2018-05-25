package engine.engineException;

public class InvalidFractionException extends RuntimeException {

    private static final String MESSAGE = "Invalid fraction %f";

    public InvalidFractionException(double fraction) {
        super(String.format(MESSAGE, fraction));
    }
}