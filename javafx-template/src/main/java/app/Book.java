package app;

public class Book extends Item implements IItem{

    public Book(String title, String owner){
        super(title, owner, "book");
    }
        
}
