package clothingStorage.client;

public class ServerResponseException extends Exception {
    /**
     * HTTP-response code.
     */
    private final int code;

    /**
     * A throwable exception related to server issues.
     *
     * @param message the exception message.
     * @param responseCode the response code.
     */
    public ServerResponseException(final String message,
                                   final int responseCode) {
        super(message);
        this.code = responseCode;
    }

    /**
     * Getter for HTTP-response-code.
     *
     * @return the response code.
     */
    public int getCode() {
        return this.code;
    }
}