package application;

public class MoviePlayer extends Product implements MultimediaControl {

    private Screen screen;
    private String monitorType;

    public MoviePlayer(String name, String manu, Screen screen, String monitorType) {
        super(name, manu, "Visual");
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
        return super.toString() + "\nScreen: " + screen +
                "\nMonitor Type: " + monitorType;
    }
}
