package clothingStorage.json.internal;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.HashMap;

class StorageSerializer extends JsonSerializer<Storage> {

    /*
    * format: 
    * {
    *     "clothes": [
    *         {
    *             "type": "Pants",
    *             "brand": "Nike",
    *             "size": "M",
    *             "price": 199.0,
    *             "discount": 0.5
    *         },
    *         {
    *             "quantity": 1
    *         },
    *         {
    *             "type": "Shirt",
    *             "brand": "Adidas",
    *             "size": "S",
    *             "price": 599.9,
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
        jsonGen.writeArrayFieldStart("clothes");
        for (HashMap.Entry<Clothing, Integer> set : storage.getAllClothes().entrySet()) {
            jsonGen.writeObject(set.getKey());
            jsonGen.writeStartObject();
            jsonGen.writeNumberField("quantity", set.getValue());
            jsonGen.writeEndObject();
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }
}

