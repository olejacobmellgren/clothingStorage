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
     * Constructs a StorageClient from a builder.
     *
     * @throws URISyntaxException if string could not be parsed to URI
     */
    public StorageClient() throws URISyntaxException {
        this.endpointBaseUri = new URI("http://localhost:8080/clothingStorage/");
        objectMapper = ClothingStoragePersistence.createObjectMapper();
    }

    /**
     * Gets the storage with clothings.
     *
     * @return the storage
     */
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
     * Gets Clothing from restserver.
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

        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("clothes/").resolve(uriParam(clothing.getName())))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .PUT(BodyPublishers.ofString(json))
                .build();
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            updated = objectMapper.readValue(response.body(), Boolean.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updated;
    }

    /**
     * Removes Clothing from restserver.
     *
     * @param name to be put
     * @return boolean true if success, false if not
     */
    public boolean removeClothing(String name) {
        Boolean removed;
        try {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
                .resolve("clothes/" + name))
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

    /**
     * Gets quantity from restserver.
     *
     * @param name to be retrieve quantity for
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
     * @param name for clothing corresponding to quantity
     * @param quantity to be updated
     * @return boolean true if success, false if not
     */
    public boolean putQuantity(String name, int quantity) {
        Boolean updated;

        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("quantity/" + name))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
            .PUT(BodyPublishers.ofString("quantity=" + uriParam(String.valueOf(quantity))))
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

    /**
     * Gets the names for clothings in the storage.
     *
     * @return the names for clothings in storage
     */
    public List<String> getNames() {
        List<String> names;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("names"))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            names = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    /**
     * Gets the names for clothings in the storage when sorted.
     *
     * @return the names for clothings in storage when sorted
     */
    public List<String> getSortedNames() {
        List<String> names;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("sortedNames"))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            names = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    /**
     * Gets the clothings in the storage when sorted in a specific way.
     *
     * @param id the id for type of sorting
     * @return the clothings in storage when sorted
     */
    public List<String> getSorted(int id) {
        List<String> list;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("sorted/" + id))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            list = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Gets the clothings in the storage when sorted by a type.
     *
     * @param type the type of clothing for sorting
     * @return the clothings in storage when sorted on a type
     */
    public List<String> getSortedType(String type) {
        List<String> list;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("sortedType/" + type))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            list = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Gets the clothings in the storage when sorted by a brand.
     *
     * @param brand the brand for sorting
     * @return the clothings in storage when sorted on a brand
     */
    public List<String> getSortedBrand(String brand) {
        List<String> list;
        HttpRequest request = HttpRequest
            .newBuilder(endpointBaseUri.resolve("sortedBrand/" + brand))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            list = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Gets the display for the storage on storage-page.
     *
     * @return the clothings to be displayed on storage-page
     */
    public List<String> getStorageDisplay() {
        List<String> list;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("storageDisplay"))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            list = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Gets the display for the storage on storage-page.
     *
     * @return the clothings to be displayed on price-page
     */
    public List<String> getPriceDisplay() {
        List<String> list;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("priceDisplay"))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            list = Arrays.asList(objectMapper.readValue(response.body(), String[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Gets the quantity for a type of clothing.
     *
     * @param type the type of clothing to get quantity for
     * @return amount of clothing for that type
     */
    public List<Integer> getQuantitiesForTypeAndSizes(String type) {
        List<Integer> quantities;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("stats/chartData/" + type))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            quantities = Arrays.asList(objectMapper.readValue(response.body(), Integer[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return quantities;
    }

    /**
     * Gets total value of storage.
     *
     * @return total quantity from restserver
     */
    public double getTotalValue() {
        double value;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("stats/totalValue"))
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            value = objectMapper.readValue(response.body(), Double.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    /**
     * Gets quantity from restserver.
     *
     * @return quantity from restserver
     */
    public int getTotalQuantity() {
        int quantity;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri
            .resolve("stats/totalQuantity"))
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
     * Converts string to UTF_8 format.
     *
     * @param string to be converted
     * @return string in UTF_8 format
     */
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
        logClient.putQuantity(clothing2.getName(), 9);
        //Clothing clothing4 = logClient.getClothing("JacketLacosteL");
        //Storage storage = logClient.getStorage();
        logClient.getNames();
        logClient.getPriceDisplay();
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