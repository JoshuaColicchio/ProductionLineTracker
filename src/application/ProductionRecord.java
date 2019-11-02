package application;

/**
 * Class that represents a Production Record. Objects of this class get created whenever the "Record
 * Production" button is pressed.
 *
 * @author Joshua Colicchio
 */
public class ProductionRecord {

  // Store the number of existing products of a certain ItemType.
  // These get used when creating a new serial number.
  private static int auCount = 0;
  private static int viCount = 0;
  private static int amCount = 0;
  private static int vmCount = 0;

  // Instance variables
  private int productionNumber = -1;
  private int QuantityProduced;
  private int productID;
  private Product productRef;
  private String serialNumber;
  private long dateProduced;

  /**
   * Constructor used when creating a new ProductionRecord.
   *
   * @param quantity How many products were produced in this run.
   * @param product What product was produced.
   */
  public ProductionRecord(int quantity, Product product) {
    this.QuantityProduced = quantity;
    this.productID = product.getId();
    this.serialNumber = generateSerialNumber(product);
    this.dateProduced = new java.util.Date().getTime();
    this.productRef = product;

    // Originally this was in a switch statement, but it took up too many lines doing the same thing
    // these do.
    if (product.getType() == ItemType.AU) auCount++;
    if (product.getType() == ItemType.VI) viCount++;
    if (product.getType() == ItemType.AM) amCount++;
    if (product.getType() == ItemType.VM) vmCount++;
  }

  /**
   * Constructor used when loading a ProductionRecord from the database.
   *
   * @param quantity How many products of this type were produced.
   * @param serialNum The serial number associated with this ProductionRecord
   * @param dateProduced The date when this ProductionRecord was created.
   * @param prodRef The product that was created.
   */
  public ProductionRecord(int quantity, String serialNum, long dateProduced, Product prodRef) {
    this.QuantityProduced = quantity;
    this.productID = prodRef.getId();
    this.serialNumber = serialNum;
    this.dateProduced = dateProduced;
    this.productRef = prodRef;

    // Originally this was in a switch statement, but it took up too many lines doing the same thing
    // these do.
    if (prodRef.getType() == ItemType.AU) auCount++;
    if (prodRef.getType() == ItemType.VI) viCount++;
    if (prodRef.getType() == ItemType.AM) amCount++;
    if (prodRef.getType() == ItemType.VM) vmCount++;
  }

  /**
   * Method to retrieve this Production Record's production number.
   *
   * @return The production number.
   */
  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Method to set the production number of this Production Record.
   *
   * @param productionNumber
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Method to retrieve this Production Record's product ID.
   *
   * @return The product ID.
   */
  public int getProductID() {
    return productID;
  }

  /**
   * Method to set this Production Record's product ID.
   *
   * @param productID
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Method to retrieve this Production Record's serial number.
   *
   * @return
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Method to set the serial number of this Production Record.
   *
   * @param serialNumber The new serial number.
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Method that returns the production date of this Production Record.
   *
   * @return The production date.
   */
  public java.util.Date getDateProduced() {
    return new java.util.Date(dateProduced);
  }

  /**
   * Method to set the production date of this Production Record.
   *
   * @param dateProduced Date to change the production date to.
   */
  public void setDateProduced(java.util.Date dateProduced) {
    this.dateProduced = dateProduced.getTime();
  }

  /**
   * Method to change the Product that this Production Record references.
   *
   * @param productRef New Product to reference.
   */
  public void setProductRef(Product productRef) {
    this.productRef = productRef;
  }

  /**
   * Method to retrieve the number of products produced in this Production Record.
   *
   * @return The number of products produced.
   */
  public int getQuantityProduced() {
    return QuantityProduced;
  }

  /**
   * Method to change the quantity of products produced in this Production Record.
   *
   * @param quantityProduced Integer value of the number of products produced.
   */
  public void setQuantityProduced(int quantityProduced) {
    QuantityProduced = quantityProduced;
  }

  // Went overboard with this javadoc because I wanted to try formatting the text a bit, and have it
  // for future reference.
  /**
   * Returns a string representation of the Production Record object. In general, this will return a
   * string with the following form:
   *
   * <blockquote>
   *
   * Product Name: productName | Product ID: productID | Serial Num: serialNumber | Date:
   * dateProduced
   *
   * </blockquote>
   *
   * @return String
   */
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

  /**
   * Method to generate a unique serial number for each production record.
   *
   * @param product Product that is being produced.
   * @return The generated serial number.
   */
  private String generateSerialNumber(Product product) {
    String serial = "";
    switch (product.getType()) {
      case AU:
        auCount++;
        serial =
            product.getManufacturer().substring(0, 3)
                + product.getType().name()
                + String.format("%05d", auCount);
        break;
      case VI:
        viCount++;
        serial =
            product.getManufacturer().substring(0, 3)
                + product.getType().name()
                + String.format("%05d", viCount);
        break;
      case AM:
        amCount++;
        serial =
            product.getManufacturer().substring(0, 3)
                + product.getType().name()
                + String.format("%05d", amCount);
        break;
      case VM:
        vmCount++;
        serial =
            product.getManufacturer().substring(0, 3)
                + product.getType().name()
                + String.format("%05d", vmCount);
        break;
    }
    return serial;
  }
}
