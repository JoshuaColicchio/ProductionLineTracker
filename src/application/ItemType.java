package application;

public enum ItemType {
  AU,
  VI,
  AM,
  VM;

  /**
   * Method that converts the provided enum into its' String form.
   *
   * @return The String version of the enum value.
   */
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
      default: // This is impossible, but it doesn't hurt to check.
        return "UNKNOWN TYPE - " + this.name();
    }
  }
}
