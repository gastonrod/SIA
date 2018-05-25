package engine.engineException;

public class InvalidProbabilityException extends RuntimeException {

    private static final String MESSAGE = "Invalid probability %f";

    public InvalidProbabilityException(double probability) {
        super(String.format(MESSAGE, probability));
    }

}
