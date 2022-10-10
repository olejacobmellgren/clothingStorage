package clothingStorage.core;

public class Clothing {

    private String name;
    private String brand;
    private char size;
    private double price;
    private double discount;  // Kan denne endres til en float? Siden et tall er false hvis det er 0? Lettere med tanke på lagring tror jeg -Å
    private final String[] validBrands = {"Nike", "Adidas", "H&M", "Lacoste", "Louis Vuitton", "Supreme", "Levi's"}; //denne listen kan utvides med klesmerker som selges i butikken

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
        setPrice(price, false);
        setSale(0);
    }

    public void setName(String name) {
        isValidName(name);
        this.name = name;
    }

    private void isValidName(String name) {
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

    public void setBrand(String brand) {
        if (!isValidBrand(brand)) {
            throw new IllegalArgumentException("Clothing brand does not exist");
        }
        this.brand = brand;
    }

    private boolean isValidBrand(String brand) {
        for (String s : validBrands) {
            if (brand.equals(s)) {
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

    public void setPrice(double price, boolean isOnSale) { // konvertering fra String til double skjer i kontroller. Så opererer på at det faktisk er et tall som blir sendt inn som argument
        if (price <= 0) {
            throw new IllegalArgumentException("Given price is negative or zero");
        }
        if (isOnSale) {
            this.setSale(0);
        }
        this.price = price;
    }

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
        if (this.discount == 0.0) {
            return false;
        }
        return true;
    }

    public double getDiscount() {
        return this.discount;
    }

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

    /*
    Små tester for oppførsel i main-metode
    Alt funker som det skal utifra forutsetninger
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
