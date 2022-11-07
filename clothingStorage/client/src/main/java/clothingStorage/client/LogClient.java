package clothingStorage.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;

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
     * getStorage
     * removeClothing
     * addClothing
     * updateClothing
     */

    /**
     * Port to the remote server.
     */
    private final URI endpointBaseUri;

    /**
     * Port to the remote server.
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * Port to the remote server.
     */
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * Accept header.
     */
    private static final String ACCEPT_HEADER = "Accept";

    /**
     * Content-type header.
     */
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    /**
     * Objectmapper.
     */
    private ObjectMapper objectMapper;

    /**
     * Storage.
     */
    private Storage storage;


    /**
     * Constructs a LogClient from a builder.
     * @throws URISyntaxException
     *
     */
    public LogClient() throws URISyntaxException {
        this.endpointBaseUri = new URI("http://localhost:8080/clothingStorage");
        objectMapper = ClothingStoragePersistence.createObjectMapper();
    }

    public Storage getStorage() {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            this.storage = objectMapper.readValue(response.body(), Storage.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return storage;
    }




    public Clothing getClothing(final String name) {
        Clothing clothing;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("/clothes/" + name))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            clothing = objectMapper.readValue(response.body(), Clothing.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return clothing;
    }

    public boolean addClothing(Clothing clothing, int quantity) throws JsonProcessingException {
        Boolean added;
        String json = objectMapper.writeValueAsString(clothing);
        System.out.println(json);
        
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("/clothes/" + clothing.getName()))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .PUT(BodyPublishers.ofString(json))
          .build();
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            added = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return added;
    }

    public boolean removeClothing(Clothing clothing) {
        Boolean removed;
        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("/clothes/" + clothing.getName()))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .DELETE()
                .build();
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            removed = objectMapper.readValue(response.body(), Boolean.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return removed;
    }

    public boolean updateClothing(Clothing clothing) throws JsonProcessingException {
        Boolean updated;
        String json = objectMapper.writeValueAsString(clothing);
        
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("/clothes/" + clothing.getName()))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .PUT(BodyPublishers.ofString(json))
          .build();
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            updated = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updated;
    }

    public static void main(String[] args) throws URISyntaxException, JsonProcessingException {
        Clothing clothing = new Clothing("Pants", "Nike", 'M', 43);
        LogClient logClient = new LogClient();
        logClient.addClothing(clothing, 5);
        
    }

}
