package clothingStorage.json.internal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

/**
 * A Jackson module for configuring JSON serialization of Storage instances.
 */
@SuppressWarnings("serial")
public class ClothingStorageModule extends SimpleModule {

    private static final String NAME = "ClothingStorageModule";

    /**
     * Initializes this ClothingStorageModule with appropriate serializers and deserializers.
     */
    public ClothingStorageModule() {
        super(NAME, Version.unknownVersion());
        addSerializer(Clothing.class, new ClothingSerializer());
        addDeserializer(Clothing.class, new ClothingDeserializer());

        addSerializer(Storage.class, new StorageSerializer());
        addDeserializer(Storage.class, new StorageDeserializer());
    }
}

