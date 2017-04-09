package pl.parser.nbp.model;

/**
 * Created by Benek on 09.04.2017.
 */
public class File {

    private String name;
    private String data;

    public File() {
    }

    public File(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
