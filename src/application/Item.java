package application;

interface Item {

  /*
  All of the warnings IntelliJ gives about this class are invalid. Currently the Product class doesn't
  use setId, setName, setManufacturer, or setType, so IntelliJ flags them as unused declarations. While
  it is correct that they aren't used, according to the project guide, they have to be here.
   */
  void setId(int newId);

  int getId();

  void setName(String newName);

  String getName();

  void setManufacturer(String newManu);

  String getManufacturer();

  void setType(ItemType newType);

  ItemType getType();
}
