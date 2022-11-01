package clothingStorage.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ClothingTest{

    Clothing clothing;

    @BeforeEach
    public void setUp() {
        clothing = new Clothing("Pants", "Nike", 'M', 150);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Pants", clothing.getType());
        assertEquals("Nike", clothing.getBrand());
        assertEquals('M', clothing.getSize());
        assertEquals(150, clothing.getPrice());
    }

    @Test
    public void testType() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setType("P4nts");
        }, "Threw IllegalArgumentException due to number in type");
        clothing.setType("Pants");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setType("Shoes");
        }, "Threw IllegalArgumentException since this is an invalid type");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setType("pants");
        }, "Threw IllegalArgumentException since input doesn't start with uppercase letter");

        clothing.setType("Pants");
        assertEquals("Pants", clothing.getType());
    }

    @Test
    public void testBrand() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setBrand("Gucci");
        }, "Threw IllegalArgumentException due to input not being a valid brand");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setBrand("nike");
        }, "Threw IllegalArgumentException due to incorrect spelling");

        clothing.setBrand("Nike");
        assertEquals("Nike", clothing.getBrand());
    }

    @Test
    public void testSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setSize('X');
        }, "Threw IllegalArgumentException since it is not a size in the store");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setSize('s');
        }, "Threw IllegalArgumentException due to incorrect spelling");

        clothing.setSize('S');
        assertEquals('S', clothing.getSize());
    }

    @Test
    public void testPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPrice(-10, false);
        }, "Threw IllegalArgumentException since price can't be negative");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPrice(0, false);
        }, "Threw IllegalArgumentException because price can't be zero");

        clothing.setPrice(49, false);
        assertEquals(49, clothing.getPrice());
        clothing.setPrice(30.55, false);
        assertEquals(30.55, clothing.getPrice());
    }

    @Test
    public void testSetPriceAfterAddedDiscount() {
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPriceAfterAddedDiscount(1.5);
        }, "Threw IllegalArgumentException since discount can't be over 1");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPriceAfterAddedDiscount(100);
        }, "Threw IllegalArgumentException since discount can't be 1");
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setPriceAfterAddedDiscount(-1);
        }, "Threw IllegalArgumentException since discount can't be below 0");
        assertThrows(IllegalStateException.class, () -> {
            clothing.setPriceAfterAddedDiscount(0.4);
            clothing.setPriceAfterAddedDiscount(0.3);
        }, "Threw IllegalStateException since clothing is already on discount");

        clothing.setPrice(150, true);
        clothing.setPriceAfterAddedDiscount(0.50);
        assertEquals(75, clothing.getPrice());
        clothing.setPrice(150, true);
        clothing.setPriceAfterAddedDiscount(0.90);
        assertEquals(15, clothing.getPrice());
        clothing.setPrice(150, true);
        clothing.setPriceAfterAddedDiscount(0.375);
        assertEquals(93.75, clothing.getPrice());
    }

    @Test
    public void testRemoveDiscount() {
        assertThrows(IllegalStateException.class, () -> {
            clothing.removeDiscount();
        }, "Threw IllegalStateException since clothing is not on discount");

        clothing.setPrice(150, true);
        clothing.setPriceAfterAddedDiscount(0.5);
        assertEquals(75, clothing.getPrice());
        assertEquals(0.5, clothing.getDiscount());
        clothing.removeDiscount();
        assertEquals(150, clothing.getPrice());
        assertEquals(0, clothing.getDiscount());
    }

    @Test
    public void testEqualsButDifferentPrice() {
        Clothing clothing2 = new Clothing("Pants", "Nike", 'M', 200);
        assertTrue(clothing.equalsButDifferentPrice(clothing2));
        clothing2.setType("Jacket");
        assertFalse(clothing.equalsButDifferentPrice(clothing2));
        clothing2.setSize('L');
        assertFalse(clothing.equalsButDifferentPrice(clothing2));
        clothing2.setBrand("Supreme");
        assertFalse(clothing.equalsButDifferentPrice(clothing2));
    }

    @Test
    public void testEqualsButDifferentSize() {
        Clothing clothing2 = new Clothing("Pants", "Nike", 'S', 150);
        assertTrue(clothing.equalsButDifferentSize(clothing2));
        clothing2.setType("Jacket");
        assertFalse(clothing.equalsButDifferentSize(clothing2));
        clothing2.setType("Pants");
        clothing2.setBrand("Supreme");
        assertFalse(clothing.equalsButDifferentSize(clothing2));
    }

    @Test
    public void testOnDiscount() {
        assertEquals(0, clothing.getDiscount());
        assertFalse(clothing.isOnDiscount());

        clothing.setPriceAfterAddedDiscount(0.5);
        assertEquals(0.5, clothing.getDiscount());
        assertTrue(clothing.isOnDiscount());

        clothing.setDiscount(0);
        assertEquals(0, clothing.getDiscount());
        
        assertThrows(IllegalArgumentException.class, () -> {
            clothing.setDiscount(2);
        }, "Threw IllegalArgumentException since input was not 0 or 1");
    }

    @Test
    public void testToString() {
        String output = "Pants" + "\n" + "   - Brand: Nike" + "\n" + "   - Size: M" + "\n" + "   - Price: 150.0,-";
        assertEquals(output, clothing.toString());
        clothing.setSize('S');
        output = "Pants" + "\n" + "   - Brand: Nike" + "\n" + "   - Size: S" + "\n" + "   - Price: 150.0,-";
        assertEquals(output, clothing.toString());
    }
}


