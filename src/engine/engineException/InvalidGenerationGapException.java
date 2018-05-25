package engine.engineException;

public class InvalidGenerationGapException extends RuntimeException {

    private static final String MESSAGE = "There are not enough children to fill the generation gap";

    public InvalidGenerationGapException() {
        super(String.format(MESSAGE));
    }
}