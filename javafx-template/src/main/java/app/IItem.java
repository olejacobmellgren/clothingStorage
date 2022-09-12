package app;

public interface IItem {
    public String getTitle();
    public String getOwner();
    public String getType();
    public String getText();
    public String toString();
    public boolean getExchange();
    public void setExchange(boolean value);
}
