package clothingStorage.client;

import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import clothingStorage.core.Clothing;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestClient {
    

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
    public void testRemoveClothing() {
        stubFor(delete(urlEqualTo("/clothingStorage/clothes/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("true")));

        Clothing nikePants = new Clothing("Pants", "Nike", 'M', 43);
        boolean bool = storageClient.removeClothing(nikePants);
        assertEquals(true, bool);
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
        boolean bool = storageClient.putQuantity(nikePants, 20);
        assertEquals(true, bool);
    }

    @Test
    public void testRemoveQuantity() {
        stubFor(delete(urlEqualTo("/clothingStorage/quantity/PantsNikeM"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("true")));

        Clothing nikePants = new Clothing("Pants", "Nike", 'M', 43);
        boolean bool = storageClient.removeQuantity(nikePants);
        assertEquals(true, bool);
    }

}