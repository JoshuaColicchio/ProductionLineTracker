package application;

/**
 * Interface that defines functionality for a standard MoviePlayer.
 *
 * @author Joshua Colicchio
 */
public interface ScreenSpec {

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
