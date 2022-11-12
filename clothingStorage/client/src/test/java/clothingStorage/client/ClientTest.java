package clothingStorage.client;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ClientTest {
    

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    private StorageClient storageClient;
  
    @BeforeEach
    public void startWireMockServerAndSetup() throws URISyntaxException {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        storageClient = new StorageClient();
    }

    @Test
    public void testGetStorage() {
        stubFor(get(urlEqualTo("/clothingStorage/"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"clothes\":[{\"type\":\"Pants\",\"brand\":\"Nike\",\"size\":\"M\",\"price\":30.0,\"discount\":0.0},{\"quantity\":4}," +
                                        "{\"type\":\"Pants\",\"brand\":\"Adidas\",\"size\":\"S\",\"price\":99.0,\"discount\":0.0},{\"quantity\":9}," +
                                        "{\"type\":\"Jacket\",\"brand\":\"Lacoste\",\"size\":\"L\",\"price\":13.0,\"discount\":0.5},{\"quantity\":6}]}")));

        Storage storage = storageClient.getStorage();
        Clothing nikePants = storage.getClothing("PantsNikeM");
        Clothing adidasPants = storage.getClothing("PantsAdidasS");
        Clothing lacosteJacket = storage.getClothing("JacketLacosteL");

        assertEquals(4, storage.getQuantity(nikePants));
        assertEquals(9, storage.getQuantity(adidasPants));
        assertEquals(6, storage.getQuantity(lacosteJacket));
    }

    @Test
    public void testGetClothing() {
        stubFor(get(urlEqualTo("/clothingStorage/clothes/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"type\":\"Pants\",\"brand\":\"Nike\",\"size\":\"M\",\"price\":43.0,\"discount\":0.0}")));

        Clothing nikePants = storageClient.getClothing("PantsNikeM");
        assertEquals("Pants", nikePants.getType());
        assertEquals("Nike", nikePants.getBrand());
        assertEquals('S', nikePants.getSize());
        assertEquals(43, nikePants.getPrice());
        assertEquals(0.0, nikePants.getDiscount());
    }

    @Test
    public void testPutClothing() {
        stubFor(put(urlEqualTo("/clothingStorage/clothes/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"type\":\"Pants\",\"brand\":\"Nike\",\"size\":\"M\",\"price\":43.0,\"discount\":0.0}")));

        try {
            Clothing nikePants = new Clothing("Pants", "Nike", 'M', 43);
            boolean bool = storageClient.putClothing(nikePants);
            assertEquals(true, bool);
        } catch (JsonProcessingException e) {
            fail();
        }
    }

    @Test
    public void testRemoveClothing() {
        stubFor(delete(urlEqualTo("/clothingStorage/clothes/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("true")));

        Clothing nikePants = new Clothing("Pants", "Nike", 'M', 43);
        boolean bool = storageClient.removeClothing(nikePants.getName());
        assertEquals(true, bool);
    }

    @Test
    public void testGetQuantity() {
        stubFor(get(urlEqualTo("/clothingStorage/quantity/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"quantity\":10}")));

        int amount = storageClient.getQuantity("PantsNikeM");
        assertEquals(10, amount);
    }

    @Test
    public void testPutQuantity() {
        stubFor(put(urlEqualTo("/clothingStorage/quantity/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"quantity\":20}")));

        Clothing nikePants = new Clothing("Pants", "Nike", 'M', 43);
        boolean bool = storageClient.putQuantity(nikePants.getName(), 20);
        assertEquals(true, bool);
    }

    @Test
    public void testGetNames() {
        stubFor(get(urlEqualTo("/clothingStorage/names"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[\"PantsNikeM\",\"PantsAdidasS\",\"JacketLacosteL\"]")));

        List<String> names = new ArrayList<>(storageClient.getNames());
        assertEquals("PantsNikeM", names.get(0));
        assertEquals("JacketLacosteL", names.get(2));
    }

    @Test
    public void testGetSortedNames() {
        stubFor(get(urlEqualTo("/clothingStorage/sortedNames"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[\"JacketLacosteL\",\"JacketNikeM\"]")));

        List<String> sortedNames = new ArrayList<>(storageClient.getSortedNames());
        assertEquals("JacketLacosteL", sortedNames.get(0));
        assertEquals("JacketNikeM", sortedNames.get(1));
    }

    @Test
    public void testGetSorted() {
        stubFor(get(urlEqualTo("/clothingStorage/sorted/0"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[\"Jacket; Lacoste; 13.0kr\",\"Pants; Nike; 30.0kr\",\"Pants; Adidas; 99.0kr\"]")));

        List<String> sortedClothes = new ArrayList<>(storageClient.getSorted(0));
        assertEquals("Jacket; Lacoste; 13.0kr", sortedClothes.get(0));
        assertEquals("Pants; Nike; 30.0kr", sortedClothes.get(1));
    }

    @Test
    public void testGetSortedType() {
        stubFor(get(urlEqualTo("/clothingStorage/sortedType/Pants"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[\"Pants; Nike; 30.0kr\",\"Pants; Adidas; 99.0kr\"]")));

        List<String> sortedPants = new ArrayList<>(storageClient.getSorted(0));
        assertEquals("Pants; Nike; 30.0kr", sortedPants.get(0));
        assertEquals("Pants; Adidas; 99.0kr", sortedPants.get(1));
    }

    @Test
    public void testGetSortedBrand() {
        stubFor(get(urlEqualTo("/clothingStorage/sortedBrand/Nike"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[\"Pants; Nike; 30.0kr\",\"Pants; Adidas; 99.0kr\"]")));

        List<String> sortedPants = new ArrayList<>(storageClient.getSorted(0));
        assertEquals("Pants; Nike; 30.0kr", sortedPants.get(0));
        assertEquals("Pants; Adidas; 99.0kr", sortedPants.get(1));
    }

    @Test
    public void testGetStorageDisplay() {
        stubFor(get(urlEqualTo("/clothingStorage/quantity/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"quantity\":10}")));

        int amount = storageClient.getQuantity("PantsNikeM");
        assertEquals(10, amount);
    }

    @Test
    public void testGetPriceDisplay() {
        stubFor(get(urlEqualTo("/clothingStorage/quantity/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"quantity\":10}")));

        int amount = storageClient.getQuantity("PantsNikeM");
        assertEquals(10, amount);
    }


}