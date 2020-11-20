package application;

/**
 * Enum of all ItemTypes that an Item can be.
 *
 * @author Joshua Colicchio
 */
public enum ItemType {
  AU,
  VI,
  AM,
  VM;
  
  public String toString() {
    return switch (this) {
      case AU -> "Audio";
      case VI -> "Visual";
      case AM -> "AudioMobile";
      case VM -> "VisualMobile";
    };
  }
}
