package clothingStorage.json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.HashMap;


import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

class StorageSerializer extends JsonSerializer<Storage> {

    /*
    * format: 
    * {
    *     "clothes": [
    *         {
    *             "name": "Pants",
    *             "brand": "Nike",
    *             "size": "M",
    *             "price": 99.5,
    *             "discount": 0.5
    *         },
    *         {
    *             "quantity": 1
    *         },
    *         {
    *             "name": "Top",
    *             "brand": "Adidas",
    *             "size": "S",
    *             "price": 59.99,
    *             "discount": 0.9
    *         },
    *         {
    *             "quantity": 5
    *         }
    *     ]
    * }
    */

    @Override
    public void serialize(Storage storage, JsonGenerator jsonGen,
        SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        if (storage instanceof Storage) {
            jsonGen.writeArrayFieldStart("clothes");
            for (HashMap.Entry<Clothing, Integer> set : storage.getAllClothes().entrySet()) {
                jsonGen.writeObject(set.getKey());
                jsonGen.writeStartObject();
                jsonGen.writeNumberField("quantity", set.getValue());
                jsonGen.writeEndObject();
            }
            jsonGen.writeEndArray();
        }
        jsonGen.writeEndObject();
    }
}

