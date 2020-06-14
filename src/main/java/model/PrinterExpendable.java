package model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class PrinterExpendable {
    @EmbeddedId
    private PrinterExpendablePK printerExpendablePK;
    @ManyToOne
    @MapsId("printerId")
    private Printer printer;
    @ManyToOne
    @MapsId("expendableName")
    private Expendable expendable;
    private String number;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Integer mileage = 0;
    @Transient
    private Boolean replaceable;

    public PrinterExpendable() {
    }

    public PrinterExpendable(Printer printer, Expendable expendable) {
        this.printer = printer;
        this.expendable = expendable;
    }

    public PrinterExpendablePK getPrinterExpendablePK() {
        return printerExpendablePK;
    }

    public void setPrinterExpendablePK(PrinterExpendablePK printerExpendablePK) {
        this.printerExpendablePK = printerExpendablePK;
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

    public Boolean getReplaceable() {
        if (this.replaceable == null) {
            this.replaceable = getExpendable().getEnumeration().getResource() <= getPrinter().getCounter() - getMileage();
        }
        return this.replaceable;
    }

    public void setReplaceable(Boolean replaceable) {
        this.replaceable = replaceable;
    }
}
