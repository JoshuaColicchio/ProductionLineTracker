package application;

/**
 * Class that represents a Production Record. Objects of this class get created whenever the "Record
 * Production" button is pressed.
 *
 * @author Joshua Colicchio
 */
class ProductionRecord {

  // Store the number of existing products of a certain ItemType.
  // These get used when creating a new serial number.
  private static int auCount = 0;
  private static int viCount = 0;
  private static int amCount = 0;
  private static int vmCount = 0;

  private final int quantityProduced;
  private final int productID;
  private final String serialNumber;
  private final long dateProduced;
  private final Product productRef;

  public ProductionRecord(int quantity, Product product) {
    this.quantityProduced = quantity;
    this.productID = product.getId();
    this.serialNumber = generateSerialNumber(product);
    this.dateProduced = new java.util.Date().getTime();
    this.productRef = product;

    if (product.getType() == ItemType.AU) {
      auCount++;
    }
    if (product.getType() == ItemType.VI) {
      viCount++;
    }
    if (product.getType() == ItemType.AM) {
      amCount++;
    }
    if (product.getType() == ItemType.VM) {
      vmCount++;
    }
  }

  public ProductionRecord(int quantity, String serialNum, long dateProduced, Product prodRef) {
    this.quantityProduced = quantity;
    this.productID = prodRef.getId();
    this.serialNumber = serialNum;
    this.dateProduced = dateProduced;
    this.productRef = prodRef;

    if (prodRef.getType() == ItemType.AU) {
      auCount++;
    }
    else if (prodRef.getType() == ItemType.VI) {
      viCount++;
    }
    else if (prodRef.getType() == ItemType.AM) {
      amCount++;
    }
    else if (prodRef.getType() == ItemType.VM) {
      vmCount++;
    }
  }

  public int getProductID() {
    return productID;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public java.util.Date getDateProduced() {
    return new java.util.Date(dateProduced);
  }

  public int getQuantityProduced() {
    return quantityProduced;
  }

  public String toString() {
    return "Product Name: "
        + productRef.getName()
        + " | Product ID: "
        + productID
        + " | Serial Num: "
        + serialNumber
        + " | Date: "
        + new java.util.Date(dateProduced);
  }

  private String generateSerialNumber(Product product) {
    String serial = product.getManufacturer().substring(0, 3) + product.getType().name();
    switch (product.getType()) {
      case AU -> serial += String.format("%05d", ++auCount);
      case VI -> serial += String.format("%05d", ++viCount);
      case AM -> serial += String.format("%05d", ++amCount);
      case VM -> serial += String.format("%05d", ++vmCount);
      default -> {
        System.out.println("ERROR creating serial number for ProductionRecord. Invalid Product Type.");
        serial += "INVALID";}
    }
    return serial;
  }
}
