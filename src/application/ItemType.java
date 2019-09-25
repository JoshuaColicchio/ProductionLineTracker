package application;

public enum ItemType {
    AU, VI, AM, VM;

    @Override
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
            default:
                return "UNKNOWN TYPE - " + this.name();
        }
    }
}
