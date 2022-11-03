package clothingStorage.client;

import java.util.Map;

public final class HttpResponses {
    /**
     * Possible HTTP-responses.
     */
    static final Map<Integer, String> RESPONSES = Map.of(
            200, "OK",
            400, "Bad Request",
            404, "Not Found",
            500, "Internal Server Error"
    );

    private HttpResponses() { }

    /**
     * Converts code to correct HTTP-response.
     *
     * @param code code
     * @return HTTP-response
     */
     public static String getReponse(final int code) {
        return RESPONSES.get(code);
     }
}