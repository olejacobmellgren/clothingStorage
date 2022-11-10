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
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;

@SpringBootTest(classes = ClothingStorageController.class) //NOTE: fjerne denne eller @ContextConfiguration


@AutoConfigureMockMvc
@ContextConfiguration(classes = { ClothingStorageController.class, ClothingStorageService.class, ClothingStorageApplication.class })
@WebMvcTest
public class ClothingStorageApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    public static final String mockStorageAsString = """
        {"leviJeansL":"10","supremeShortsS":"8","adidasSocksS":"30","lacosteShirtM":"6","lacosteShortsS":"4"}
        """.strip();

    private ObjectMapper objectMapper; //NOTE: kan muligens fjernes etterhvert

    private static final String APPLICATION_JSON = "application/json"; //NOTE: vil vi linke til dette? Tror ikke denne er riktig mtp mappestruktur, se restapi doc til todolist

    @BeforeEach
    public void setup() throws Exception {
    objectMapper = ClothingStoragePersistence.createObjectMapper();
    }

    private String storageUrl(String... segments) {
    String url = "/" + ClothingStorageController.STORAGE_SERVICE_PATH;
    for (String segment : segments) {
      url = url + "/" + segment;
    }
    return url;
    }

    @Test
    public void testAppMainMethod() {
        ClothingStorageApplication.main();
    }

    
    @Test
    public void testAddItem() {
        try {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/storage/addItem")
                                    .content(String.format(mockStorageAsString, "")))
                    .andDo(print()).andExpect(status().isOk()).andReturn();
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testGet_clothingStorage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(storageUrl())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        try {
            /*Lage tomt hashmap her og legge til ting? */
            Storage storage = objectMapper.readValue(result.getResponse().getContentAsString(), Storage.class);
            Storage dummyStorage = new Storage();

        }  
        catch (JsonProcessingException e){
            fail(e.getMessage());
        }
    }
}
