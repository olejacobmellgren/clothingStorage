package clothingStorage.json.internal;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;


class StorageDeserializer extends JsonDeserializer<Storage> {

    private ClothingDeserializer clothingDeserializer = new ClothingDeserializer();
    
    @Override
    public Storage deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * Deserializes JsonNode to Storage object.
     *
     * @param jsonNode to be deserialized
     * @return Storage object
     */
    Storage deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            JsonNode itemsNode = objectNode.get("clothes");
            boolean hasItems = itemsNode instanceof ArrayNode;
            Storage storage = new Storage();

            Clothing clothing = new Clothing("Pants", "Nike", 'M', 199); 
            boolean isClothing = true;
            boolean isSortedClothing = false;

            if (hasItems) {
                itemsNode = (ArrayNode) itemsNode;
                for (int i = 0; i < itemsNode.size(); i++) {
                    if (i == itemsNode.size() - 1) {
                        JsonNode isClothing2 = itemsNode.get(i).get("isSorted");
                        boolean isClothingBool = isClothing2.asBoolean();
                        storage.setIsSortedPricePage(isClothingBool);
                        if (isClothingBool == true) {
                            isSortedClothing = true;
                        }
                    } else if (isClothing) {
                        clothing = clothingDeserializer.deserialize(itemsNode.get(i));
                        isClothing = false;
                    } else {
                        JsonNode quantityNode = itemsNode.get(i).get("quantity");
                        int quantity = quantityNode.asInt();
                        storage.addNewClothing(clothing, quantity);
                        isClothing = true;
                    }
                }
                if (isSortedClothing == true) {
                    ArrayNode itemsNode2 = (ArrayNode) objectNode.get("sortedClothes");
                    for (int i = 0; i < itemsNode2.size(); i++) {
                        Clothing clothing2 = clothingDeserializer.deserialize(itemsNode2.get(i));
                        storage.addSortedClothing(clothing2);
                    }
                }
            }
            return storage;
        }
        return null;
    }  
}



