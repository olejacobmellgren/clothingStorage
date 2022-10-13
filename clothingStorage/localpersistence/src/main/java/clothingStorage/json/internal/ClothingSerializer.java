package clothingStorage.json.internal;

import clothingStorage.core.Clothing;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;


class ClothingSerializer extends JsonSerializer<Clothing> {

    /*
    * format: 
    * { 
    *    "name": "...", 
    *    "brand": ..., 
    *    "size": ..., 
    *    "price": ..., 
    *    "discount": ...}
    */

    @Override
    public void serialize(Clothing clothing, JsonGenerator jsonGen,
        SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", clothing.getName());
        jsonGen.writeStringField("brand", clothing.getBrand());
        jsonGen.writeStringField("size", Character.toString(clothing.getSize()));
        jsonGen.writeNumberField("price", clothing.getPrice());
        jsonGen.writeNumberField("discount", clothing.getDiscount());
        jsonGen.writeEndObject();
    }
}

