package engine.engineException;

public class InvalidProportionException extends RuntimeException {

    private static final String MESSAGE = "Invalid proportion %f";

    public InvalidProportionException(double fraction) {
        super(String.format(MESSAGE, fraction));
    }
}