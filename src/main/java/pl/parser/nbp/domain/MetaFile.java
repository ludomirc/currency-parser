package pl.parser.nbp.domain;

/**
 * Created by Benek on 09.04.2017.
 */
public class MetaFile {

    private String name;
    private String data;

    public MetaFile() {
    }

    public MetaFile(String name, String data) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetaFile)) return false;

        MetaFile file = (MetaFile) o;

        if (!name.equals(file.name)) return false;
        return data.equals(file.data);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MetaFile{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
