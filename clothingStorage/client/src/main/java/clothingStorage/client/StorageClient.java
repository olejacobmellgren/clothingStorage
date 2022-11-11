package clothingStorage.client;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * The StorageClient.
 */
public class StorageClient {
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
     * URI base path.
     */
    private final URI endpointBaseUri;

    /**
     * Application json for requests.
     */
    private static final String APPLICATION_JSON = "application/json";

    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * Accept header for requests.
     */
    private static final String ACCEPT_HEADER = "Accept";

    /**
     * Content-type header for requests.
     */
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    /**
     * Objectmapper.
     */
    private ObjectMapper objectMapper;


    /**
     * Constructs a LogClient from a builder.
     *
     * @throws URISyntaxException if string could not be parsed to URI
     */
    public StorageClient() throws URISyntaxException {
        this.endpointBaseUri = new URI("http://localhost:8080/clothingStorage/");
        objectMapper = ClothingStoragePersistence.createObjectMapper();
    }

    public Storage getStorage() {
        Storage storage;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            storage = objectMapper.readValue(response.body(), Storage.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return storage;
    }

    /**
     * gets Clothing from restserver.
     *
     * @param name of clothing to be retrieved
     * @return Clothing item
     */
    public Clothing getClothing(final String name) {
        Clothing clothing;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("clothes/" + name))
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

    /**
     * Puts Clothing to restserver.
     *
     * @param clothing to be put
     * @return boolean true if put, false if not
     */
    public boolean putClothing(Clothing clothing) throws JsonProcessingException {
        Boolean updated;
        String json = objectMapper.writeValueAsString(clothing);
        System.out.println(json);

        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("clothes/").resolve(uriParam(clothing.getName())))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .PUT(BodyPublishers.ofString(json))
                .build();
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            updated = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updated;
    }

    /**
     * Removes Clothing from restserver.
     *
     * @param clothing to be removed
     * @return boolean true if success, false if not
     */
    public boolean removeClothing(Clothing clothing) {
        Boolean removed;
        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("clothes/" + clothing.getName()))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .DELETE()
                .build();
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            removed = objectMapper.readValue(response.body(), Boolean.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return removed;
    }

    /**
     * Gets quantity from restserver.
     *
     * @param name to be retrieved
     * @return quantity from restserver
     */
    public int getQuantity(final String name) {
        int quantity;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("quantity/" + name))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            quantity = objectMapper.readValue(response.body(), Integer.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return quantity;
    }

    /**
     * Puts quantity to restserver.
     *
     * @param clothing corresponding to quantity
     * @param quantity to be updated
     * @return boolean true if success, false if not
     */
    public boolean putQuantity(Clothing clothing, int quantity) {
        Boolean updated;

        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("quantity/" + clothing.getName()))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
            .PUT(BodyPublishers.ofString("quantity=" + uriParam(String.valueOf(quantity))))
            .build();
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            
            updated = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updated;
    }

    /**
     * Removes quantity from restserver.
     *
     * @param clothing corresponding to quantity
     * @return boolean true if success, false if not
     */
    public boolean removeQuantity(Clothing clothing) {
        Boolean removed;
        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("quantity/" + clothing.getName()))
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

    public List<String> getNames() {
        List<String> names;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("names/"))
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            String namesString= response.body();
            namesString = namesString.replace("[", "");
            namesString = namesString.replace("]", "");
            names = new ArrayList<String>(Arrays.asList(namesString.split(",")));
            System.out.println(names);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    public List<String> getSorted(int id) {
        List<String> names;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("sorted/" + id))
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            String namesString= response.body();
            namesString = namesString.replace("[", "");
            namesString = namesString.replace("]", "");
            names = new ArrayList<String>(Arrays.asList(namesString.split(",")));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    public List<String> getSortedType(String type) {
        List<String> names = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("sortedType/" + type))
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            String namesString= response.body();
            namesString = namesString.replace("[", "");
            namesString = namesString.replace("]", "");
            names = new ArrayList<String>(Arrays.asList(namesString.split(",")));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    public List<String> getSortedBrand(String brand) {
        List<String> names = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("sortedBrand/" + brand))
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            String namesString= response.body();
            namesString = namesString.replace("[", "");
            namesString = namesString.replace("]", "");
            names = new ArrayList<String>(Arrays.asList(namesString.split(",")));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    private String uriParam(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }


    /**
     * wtf.
     *
     * @param args fd
     * @throws URISyntaxException fd 
     * @throws JsonProcessingException fd
     */
    public static void main(String[] args) throws URISyntaxException, JsonProcessingException {
        Clothing clothing = new Clothing("Pants", "Nike", 'M', 43);
        StorageClient logClient = new StorageClient();
        logClient.putClothing(clothing);
        Clothing clothing2 = new Clothing("Pants", "Adidas", 'S', 99);
        logClient.putClothing(clothing2);
        Clothing clothing3 = new Clothing("Jacket", "Lacoste", 'L', 13);
        clothing3.setDiscount(0.5);
        logClient.putClothing(clothing3);
        logClient.putQuantity(clothing2, 9);
        Clothing clothing4 = logClient.getClothing("JacketLacosteL");
        System.out.println(clothing4.toString());
        Storage storage = logClient.getStorage();
        System.out.println(storage.toString());
        logClient.getNames();
    }
}

/*

http:/localhost:8080/clothingStorage/clothes/name
http:/localhost:8080/clothingStorage/quantities/name

Storage storage = client.getStorage();
storage.increaseQuantity(Clothing clothing);
client.putStorage();
*/

//http:/localhost:8080/clothingStorage/names/{name}