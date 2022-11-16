package clothingStorage.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import clothingStorage.core.*;
import clothingStorage.json.ClothingStoragePersistence;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { ClothingStorageController.class, ClothingStorageService.class, ClothingStorageApplication.class })
@WebMvcTest
public class ClothingStorageApplicationTest {
    
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Clothing pantsNike = new Clothing("Pants", "Nike", 'M', 30);
    private Clothing pantsAdidas = new Clothing("Pants", "Adidas", 'S', 99);
    private Clothing jacketLacoste = new Clothing("Jacket", "Lacoste", 'L', 13);

    @BeforeEach
    public void setupStorage() throws Exception{

        objectMapper = ClothingStoragePersistence.createObjectMapper();

        if (jacketLacoste.getDiscount() != 0) {
           jacketLacoste.setDiscount(0.5);
        }
        
        this.mockMvc.perform(MockMvcRequestBuilders.put("/clothingStorage/clothes/PantsNikeM").contentType("application/json").content(objectMapper.writeValueAsString(pantsNike)));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/clothingStorage/clothes/PantsAdidasS").contentType("application/json").content(objectMapper.writeValueAsString(pantsAdidas)));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/clothingStorage/clothes/JacketLacosteL").contentType("application/json").content(objectMapper.writeValueAsString(jacketLacoste)));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/clothingStorage/quantity/PantsNikeM").contentType("application/x-www-form-urlencoded").content("quantity=" + uriParam(String.valueOf(4))));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/clothingStorage/quantity/PantsAdidasS").contentType("application/x-www-form-urlencoded").content("quantity=" + uriParam(String.valueOf(9))));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/clothingStorage/quantity/JacketLacosteL").contentType("application/x-www-form-urlencoded").content("quantity=" + uriParam(String.valueOf(6))));

        // har nå lagt til 3 Clothing-objekter med tilhørende quantity til restserver vha mockMvc.
    }

    @Test
    public void testGetStorage(){
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();

            Storage storage = objectMapper.readValue(result.getResponse().getContentAsString(), Storage.class);

            Clothing nikePants = storage.getClothing("PantsNikeM");
            Clothing adidasPants = storage.getClothing("PantsAdidasS");
            Clothing lacosteJacket = storage.getClothing("JacketLacosteL");

            assertEquals(4, storage.getQuantity(nikePants));
            assertEquals(9, storage.getQuantity(adidasPants));
            assertEquals(6, storage.getQuantity(lacosteJacket));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetClothing() { // denne testen ser bra ut, tror den funker
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/clothes/PantsNikeM")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
                                      
        Clothing nikePants = objectMapper.readValue(result.getResponse().getContentAsString(), Clothing.class);
        
        assertEquals("Pants", nikePants.getType());
        assertEquals("Nike", nikePants.getBrand());
        assertEquals('M', nikePants.getSize());
        assertEquals(30, nikePants.getPrice());
        assertEquals(0.0, nikePants.getDiscount());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPutClothing() {
        Clothing pantsNike2 = new Clothing("Pants", "Nike", 'M', 40);
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.put("/clothingStorage/clothes/PantsNikeM")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(pantsNike2))) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        boolean bool = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        assertTrue(bool);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveClothing() {
        try {
            MvcResult result1 = this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/clothingStorage/clothes/JacketLacosteL"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
            MvcResult result2 = this.mockMvc.perform(
                MockMvcRequestBuilders.put("/clothingStorage/clothes/JacketLacosteL")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(jacketLacoste))) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
            MvcResult result3 = this.mockMvc.perform(
                MockMvcRequestBuilders.put("/clothingStorage/quantity/JacketLacosteL")
                                      .contentType("application/x-www-form-urlencoded")
                                      .content("quantity=" + uriParam(String.valueOf(6))))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
            boolean bool1 = objectMapper.readValue(result1.getResponse().getContentAsString(), Boolean.class);
            boolean bool2 = objectMapper.readValue(result2.getResponse().getContentAsString(), Boolean.class);
            boolean bool3 = objectMapper.readValue(result3.getResponse().getContentAsString(), Boolean.class);
            assertTrue(bool1);
            assertTrue(bool2);
            assertTrue(bool3);
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

    @Test
    public void testGetQuantity() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/quantity/PantsNikeM")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        int amount = objectMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        assertEquals(4, amount);
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPutQuantity() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.put("/clothingStorage/quantity/PantsNikeM")
                                      .contentType("application/x-www-form-urlencoded")
                                      .content("quantity=" + uriParam(String.valueOf(10)))) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
            MvcResult result2 = this.mockMvc.perform(
                MockMvcRequestBuilders.put("/clothingStorage/quantity/PantsNikeM")
                                      .contentType("application/x-www-form-urlencoded")
                                      .content("quantity=" + uriParam(String.valueOf(4)))) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        boolean bool = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        boolean bool2 = objectMapper.readValue(result2.getResponse().getContentAsString(), Boolean.class);
        assertTrue(bool);
        assertTrue(bool2);
            
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

    @Test
    public void testGetNames() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/names")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<String> names = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
        assertEquals("PantsNikeM", names.get(0));
        assertEquals("PantsAdidasS", names.get(1));
        assertEquals("JacketLacosteL", names.get(2));
            
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

    @Test
    public void testGetSortedNames() {
        try {
            this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/sorted/0")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/sortedNames")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
            List<String> sortedClothes = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
            assertEquals("JacketLacosteL", sortedClothes.get(0));
            assertEquals("PantsNikeM", sortedClothes.get(1));
            assertEquals("PantsAdidasS", sortedClothes.get(2));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetSortedClothes() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/sorted/0")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<String> sortedClothes = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
        assertEquals("Jacket; Lacoste; 13.0kr", sortedClothes.get(0));
        assertEquals("Pants; Nike; 30.0kr", sortedClothes.get(1));
        assertEquals("Pants; Adidas; 99.0kr", sortedClothes.get(2));
            
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

     @Test
    public void testGetSortedType() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/sortedType/Pants")) 
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<String> sortedPants = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
        assertEquals("Pants; Nike; 30.0kr", sortedPants.get(0));
        assertEquals("Pants; Adidas; 99.0kr", sortedPants.get(1));
        
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

    @Test
	public void testGetSortedBrand() throws Exception {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/sortedBrand/Nike"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<String> sortedNike = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
        assertEquals("Pants; Nike; 30.0kr", sortedNike.get(0));

        } catch (Exception e) {
             fail(e.getMessage());
        }
	}
    
    @Test
    public void testGetStorageDisplay() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/storageDisplay"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<String> storageDisplay = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
        assertEquals("Pants; Nike; M; 4", storageDisplay.get(0));
        assertEquals("Pants; Adidas; S; 9", storageDisplay.get(1));
        assertEquals("Jacket; Lacoste; L; 6", storageDisplay.get(2));

        } catch (Exception e) {
             fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetPriceDisplay() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/priceDisplay"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<String> priceDisplay = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), String[].class));
        assertEquals("Pants; Nike; 30.0kr", priceDisplay.get(0));
        assertEquals("Pants; Adidas; 99.0kr", priceDisplay.get(1));
        assertEquals("Jacket; Lacoste; 13.0kr", priceDisplay.get(2));
        
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

    @Test
    public void testGetQuantitiesForTypeAndSizes() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/stats/chartData/Pants"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();
        List<Integer> quantitySizes = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Integer[].class));
        assertEquals(9, quantitySizes.get(0));
        assertEquals(4, quantitySizes.get(1));
        assertEquals(0, quantitySizes.get(2));
        
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetTotalValue() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/stats/totalValue"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();

        int totalValue = objectMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        assertEquals(1089, totalValue);
            
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }

    @Test
    public void testGetTotalQuantity() {
        try {
            MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/clothingStorage/stats/totalQuantity"))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andReturn();

        int totalQuantity = objectMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        assertEquals(19, totalQuantity);
            
        } catch (Exception e) {
             fail(e.getMessage());
        }
    }
    
    private String uriParam(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }          
}
