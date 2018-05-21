package engine.engineException;

public class InvalidLocusAmountException extends RuntimeException {

    private static final String MESSAGE = "You can't use a two point crosser with individuals with" +
            " a locus amount lesser than two.";

    public InvalidLocusAmountException() {
        super(String.format(MESSAGE));
    }
}
