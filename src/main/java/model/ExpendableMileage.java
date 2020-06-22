package model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class ExpendableMileage {
    @EmbeddedId
    private ExpendableMileagePK expendableMileagePK;
    @ManyToOne(optional = false)
    @MapsId("printerId")
    private Printer printer;
    @ManyToOne(optional = false)
    @MapsId("expendableName")
    private Expendable expendable;
    private String number;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Integer mileage;

    public ExpendableMileagePK getExpendableMileagePK() {
        return expendableMileagePK;
    }

    public void setExpendableMileagePK(ExpendableMileagePK expendableMileagePK) {
        this.expendableMileagePK = expendableMileagePK;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Expendable getExpendable() {
        return expendable;
    }

    public void setExpendable(Expendable expendable) {
        this.expendable = expendable;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
}
