package missoncontrol;

/**
 * Exception raised if conversion error
 */
public class ConversionException extends Exception {
    /**
     * Default constructor
     */
    public ConversionException() {
        super();
    }

    /**
     * Exception with message
     * @param message Exception message
     */
    public ConversionException(String message) {
        super(message);
    }

    /**
     * Exception with message and original error
     * @param message Exception message
     * @param cause Underlying reason
     */
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
