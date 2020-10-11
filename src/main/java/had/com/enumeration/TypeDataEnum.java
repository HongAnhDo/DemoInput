package had.com.enumeration;

public enum TypeDataEnum {
    JTextField(0, "JTextField"),
    JTextArea(1, "JTextArea");

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
