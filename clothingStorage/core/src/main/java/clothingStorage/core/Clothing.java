package clothingStorage.core;


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
     * 
     * @param name, brand, size, price
    */
    public Clothing(String name, String brand, char size, double price) {
        setName(name);
        setBrand(brand);
        setSize(size);
        setPrice(price);
    }

    /** 
     * Sets name
     * 
     *  @param name of the name you want to set
    */
    public void setName(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name of clothing contains a number");
        }
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
                return false;
            }
        }
        if (Character.isUpperCase(name.charAt(0))) {
            return true; 
        }
        return false;
    }

    /** 
     * Sets the clothing brand
     * 
     *  @param brand you want to set
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
    */
    public void setSize(char size) {
        if (size == 'S' || size == 'M' || size == 'L') {
            this.size = size;
        } else {
            throw new IllegalArgumentException("Given size is not S, M or L");
        }
    }

    /** 
     * Sets clothing price
     * 
     * @param price you want to set
    */
    public void setPrice(double price) { // konvertering fra String til double skjer i kontroller. Så opererer på at det faktisk er et tall som blir sendt inn som argument
        if (price <= 0) {
            throw new IllegalArgumentException("Given price is negative or zero");
        }
        this.price = price;
    }

    /** 
     * Sets discount
     * 
     * @param discount you want to set
    */
    public void setDiscount(double discount) {
        if (!isValidDiscount(discount)) {
            throw new IllegalArgumentException("Given discount is not valid");
        }
        this.setPrice(this.getPrice()*(100-discount)/100);
        this.setSale(1);
    }

    /** 
     * Checks if the discount is valid
     * 
     * @param discount you want to check
     * @return true if valid, false if invalid
    */
    public boolean isValidDiscount(double discount) {
        if (discount>0 && discount<100) {
            return true;
        }
        return false;
    }

    /** 
     * Sets item on sale
     * 
     * @param sale on item you want to set
    */
    public void setSale(int sale) {
        if (sale == 1) {
            this.onSale = 1;
        } else if (sale == 0) {
            this.onSale = 0;
        } else {
            throw new IllegalArgumentException("Input must be either 0 or 1");
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
        if (this.onSale == 1) {
            return true;
        } else {
            return false;
        }
    }

    /** 
     * Retrieves whether the item is on sale or not
     * 
     * @return int value 1 if item is on sale, 0 if not on sale
    */
    public int getSale() {
        return this.onSale;
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

    //@Override
    //public boolean equals(Clothing clothing) {
    //    if (!(clothing.getBrand() == this.getBrand())) {
    //        return false;
    //    }
    //    else if (!(clothing.getSize() == this.getSize())) {
    //        return false;
    //    }
    //    else if (!(clothing.getName() == this.getName())) {
    //        return false;
    //    }
    //    else {
    //        return true;
    //    }
    //}

    /** 
     * Test to see if Clothing object is created
     * 
    */
    public static void main(String[] args) {
        Clothing clothing = new Clothing("Bukse", "Levi's", 'S', 24);
        System.out.println(clothing);
    }
}
