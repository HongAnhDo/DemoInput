package had.com.model;

public class DataTable {
    String name;
    String value;

    public DataTable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public DataTable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataTable{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
