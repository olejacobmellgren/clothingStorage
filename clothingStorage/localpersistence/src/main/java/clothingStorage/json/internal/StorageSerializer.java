package clothingStorage.json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.HashMap;


import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

class StorageSerializer extends JsonSerializer<Storage> {

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

