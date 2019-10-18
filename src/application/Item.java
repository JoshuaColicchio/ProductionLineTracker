package application;

public interface Item {
  void setId(int newId);

  int getId();

  void setName(String newName);

  String getName();

  void setManufacturer(String newManu);

  String getManufacturer();

  void setType(ItemType newType);

  ItemType getType();
}
