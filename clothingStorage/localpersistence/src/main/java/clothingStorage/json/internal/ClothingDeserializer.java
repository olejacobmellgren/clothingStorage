package clothingStorage.json.internal;

import clothingStorage.core.Clothing;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;


class ClothingDeserializer extends JsonDeserializer<Clothing> {

    @Override
    public Clothing deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * Deserializes JsonNode to Clothing object.
     *
     * @param jsonNode to be deserialized
     * @return Clothing object
     */
    Clothing deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            String type;
            JsonNode typeNode = objectNode.get("type");
            type = typeNode.asText();
            
            String brand;
            JsonNode brandNode = objectNode.get("brand");
            brand = brandNode.asText();
            
            char size;
            JsonNode sizeNode = objectNode.get("size");
            size = sizeNode.asText().charAt(0);

            double price;
            JsonNode priceNode = objectNode.get("price");
            price = priceNode.asDouble();

            double discount;
            JsonNode discountNode = objectNode.get("discount");
            discount = discountNode.asDouble();

            Clothing clothing = new Clothing(type, brand, size, price);
            clothing.setDiscount(discount);
            return clothing;
        }
        return null;
    }   
}

