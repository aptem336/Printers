package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PrinterExpendablePK implements Serializable {
    @Column
    private ExpendableEnumeration expendableName;
    @Column
    private String printerId;
}
