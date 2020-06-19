package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ExpendableResourcePK implements Serializable {
    @Column
    private String printerModelId;
    @Column
    private String expendableName;
}
