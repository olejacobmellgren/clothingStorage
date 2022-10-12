package clothingStorage.json.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

class StorageDeserializer extends JsonDeserializer<Storage> {

  private ClothingDeserializer clothingDeserializer = new ClothingDeserializer();
  /*
   * format: { "clothes": [ ... ] }
   */

  @Override
  public Storage deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }
  Storage deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode objectNode) {
      JsonNode itemsNode = objectNode.get("clothes");
      boolean hasItems = itemsNode instanceof ArrayNode;
      Storage storage = new Storage();

      Clothing clothing = new Clothing("Jeans", "Nike", 'M', 199); 
      boolean isClothing = true;

      if (hasItems) {
        for (JsonNode elementNode : ((ArrayNode) itemsNode)) {
          if (isClothing) {
            clothing = clothingDeserializer.deserialize(elementNode);
            isClothing = false;
          } else {
              JsonNode quantityNode = elementNode.get("quantity");
              int quantity = quantityNode.asInt();
              storage.addNewClothing(clothing, quantity);
              isClothing = true;
          }
        }
      }
      return storage;
    }
    return null;
  }   
}



