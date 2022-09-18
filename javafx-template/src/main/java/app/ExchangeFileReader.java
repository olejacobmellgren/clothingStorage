package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

//Filleseren til Appen, mesteparten av logikken er hentet fra SelfChechOut eksempelet brukt i 
//  øvingsforelesningene


public class ExchangeFileReader implements IFiles{

    public void writeToFile(String filename, Marked marked) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getFile(filename))) {
            writer.flush();
            for (IItem item : marked.getMyMarkedItems()) {
                writer.println(String.format("%s;%s;%s;%s;%s;%s;%s", 
                item.getType(), item.getTitle(), item.getOwner(), item.getExchange(),   
                item.getExchangeOwner(), item.getExchangeType(), item.getExchangeTitle()));
            }
        }   
    }//Merk at det skrives til filen som ligger i "target" mappen og ikke den som ligger under "src"

    public Marked readFromFile(String filename) throws FileNotFoundException {
        Marked libary = new Marked();
        try (Scanner scanner = new Scanner(getFile(filename))) {
            //scanner.nextLine(); (denne må legges til hvis man vil ha overskrifter på første linje)
            while (scanner.hasNextLine()) {
                String[] properties = scanner.nextLine().split(";");
                IItem newEntry;
                switch (properties[0]) {
                    case "book":
                        newEntry = new Book((String)properties[1], (String)properties[2]);
                        break;
                    case "plant":
                        newEntry = new Plant(properties[1], properties[2]); 
                        break;
                    default:
                        throw new IllegalArgumentException("No such type");
                        
                }
            }
        }
        return libary;
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


    @Override
    public File getFile(String filename) {
        return new File(ExchangeFileReader.class.getResource("brukerdata").getFile() + filename + ".txt");
    }
   
}
