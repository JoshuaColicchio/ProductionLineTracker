package application;

public abstract class Product implements Item {

    private int id;
    private String name;
    private String manufacturer;
    private String type;

    public Product(String name, String manu, String type) {
        this.name = name;
        this.manufacturer = manu;
        this.type = type;
    }

    public void setId(int newId) {
        id = newId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setName(String newName) {
        name = newName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setManufacturer(String newManu) {
        manufacturer = newManu;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setType(String newType) {
        name = newType;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
    }
}
