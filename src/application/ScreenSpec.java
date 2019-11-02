package application;

/**
 * Interface that defines functionality for a standard MoviePlayer.
 *
 * @author Joshua Colicchio
 */
interface ScreenSpec {
  /*
  I don't know what to do about the warnings IntelliJ gives here. These don't get used directly,
  but they are overridden in Screen. To make the warnings go away, I'd have to call these methods in
  Screen.java, but that'd be pointless.

  Let me know if the warnings are valid, because I don't think they are.
   */

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
