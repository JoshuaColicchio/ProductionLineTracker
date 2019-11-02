package application;

/**
 * Class that represents all producible screens for MoviePlayers.
 *
 * @author Joshua Colicchio
 */
public class Screen implements ScreenSpec {

  private final String resolution;
  private final int refreshRate;
  private final int responseTime;

  /**
   * Constructor for a Screen object.
   *
   * @param resolution The Screen's resolution.
   * @param refreshRate The Screen's refresh rate.
   * @param responseTime The Screen's response time.
   */
  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  @Override
  public String getResolution() {
    return resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  @Override
  public int getResponseTime() {
    return responseTime;
  }

  @Override
  public String toString() {
    return "Resolution: "
        + resolution
        + "\nRefresh Rate: "
        + refreshRate
        + "\nResponse Time: "
        + responseTime;
  }
}
