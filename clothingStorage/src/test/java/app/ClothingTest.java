package app;

/*import static org.junit.jupiter.api.Assertions.assertArrayEquals;*/

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClothingTest{
    Clothing clothing = new Clothing("jeans", "Nike", 'M', 199);
    
    @Test
    public void checkClothing(){
        Assertions.assertEquals("jeans", clothing.getName());
        Assertions.assertEquals("Nike", clothing.getBrand());
        Assertions.assertEquals('M', clothing.getSize());
        Assertions.assertEquals(199, clothing.getPrice());
    }

}


