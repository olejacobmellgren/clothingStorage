package clothingStorage.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;


@AutoConfigureMockMvc
@ContextConfiguration(classes = { ClothingStorageController.class, ClothingStorageService.class, ClothingStorageApplication.class })
@WebMvcTest
public class ClothingStorageApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static final String APPLICATION_JSON = "application/json"; //NOTE: vil vi linke til dette? Tror ikke denne er riktig mtp mappestruktur, se restapi doc til todolist

    @BeforeEach
    public void setup() throws Exception {
    objectMapper = ClothingStoragePersistence.createObjectMapper();
    }

    private String todoUrl(String... segments) {
    String url = "/" + ClothingStorageController.STORAGE_SERVICE_PATH;
    for (String segment : segments) {
      url = url + "/" + segment;
    }
    return url;
    }

    @Test
    public void testGet_clothingStorage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(todoUrl())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        try {
            /*Lage tomt hashmap her og legge til ting? */
            Storage storage = objectMapper.readValue(result.getResponse().getContentAsString(), Storage.class);
            
        }  
        catch (JsonProcessingException e){
            fail(e.getMessage());
        }
    }
}
