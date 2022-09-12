package app;

public abstract class Item {
    private final String title;
    private final String type;
    private String owner;
    private String text;

    public Item(String title, String owner, String type){
        this.title = title;
        checkType(type);
        this.type = type;
        this.owner = owner;
    }

    private void checkType(String type){
        if (!(type == "plant" || type == "book")) throw new IllegalArgumentException("Illeagal type");
    } 

    public String getTitle() {
        return this.title;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getType(){
        return this.type;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "\n Type: " + this.type + "\n Owner: " + this.owner;
    }

}
