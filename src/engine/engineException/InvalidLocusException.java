package engine.engineException;

public class InvalidLocusException extends RuntimeException {

    private static final String MESSAGE = "Locus %d is out of bounds";

    public InvalidLocusException(int locus) {
        super(String.format(MESSAGE, locus));
    }

}
