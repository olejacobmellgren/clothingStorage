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
    format: 
    {
        "clothes" : [ {
            "type" : "Shirt",
            "brand" : "Adidas",
            "size" : "L",
            "price" : 453.0,
            "discount" : 0.0
        }, {
            "quantity" : 5
        }, {
            "type" : "Jacket",
            "brand" : "H&M",
            "size" : "M",
            "price" : 43.0,
            "discount" : 0.0
        }, {
            "quantity" : 7
        }, {
            "isSorted" : true
        } ],
        "sortedClothes" : [ {
            "type" : "Shirt",
            "brand" : "Adidas",
            "size" : "L",
            "price" : 453.0,
            "discount" : 0.0
        }, {
            "type" : "Jacket",
            "brand" : "H&M",
            "size" : "M",
            "price" : 43.0,
            "discount" : 0.0
        } ]
    }
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
        jsonGen.writeStartObject();
        jsonGen.writeBooleanField("isSorted", storage.getIsSortedClothes());
        jsonGen.writeEndObject();
        if (storage.getIsSortedClothes() == true) {
            jsonGen.writeEndArray();
            jsonGen.writeArrayFieldStart("sortedClothes");
            for (Clothing clothing : storage.getSortedClothings()) {
                jsonGen.writeObject(clothing);
            }
            jsonGen.writeEndArray();
        } else {
            jsonGen.writeEndArray();
        }
        jsonGen.writeEndObject();
    }
}

