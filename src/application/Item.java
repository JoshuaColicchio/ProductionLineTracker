package application;

public interface Item {
    int getId();
    void setName(String newName);
    String getName();
    void setManufacturer(String newManu);
    String getManufacturer();
    void setType(String newType);
    String getType();
}
