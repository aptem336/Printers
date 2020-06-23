package model;

import controller.QueryController;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NamedQuery(name = QueryController.getAllPrinters, query = "SELECT p FROM Printer p")
public class Printer {
    @Id
    private String inventoryNumber;
    @ManyToOne(optional = false)
    private PrinterModel printerModel;
    private String location;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Integer counter = 0;
    @OneToMany(mappedBy = "printer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpendableMileage> expendableMileages = new ArrayList<>();
    @OneToMany(mappedBy = "printer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();
    @Transient
    private List<ReplaceableExpendable> replaceableExpendable;

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public PrinterModel getPrinterModel() {
        return printerModel;
    }

    public void setPrinterModel(PrinterModel model) {
        this.printerModel = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public List<ExpendableMileage> getExpendableMileages() {
        return expendableMileages;
    }

    public void setExpendableMileages(List<ExpendableMileage> expendables) {
        this.expendableMileages = expendables;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<ReplaceableExpendable> getReplaceableExpendables() {
        if (replaceableExpendable == null) {
            if (printerModel != null) {
                replaceableExpendable = printerModel.getExpendableResources().stream()
                        .filter(er -> getExpendableMileage(er) == null || er.getResource() < counter - getExpendableMileage(er).getMileage())
                        .map(er -> new ReplaceableExpendable(er.getExpendable().getName(), true, er.getNumber())).collect(Collectors.toList());
            }
        }
        return replaceableExpendable;
    }

    public void setReplaceableExpendable(List<ReplaceableExpendable> replaceableExpendable) {
        this.replaceableExpendable = replaceableExpendable;
    }

    public ExpendableMileage getExpendableMileage(ExpendableResource expendableResource) {
        return expendableMileages.stream().filter(expendableMileage ->
                expendableMileage.getExpendable().equals(expendableResource.getExpendable()))
                .findFirst().orElse(null);
    }
}
