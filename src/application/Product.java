package application;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that represents a base product. All producible objects inherit from this class.
 *
 * @author Joshua Colicchio
 */
public abstract class Product {

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

  public static Product createProduct(ResultSet resultSet) {
    try {
      switch (resultSet.getString("TYPE")) {
        case "Audio":
        case "AudioMobile":
          return new AudioPlayer(
                  resultSet.getInt("ID"), resultSet.getString("NAME"),
                  resultSet.getString("MANUFACTURER"), resultSet.getString("TYPE"),
                  "MP3");
        case "Visual":
        case "VisualMobile":
          return new MoviePlayer(
                  resultSet.getInt("ID"), resultSet.getString("NAME"),
                  resultSet.getString("MANUFACTURER"),
                  new Screen("1920x1080", 144, 22), MonitorType.LED);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public static Product createProduct(int productId, String name, String manufacturer, ItemType type) {
    return switch (type.toString()) {
      case "Audio", "AudioMobile" -> new AudioPlayer(productId, name, manufacturer, type.toString(), "MP3");
      case "Visual", "VisualMobile" -> new MoviePlayer(
              productId, name, manufacturer,
              new Screen("1920x1080", 144, 22), MonitorType.LED);
      default -> null;
    };
  }

  public void setId(int newId) {
    id = newId;
  }

  public int getId() {
    return id;
  }

  public void setName(String newName) {
    name = newName;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String newManu) {
    manufacturer = newManu;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setType(ItemType newType) {
    type = newType;
  }

  public ItemType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}
