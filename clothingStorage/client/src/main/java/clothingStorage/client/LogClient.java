package clothingStorage.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import clothingStorage.core.Clothing;

/**
 * The LogClient.
 */
public class LogClient {
    /* Skriv metoder vi trenger
     * get
     * getAsync
     * post
     * postAsync
     * getClothing
     * getStorageHashMap
     * removeClothing
     * addClothing
     * updateClothing
     */

    /**
     * OK response value.
     */
    private static final int OK_CODE = 200;
    /**
     * URL to remote server.
     */
    private final String url;
    /**
     * Port to the remote server.
     */
    private final int port;

    /**
     * Constructs a LogClient from a builder.
     *
     * @param serverUrl  server base url.
     * @param serverPort server port.
     */
    public LogClient(final String serverUrl, final int serverPort) {
        this.url = serverUrl;
        this.port = serverPort;
    }

    public Clothing getClothing(final String id)
            throws URISyntaxException, InterruptedException,
            ExecutionException, ServerResponseException {
        
        HttpResponse<String> response = this.get("/api/v1/clothings/" + id);
        String jsonString = response.body();
        // hvordan ser jsonString ut???
        JSONObject jsonObject = new JSONObject(jsonString);

        String type = jsonObject.getString("type");
        String brand = jsonObject.getString("brand");
        char size = jsonObject.getString("size").charAt(0);
        double price = Integer.parseInt(jsonObject.getString("price"));
        double discount = Integer.parseInt(jsonObject.getString("discount"));

        Clothing clothing = new Clothing(type, brand, size, price);
        clothing.setDiscount(discount);
        return clothing;
    }

    public void addClothing(Clothing clothing)
            throws URISyntaxException, InterruptedException,
            ExecutionException, ServerResponseException {
        
        
    }
}
