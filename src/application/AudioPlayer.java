package application;

/**
 * Class that represents a producible AudioPlayer device.
 *
 * @author Joshua Colicchio
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  /**
   * Constructor for the AudioPlayer Product.
   *
   * @param id The ID of the AudioPlayer.
   * @param prodName The Name of the AudioPlayer.
   * @param manuName The Manufacturer of the AudioPlayer.
   * @param supportedAudioFormats The supported Audio Formats for this AudioPlayer.
   * @param supportedPlaylistFormats The supported Playlist Formats for this AudioPlayer.
   */
  public AudioPlayer(
      int id,
      String prodName,
      String manuName,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(id, prodName, manuName, ItemType.AU);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
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
    return super.toString()
        + "\nSupported Audio Formats: "
        + supportedAudioFormats
        + "\nSupported Playlist Formats: "
        + supportedPlaylistFormats;
  }
}
