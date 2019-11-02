package application;

/**
 * Interface that defines functionality for a standard MoviePlayer.
 *
 * @author Joshua Colicchio
 */
interface MultimediaControl {
  /*
  I don't know what to do about the warnings IntelliJ gives here. These don't get used directly,
  but they are overridden in MoviePlayer. To make the warnings go away, I'd have to call these
  methods in MoviePlayer.java, but that'd be pointless.

  Let me know if the warnings are valid, because I don't think they are.
   */
  void play();

  void stop();

  void previous();

  void next();
}
