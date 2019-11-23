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

  /**
   * Constructor for a new Product object.
   *
   * @param id The Product's ID.
   * @param name The Product's name.
   * @param manu The Product's manufacturer.
   * @param type The Product's type.
   */
  Product(int id, String name, String manu, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manu;
    this.type = type;
  }

  /**
   * Method to set the Product's ID.
   *
   * @param newId The new ID to set this Product's ID to.
   */
  public void setId(int newId) {
    id = newId;
  }

  /**
   * Method to retrieve this Product's ID.
   *
   * @return This Product's ID.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Method to set this Product's name.
   *
   * @param newName The new name for this Product.
   */
  @Override
  public void setName(String newName) {
    name = newName;
  }

  /**
   * Method to retrieve this Product's name.
   *
   * @return This Product's name.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Method to set this Product's manufacturer.
   *
   * @param newManu The new manufacturer for this Product.
   */
  @Override
  public void setManufacturer(String newManu) {
    manufacturer = newManu;
  }

  /**
   * Method to retrieve this Product's manufacturer.
   *
   * @return This Product's manufacturer.
   */
  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Method to set this Product's type.
   *
   * @param newType The new type for this Product.
   */
  @Override
  public void setType(ItemType newType) {
    type = newType;
  }

  /**
   * Method to retrieve this Product's type.
   *
   * @return This Product's type.
   */
  @Override
  public ItemType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}
