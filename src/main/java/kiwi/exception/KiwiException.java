package kiwi.exception;

/**
 * Represents an exception specific to the Kiwi application.
 * This exception is thrown when there is an error during the execution of the program.
 */
public class KiwiException extends Exception {
    /**
     * Constructs a new KiwiException with the specified error message.
     *
     * @param errorMessage The error message to be associated with this exception.
     */
    public KiwiException(String errorMessage) {
        super(errorMessage);
    }
}
