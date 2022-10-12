package clothingStorage.json.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import clothingStorage.core.Clothing;

class ClothingDeserializer extends JsonDeserializer<Clothing> {

  @Override
  public Clothing deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Clothing deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode objectNode) {
      String name;
      String brand;
      char size;
      double price;
      double discount;

      JsonNode nameNode = objectNode.get("name");
      name = nameNode.asText();
      
      JsonNode brandNode = objectNode.get("brand");
      brand = brandNode.asText();
      
      JsonNode sizeNode = objectNode.get("size");
      size = sizeNode.asText().charAt(0);

      JsonNode priceNode = objectNode.get("price");
      price = priceNode.asDouble();

      JsonNode discountNode = objectNode.get("discount");
      discount = discountNode.asDouble();

      Clothing clothing = new Clothing(name, brand, size, price);
      clothing.setSale(discount);
      return clothing;
    }
    return null;
  }   
}

