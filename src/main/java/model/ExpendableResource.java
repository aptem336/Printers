package model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class ExpendableResource {
    @EmbeddedId
    private ExpendableResourcePK expendableResourcePK;
    @ManyToOne(optional = false)
    @MapsId("printerModelId")
    private PrinterModel printerModel;
    @ManyToOne(optional = false)
    @MapsId("expendableName")
    private Expendable expendable;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Integer resource = 0;

    public ExpendableResourcePK getExpendableResourcePK() {
        return expendableResourcePK;
    }

    public void setExpendableResourcePK(ExpendableResourcePK expendableResourcePK) {
        this.expendableResourcePK = expendableResourcePK;
    }

    public PrinterModel getPrinterModel() {
        return printerModel;
    }

    public void setPrinterModel(PrinterModel printerModel) {
        this.printerModel = printerModel;
    }

    public Expendable getExpendable() {
        return expendable;
    }

    public void setExpendable(Expendable expendable) {
        this.expendable = expendable;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }
}
