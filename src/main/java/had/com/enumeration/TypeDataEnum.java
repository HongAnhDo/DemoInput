package had.com.enumeration;

public enum TypeDataEnum {
    TEXT_FIELD(0, "JTextField"),
    TEXT_AREA(1, "JTextArea"),
    TEXT_FORMATTED(2, "JFormattedTextField"),
    DATE_SHORT(3, "");
    int type;
    String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    TypeDataEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
