package model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Expendable {
    @Id
    @Enumerated
    private ExpendableEnumeration enumeration;

    public Expendable() {
    }

    public Expendable(ExpendableEnumeration enumeration) {
        this.enumeration = enumeration;
    }

    public ExpendableEnumeration getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(ExpendableEnumeration name) {
        this.enumeration = name;
    }
}
