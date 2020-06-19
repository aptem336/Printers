package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Expendable {
    @Id
    private String name;

    public Expendable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expendable that = (Expendable) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
