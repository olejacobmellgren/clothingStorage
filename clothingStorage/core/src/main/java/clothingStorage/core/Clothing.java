package clothingStorage.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a clothing item with type, brand, size and price, and corresponding get/set functions.
 */
public class Clothing {

    /** 
     * Type of Clothing object.
    */
    private String type;

    /** 
     * Brand of Clothing object.
    */
    private String brand;

    /** 
     * Size of Clothing object.
    */
    private char size;

    /** 
     * Price of Clothing object.
    */
    private double price;

    /** 
     * Discount of Clothing object.
    */
    private double discount;
    /** 
     * Name of Clothing object.
    */
    private String name;

    /** 
     * Valid brands for Clothing object.
    */
    private final String[] validBrands = {"Nike", "Adidas", "H&M", "Lacoste", 
                                          "LouisVuitton", "Supreme", "Levi's"}; /*May be expanded*/

    /** 
     * Valid types for Clothing object.
    */
    private final String[] validTypes = {"Pants", "Shirt", "Underwear",
                                         "Socks", "Sweater", "Jacket",
                                         "Shorts"}; /*May be expanded*/
                                         

    /**
     * Initializes Clothing object.
     *
     * @param type or type of clothing item
     * @param brand of clothing item
     * @param size of clothing item
     * @param price of clothing item
    */
    public Clothing(String type, String brand, char size, double price) {
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price, false);
        setDiscount(0);
        setName();
    }

    /** 
     * Sets type.
     *
     *  @param type of the type you want to set
    */
    public void setType(String type) {
        isValidType(type);
        this.type = type;
    }


    /** 
     * Checks if the clothing type is valid.
     *
     * @param type of the type you want to check
     * @throws IllegalArgumentException if type contains a number
     * @throws IllegalArgumentException if type does not start with uppercase letter
    */
    private void isValidType(String type) {
        char[] charNumbers = type.toCharArray();
        for (char c : charNumbers) {
            if (Character.isDigit(c)) {
                throw new IllegalArgumentException("Type of clothing contains a number");
            }
        }
        if (Character.isLowerCase(type.charAt(0))) {
            throw new IllegalArgumentException("Type of clothing must start with uppercase letter");
        }
        for (String s : validTypes) {
            if (type.equals(s)) {
                return;
            }
        }
        throw new IllegalArgumentException("Type of Clothing: " + type + ", is not a valid type");


    }

    /** 
     * Sets clothing brand.
     *
     *  @param brand you want to set
     *  @throws IllegalArgumentException if brand is not valid
    */
    public void setBrand(String brand) {
        if (!isValidBrand(brand)) {
            throw new IllegalArgumentException("Clothing brand does not exist");
        }
        this.brand = brand;
    }

    /** 
     * Checks if the brand is valid.
     *
     * @param brand to check
     * @return true if valid, false if invalid
    */
    private boolean isValidBrand(String brand) {
        for (String s : validBrands) {
            if (brand.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /** 
     * Sets clothing size.
     *
     * @param size you want to set
     * @throws IllegalArgumentException if size is not S, M or L
    */
    public void setSize(char size) {
        if (size == 'S' || size == 'M' || size == 'L') {
            this.size = size;
        } else {
            throw new IllegalArgumentException("Given size is not S, M or L");
        }
    }

    /**
     * Sets clothing price.
     *
     * @param price to be set
     * @param isOnDiscount true if clothing is on discount, false if not on discount
     * @throws IllegalArgumentException if given price is zero or below
     */
    public void setPrice(double price, boolean isOnDiscount) {
        if (price <= 0) {
            throw new IllegalArgumentException("Given price is negative or zero");
        }
        if (isOnDiscount) {
            this.setDiscount(0);
        }
        this.price = price;
    }

    /** 
     * Sets price after a discount is added.
     *
     * @param discount you want to add
     * @throws IllegalStateException if clothing is already on discount
     * @throws IllegalArgumentException is discount is invalid
    */
    public void setPriceAfterAddedDiscount(double discount) {
        if (!isValidDiscount(discount)) {
            if (this.getDiscount() != 0) {
                throw new IllegalStateException("Clothing is already on discount");
            } else {
                throw new IllegalArgumentException("Given discount is not valid");
            }
        }
        this.setDiscount(discount);
        this.setPrice(this.getPrice() * (1 - this.getDiscount()), false);
    }

    /**
     * Removes discount from clothing item and 
     * reverts price to original price.
     *
     * @throws IllegalStateException if clothing item is not on discount
     */
    public void removeDiscount() {
        if (this.getDiscount() > 0) {
            this.setPrice(this.getPrice() / (1 - discount), true);
            this.setDiscount(0);
        } else {
            throw new IllegalStateException("Clothing is not on discount");
        }
    }

    /**
     * Checks if discount is a valid discount.
     *
     * @param discount to be checked
     * @return true if discount is valid, false if discount is not valid
     */
    private boolean isValidDiscount(double discount) {
        if (discount > 0 && discount < 1 && this.getDiscount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Sets the discount of clothing item.
     *
     * @param discount discount to be set
     * @throws IllegalArgumentException if discount is not between 0 and 1
     */
    public void setDiscount(double discount) {
        if (discount >= 0 && discount < 1) {
            this.discount = discount;
        } else {
            throw new IllegalArgumentException("Input must be between 0 and 1");
        }
    }

    /**
     * Sets the Name of clothing item.
     */
    public void setName() {
        String string = "";
        string += this.type + this.brand + this.size;
        this.name = string;
    }

    /** 
     * Retrieves clothing type.
     *
     * @return type of clothing item
    */
    public String getType() {
        return this.type;
    }

    /** 
     * Retrieves clothing brand.
     *
     * @return brand of clothing item
    */
    public String getBrand() {
        return this.brand;
    }

    /** 
     * Retrieves clothing size.
     *
     * @return size of clothing item
    */
    public char getSize() {
        return this.size;
    }

    /** 
     * Retrieves clothing price.
     *
     * @return price of clothing item
    */
    public double getPrice() {
        return new BigDecimal(this.price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /** 
     * Checks if clothing item is on discount.
     *
     * @return true if on discount, false if not on discount
    */
    public boolean isOnDiscount() {
        if (this.discount == 0.0) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves clothing discount.
     *
     * @return discount of clothing item
     */
    public double getDiscount() {
        return this.discount;
    }

    /**
     * Retrieves clothing name.
     *
     * @return name of clothing item
     */
    public String getName() {
        return this.name;
    }

    /** 
     * Formats type, brand, size and price values to string.
     *
     * @return formatted string with values of type, brand, size and price
    */
    @Override
    public String toString() {
        String toString = this.getType() + "\n" + "   - Brand: " + this.getBrand() + "\n"
            + "   - Size: " + String.valueOf(this.getSize()) + "\n"
            + "   - Price: " + String.valueOf(this.getPrice()) + "kr";
        return toString;
    }

    /**
     * Check if two Clothing-objects have the same attributes except price.
     *
     * @return true if same attributes but not price, false if not
     */
    public boolean equalsButDifferentPrice(Clothing clothing) {
        if (!(clothing.getBrand().equals(this.getBrand()))) {
            return false;
        } else if (!(clothing.getSize() == this.getSize())) {
            return false;
        } else if (!(clothing.getType().equals(this.getType()))) {
            return false;
        }
        return true;
    }

    /**
     * Check if two Clothing-objects have the same attributes except size.
     *
     * @return true if same attributes but not size, false if not
     */
    public boolean equalsButDifferentSize(Clothing clothing) {
        if (!(clothing.getBrand().equals(this.getBrand()))) {
            return false;
        } else if (!(clothing.getType().equals(this.getType()))) {
            return false;
        }
        return true;
    }
}

