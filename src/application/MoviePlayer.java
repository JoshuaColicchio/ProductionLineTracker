package application;

/**
 * Class that represents all producible movie player type items. *
 *
 * @author Joshua Colicchio
 */
public class MoviePlayer extends Product implements MultimediaControl {

  private final Screen screen;
  private final MonitorType monitorType;

  /**
   * Constructor for the MoviePlayer Product.
   *
   * @param id The ID of this MoviePlayer Product.
   * @param name The Name of this MoviePlayer Product.
   * @param manu The Manufacturer of this MoviePlayer Product.
   * @param screen The Screen of this MoviePlayer Product.
   * @param monitorType The Monitor Type of this MoviePlayer Product.
   */
  public MoviePlayer(int id, String name, String manu, Screen screen, MonitorType monitorType) {
    super(id, name, manu, ItemType.VI);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  @Override
  public String toString() {
    return super.toString() + "\nScreen: " + screen + "\nMonitor Type: " + monitorType;
  }
}
