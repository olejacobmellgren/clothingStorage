package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

//Filleseren til Appen, mesteparten av logikken er hentet fra SelfChechOut eksempelet brukt i 
//  øvingsforelesningene


public class FileReader implements IFiles{

    public void writeToFile(String filename, Storage storage) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getFile(filename))) {
            writer.flush();
            for (Clothing clothing : storage.getAllClothes().keySet()) {
                writer.println(String.format("%s;%s;%s;%s;%s;%s", 
                clothing.getName(), clothing.getBrand(), clothing .getSize(), clothing.getPrice(),   
                clothing.getSale(), storage.getQuantity(clothing)));
            }
        }   
    }//Merk at det skrives til filen som ligger i "target" mappen og ikke den som ligger under "src"

    public Storage readFromFile(String filename) throws FileNotFoundException {
        Storage storage = new Storage();
        try (Scanner scanner = new Scanner(getFile(filename))) {
            //scanner.nextLine(); (denne må legges til hvis man vil ha overskrifter på første linje)
            while (scanner.hasNextLine()) {
                String[] properties = scanner.nextLine().split(";");
                Clothing newClothing = new Clothing(properties[0], properties[1], properties[2].charAt(0), Double.parseDouble(properties[3]));
                newClothing.setSale(Float.parseFloat(properties[4]));
                storage.addNewClothing(newClothing, Integer.parseInt(properties[5]));
            }
        }
        return storage;
    }
    public String readAsString(String filename) throws FileNotFoundException{
        try (Scanner scanner = new Scanner(getFile(filename))) {
            String text = "";
            if (scanner.hasNextLine()) text += scanner.nextLine();
            while (scanner.hasNextLine()) {
                text += '\n' + scanner.nextLine();
            }
            return text;
        }
    }


    // Her må det legges inn riktig mappe navn på hvor filene som skrevet ligger (ikke koden)
    @Override
    public File getFile(String filename) {
        return new File(FileReader.class.getResource("brukerdata").getFile() + filename + ".txt");
    }
   
}
