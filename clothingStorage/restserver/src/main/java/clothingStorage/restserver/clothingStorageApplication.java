package clothingStorage.restserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
 * The clothingStorageApplication.
 */
public class clothingStorageApplication {
    

    /**
     * Starts spring server
     * 
     * @param args launched by string
     */
    public static void main(String[] args){
        SpringApplication.run(clothingStorageApplication, args);
    }

    /**
     * Required for checkstyle to pass. Prevents springboot from breaking
     */
    public void dummy() {}
}
