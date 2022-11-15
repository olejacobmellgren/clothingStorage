package clothingStorage.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Statistics class for storage.
 */
public class StorageStatistics {
    
    /**
     * Returns the quantity for a type of clohting.
     *
     * @param storage to count clohting of certain type
     * @return total quantity of all clothes in storage
     */
    public static int getTotalQuantity(final Storage storage) {
        return storage.getAllClothes()
                        .values().stream()
                        .mapToInt(q -> q)
                        .sum();
    }

    /**
     * Returns the value of the storage.
     *
     * @param storage to calculate total value for
     * @return total value of storage
     */
    public static double getTotalValue(final Storage storage) {
        double totalValue = storage.getAllClothes()
                        .entrySet().stream()
                        .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                        .sum();
        return new BigDecimal(totalValue).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Returns the quantity for a type of clohting.
     *
     * @param storage to count clothing of certain type
     * @param type to count amount of
     * @return quantity of type
     */
    public static int getQuantityForType(final Storage storage, final String type) {
        return storage.getAllClothes()
                        .entrySet()
                        .stream()
                        .filter(e -> e.getKey().getType().equals(type))
                        .mapToInt(e -> e.getValue())
                        .sum();
    }
    
    /**
     * Returns the quantity for a type of clohting with certain size.
     *
     * @param storage to count clothing of certain type and size
     * @param type to count amount of
     * @param size to count amount of
     * @return quantity of type and size
     */
    public static int getQuantityForTypeAndSize(final Storage storage,
                                                final String type, 
                                                final char size) {
        return storage.getAllClothes()
                        .entrySet()
                        .stream()
                        .filter(e -> e.getKey().getType().equals(type))
                        .filter(e -> e.getKey().getSize() == size)
                        .mapToInt(e -> e.getValue())
                        .sum();
    }
}
