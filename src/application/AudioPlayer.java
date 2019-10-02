package application;

public class AudioPlayer extends Product implements MultimediaControl {

  private String audioSpecification;
  private String mediaType;

  public AudioPlayer(String prodName, String manuName, String audioSpecs) {
    super(prodName, manuName, ItemType.AU.toString());
    audioSpecification = audioSpecs;
    mediaType = "AU";

    play();
    stop();
    next();
    previous();
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
      return super.toString() + "\nAudio Specification: " + audioSpecification + "\nMedia Type: " + mediaType;
  }
}
