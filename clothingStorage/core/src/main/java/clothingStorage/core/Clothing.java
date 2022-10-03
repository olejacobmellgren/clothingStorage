package clothingStorage.core;

public class Clothing {

    private String name;
    private String brand;
    private char size;
    private double price;
    private float onSale;  // Kan denne endres til en float? Siden et tall er false hvis det er 0? Lettere med tanke på lagring tror jeg -Å
    private final String[] validBrands = {"nike", "adidas", "h&m", "lacoste", "louis vuitton", "supreme", "levi's"}; //denne listen kan utvides med klesmerker som selges i butikken

    /*
    Antagelser:
    - Et navn på et klesplagg kan ikke inneholde et tall.
    - Kun størrelse small, medium og large eksisterer.
    - Bruker kan kun legge inn et klesmerke som eksisterer
    */


    public Clothing(String name, String brand, char size, double price) {
        setName(name);
        setBrand(brand);
        setSize(size);
        setPrice(price);
    }

    public void setName(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name of clothing contains a number");
        }
        this.name = name;
    }

    private boolean isValidName(String name) {
        char[] charNumbers = name.toCharArray();
        for (char c : charNumbers) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true; 
    }

    public void setBrand(String brand) {
        if (!isValidBrand(brand)) {
            throw new IllegalArgumentException("Clothing brand does not exist");
        }
        this.brand = brand;
    }

    private boolean isValidBrand(String brand) {
        for (String s : validBrands) {
            if (brand.toLowerCase().equals(s)) {
                return true;
            }
        }
        return false;
    }

    public void setSize(char size) {
        if (size == 'S' || size == 'M' || size == 'L') {
            this.size = size;
        } else {
            throw new IllegalArgumentException("Given size is not S, M or L");
        }
    }

    public void setPrice(double price) { // konvertering fra String til double skjer i kontroller. Så opererer på at det faktisk er et tall som blir sendt inn som argument
        if (price <= 0) {
            throw new IllegalArgumentException("Given price is negative or zero");
        }
        this.price = price;
    }

    public void setSale(float onSale) {
        this.onSale = onSale;
    }

    public String getName() {
        return this.name;
    }

    public String getBrand() {
        return this.brand;
    }

    public char getSize() {
        return this.size;
    }

    public double getPrice() {
        return this.price;
    }

    public boolean isOnSale() {
        if (onSale == 0.0) {
            return true;
        } else {
            return false;
        }
    }

    public float getSale() {
        return this.onSale;
    }

    @Override
    public String toString() {
        return this.getName() + "\n" + "   - Brand: " + this.getBrand() + "\n" + "   - Size: " + String.valueOf(this.getSize()) + "\n" + "   - Price: " + String.valueOf(this.getPrice()) + ",-";
    }

    /*
    Små tester for oppførsel i main-metode
    Alt funker som det skal utifra forutsetninger
    */

    public static void main(String[] args) {
        Clothing clothing = new Clothing("Bukse", "Levi's", 'S', 24);
        System.out.println(clothing);
    }
}
