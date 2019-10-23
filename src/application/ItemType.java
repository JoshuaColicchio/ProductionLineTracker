package application;

public enum ItemType {
  AU,
  VI,
  AM,
  VM;

  public String toString() {
    switch (this) {
      case AU:
        return "Audio";
      case VI:
        return "Visual";
      case AM:
        return "AudioMobile";
      case VM:
        return "VisualMobile";
      default: // This is impossible, but required.
        return "UNKNOWN TYPE - " + this.name();
    }
  }
}