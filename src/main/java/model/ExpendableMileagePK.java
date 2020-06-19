package model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ExpendableMileagePK implements Serializable {
    @Column
    private String expendableName;
    @Column
    private String printerId;
}
