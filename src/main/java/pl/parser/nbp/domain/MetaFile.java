package pl.parser.nbp.domain;

import java.time.LocalDate;

/**
 * Created by Benek on 09.04.2017.
 */
public class MetaFile {

    private String name;
    private LocalDate data;

    public MetaFile(String fileName, LocalDate date) {
        this.name = fileName;
        this.data = date;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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
