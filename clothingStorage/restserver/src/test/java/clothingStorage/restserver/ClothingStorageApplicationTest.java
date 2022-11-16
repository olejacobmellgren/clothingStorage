package clothingStorage.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;


import clothingStorage.core.*;
import clothingStorage.json.ClothingStoragePersistence;

/* @ContextConfiguration(classes = { ClothingStorageController.class, ClothingStorageService.class, ClothingStorageApplication.class })*/
@SpringBootTest(classes = ClothingStorageController.class) //NOTE: fjerne denne eller @ContextConfiguration
@AutoConfigureMockMvc
public class ClothingStorageApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @LocalServerPort
	private int port;

    /* Variables for dummy storage */
    private Storage storage;
    private Clothing leviPantsM;
    private Clothing leviPantsL;
    private Clothing supremeShorts;

    @BeforeEach
    public void setup_dummyStorage(){
        storage = new Storage();
        leviPantsM = new Clothing("Pants", "Levi's", 'M', 199);
        leviPantsL = new Clothing("Pants", "Levi's", 'L', 200);
        supremeShorts = new Clothing("Shorts", "Supreme", 'S', 159);

        /*
        storage.addNewClothing(leviPantsM, 3);
        storage.addNewClothing(leviPantsL, 4);
        storage.addNewClothing(supremeShorts, 15);*/
    }


    @BeforeEach
    public void setup() throws Exception {
        objectMapper = ClothingStoragePersistence.createObjectMapper();

    }

    private String clothingStorageUrl(String... segments) {
        String url = "/clothingStorage";
        for (String segment : segments) {
        url = url + "/" + segment;
        }
        return url;
    }

    @Test
    public void testGet_clothingStorage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(clothingStorageUrl("names"))
            //.accept(MediaType.APPLICATION_JSON) cannot import Mimetype
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        try {
        Storage storage = objectMapper.readValue(result.getResponse().getContentAsString(), Storage.class);
        storage.addNewClothing(leviPantsM, 3);
        assertEquals("PantsLevi'sM", storage.getClothing("PantsLevi'sM"));
        } catch (JsonProcessingException e) {
        fail(e.getMessage());
        }
    }
    
    
    /*
    @Test
	public void contextLoads(){
		assertThat(controller).isNotNull();
	}

    @Test
	public void testGetClothingNames() throws Exception {
		try {
            MvcResult result = this.mockMvc.perform(get("/names")
                    // .accept(MediaType.APPLICATION_JSON) Unable to import MimeTypes for some
                    // reason
            ).andDo(print()).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(),
                    controller.getNames());
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}

   
    @Test
	public void testGetSortedClothingNames() throws Exception {
		try {
            MvcResult result = this.mockMvc.perform(get("/sortedNames")
                    // .accept(MediaType.APPLICATION_JSON) Unable to import MimeTypes for some
                    // reason
            ).andDo(print()).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(),
                    controller.getSortedNames());
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}

    @Test
	public void testGetSortedClothes() throws Exception {
		try {
            MvcResult result = this.mockMvc.perform(get("/sorted/{id}")
                    // .accept(MediaType.APPLICATION_JSON) Unable to import MimeTypes for some
                    // reason
            ).andDo(print()).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(),
                    controller.getSortedClothes("0"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}

    @Test
	public void testGetSortedClothesType() throws Exception {
		try {
            MvcResult result = this.mockMvc.perform(get("/sortedType/{type}")
                    // .accept(MediaType.APPLICATION_JSON) Unable to import MimeTypes for some
                    // reason
            ).andDo(print()).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(),
                    controller.getSortedClothesType("leviJeansL"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}*/
    
}
