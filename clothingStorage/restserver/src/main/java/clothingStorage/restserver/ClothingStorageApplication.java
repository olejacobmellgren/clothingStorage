package clothingStorage.restserver;

import clothingStorage.json.ClothingStoragePersistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import clothingStorage.json.ClothingStoragePersistence;

/**
 * The clothingStorageApplication.
 */
@SpringBootApplication
public class ClothingStorageApplication {
    
    @Bean
    public ObjectMapper objectMapperModule() {
        return ClothingStoragePersistence.createObjectMapper();
    }

    /**
     * Starts spring server.
     *
     * @param args launched by string
     */
    public static void main(String[] args) {
        SpringApplication.run(ClothingStorageApplication.class, args);
    }

    /**
     * Required for checkstyle to pass. Prevents springboot from breaking
     */
    public void dummy() {}
}