package application;

public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  public AudioPlayer(int id, String prodName, String manuName, String supportedAudioFormats, String supportedPlaylistFormats) {
    super(id, prodName, manuName, ItemType.AU.toString());
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  @Override
  public void play() {
    System.out.println("Playing...");
  }

  @Override
  public void stop() {
    System.out.println("Stopping...");
  }

  @Override
  public void previous() {
    System.out.println("Previous...");
  }

  @Override
  public void next() {
    System.out.println("Next...");
  }

  @Override
    public String toString() {
      return super.toString() + "\nSupported Audio Formats: " + supportedAudioFormats +
              "\nSupported Playlist Formats: " + supportedPlaylistFormats;
  }
}
