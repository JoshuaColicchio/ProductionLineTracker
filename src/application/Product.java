package application;

/**
 * Class that represents a base product. All producible objects inherit from this class.
 *
 * @author Joshua Colicchio
 */
public abstract class Product implements Item {

  private int id;
  private String name;
  private String manufacturer;
  private ItemType type;

  Product(int id, String name, String manu, ItemType type) {
    this.id = id;
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
  public void setType(ItemType newType) {
    type = newType;
  }

  @Override
  public ItemType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}
