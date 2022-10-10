package clothingStorage.core;

/**
 * Represents a clothing item with name, brand, size and price, and corresponding get/set functions
 */
public class Clothing {

    /** 
     * Name of Clothing object
    */
    private String name;

    /** 
     * Brand of Clothing object
    */
    private String brand;

    /** 
     * Size of Clothing object
    */
    private char size;

    /** 
     * Price of Clothing object
    */
    private double price;
    private double discount;  // Kan denne endres til en float? Siden et tall er false hvis det er 0? Lettere med tanke på lagring tror jeg -Å
    private final String[] validBrands = {"Nike", "Adidas", "H&M", "Lacoste", "Louis Vuitton", "Supreme", "Levi's"}; //denne listen kan utvides med klesmerker som selges i butikken

    /** 
     * If Clothing object is on sale
    */
    private int onSale;  

    /** 
     * Valid brands for Clothing object
    */
    private final String[] validBrands = {"Nike", "Adidas", "H&M", "Lacoste", "Louis Vuitton", "Supreme", "Levi's"};

    /**
     * Initializes Clothing object
     * @param name or type of clothing item
     * @param brand of clothing item
     * @param size of clothing item
     * @param price of clothing item
     */
    public Clothing(String name, String brand, char size, double price) {
        setName(name);
        setBrand(brand);
        setSize(size);
        setPrice(price, false);
        setSale(0);
    }

    /** 
     * Sets name
     * 
     *  @param name of the name you want to set
     *  @throws IllegalArgumentException if name is invalid
    */
    public void setName(String name) {
        isValidName(name);
        this.name = name;
    }


    /** 
     * Checks if the clothing name is valid
     * 
     * @param name of the name you want to check
     * @return true if valid, false if invalid
    */
    private boolean isValidName(String name) {
        char[] charNumbers = name.toCharArray();
        for (char c : charNumbers) {
            if (Character.isDigit(c)) {
                throw new IllegalArgumentException("Name of clothing contains a number");
            }
        }
        if (Character.isLowerCase(name.charAt(0))) {
            throw new IllegalArgumentException("Name of clothing must start with uppercase letter");
        }
    }

    /** 
     * Sets the clothing brand
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
     * Checks if the brand is valid
     * 
     * @param brand of the name you want to check
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
     * Sets clothing size
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

    public void setPrice(double price, boolean isOnSale) { // konvertering fra String til double skjer i kontroller. Så opererer på at det faktisk er et tall som blir sendt inn som argument
        if (price <= 0) {
            throw new IllegalArgumentException("Given price is negative or zero");
        }
        if (isOnSale) {
            this.setSale(0);
        }
        this.price = price;
    }

    /** 
     * Sets discount
     * 
     * @param discount you want to set
     * @throws IllegalArgumentException is discount is invalid
    */
    public void setDiscount(double discount) {
        if (!isValidDiscount(discount)) {
            if (this.getDiscount() != 0) {
                throw new IllegalStateException("Clothing is already on discount");
            } else {
                throw new IllegalArgumentException("Given discount is not valid");
            }
        }
        this.setSale(discount);
        this.setPrice(this.getPrice()*(1-this.getDiscount()), false);
    }

    public void removeDiscount() {
        if (this.getDiscount() > 0) {
            this.setPrice(this.getPrice()/(1-discount), true);
            this.setSale(0);
        } else {
            throw new IllegalStateException("Clothing is not on discount");
        }
    }

    private boolean isValidDiscount(double discount) {
        if (discount>0 && discount<1 && this.getDiscount() == 0) {
            return true;
        }
        return false;
    }

    public void setSale(double sale) {
        if (sale>=0 && sale<1) {
            this.discount = sale;
        } else {
            throw new IllegalArgumentException("Input must be between 0 and 1");
        }
    }

    /** 
     * Retrieves clothing name
     * 
     * @return name of clothing item
    */
    public String getName() {
        return this.name;
    }

    /** 
     * Retrieves clothing brand
     * 
     * @return brand of clothing item
    */
    public String getBrand() {
        return this.brand;
    }

    /** 
     * Retrieves clothing size
     * 
     * @return size of clothing item
    */
    public char getSize() {
        return this.size;
    }

    /** 
     * Retrieves price of clothing item
     * 
     * @return price of clothing item
    */
    public double getPrice() {
        return this.price;
    }

    /** 
     * Checks if clothing item is on sale
     * 
     * @return true if on sale, false if not on sale
    */
    public boolean isOnSale() {
        if (this.discount == 0.0) {
            return false;
        }
        return true;
    }

    public double getDiscount() {
        return this.discount;
    }

    /** 
     * Formats name, brand, size and price values to string
     * 
     * @return formatted string with values of name, brand, size and price
    */
    @Override
    public String toString() {
        return this.getName() + "\n" + "   - Brand: " + this.getBrand() + "\n" + "   - Size: " + String.valueOf(this.getSize()) + "\n" + "   - Price: " + String.valueOf(this.getPrice()) + ",-";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Clothing) {
            Clothing clothing = (Clothing) object;
            if (!(clothing.getBrand().equals(this.getBrand()))) {
                return false;
            }
            else if (!(clothing.getSize() == this.getSize())) {
                return false;
            }
            else if (!(clothing.getName().equals(this.getName()))) {
                return false;
            }
            return true;
        }
        return false;
    }

    /** 
     * Test to see if Clothing object is created
     * 
    */
    public static void main(String[] args) {
        Clothing clothing = new Clothing("Bukse", "Levi's", 'S', 188);
        System.out.println(clothing);
        Clothing clothing2 = new Clothing("Bukse", "Levi's", 'S', 100);
        System.out.println(clothing.equals(clothing2));
        clothing.setDiscount(60);
        System.out.println(clothing.getPrice());
        clothing.removeDiscount();
        System.out.println(clothing.getPrice());

    }
}
