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
    @ManyToOne
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
    private List<ExpendableResourceMileage> expendableResourceMileages;

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

    public List<ExpendableResourceMileage> getExpendableResourceMileages() {
        if (expendableResourceMileages == null && printerModel != null) {
            expendableResourceMileages = printerModel.getExpendableResources().stream().map(expendableResource ->
                    new ExpendableResourceMileage(expendableResource, getExpendableMileage(expendableResource)))
                    .collect(Collectors.toList());
        }
        return expendableResourceMileages;
    }

    public void setExpendableResourceMileages(List<ExpendableResourceMileage> expendableResourceMileages) {
        this.expendableResourceMileages = expendableResourceMileages;
    }

    public ExpendableMileage getExpendableMileage(ExpendableResource expendableResource) {
        return expendableMileages.stream().filter(expendableMileage ->
                expendableMileage.getExpendable().equals(expendableResource.getExpendable()))
                .findFirst().orElse(null);
    }

    public List<ExpendableResourceMileage> getReplaceableExpendableResourceMileage() {
        return getExpendableResourceMileages().stream()
                .filter(ExpendableResourceMileage::getReplaceable).collect(Collectors.toList());
    }

    public void replaceExpendable(ExpendableResource expendableResource) {
        ExpendableMileage expendableMileage = getExpendableMileage(expendableResource);
        if (expendableMileage == null) {
            expendableMileage = new ExpendableMileage();
            expendableMileage.setExpendable(expendableResource.getExpendable());
            expendableMileage.setNumber("");
            expendableMileage.setPrinter(this);
            this.getExpendableMileages().add(expendableMileage);
        }
        expendableMileage.setMileage(0);
    }

    public void addNote() {
        Note note = new Note();
        note.setPrinter(this);
        notes.add(note);
    }

    public void removeNote(Note note) {
        note.setPrinter(null);
        notes.remove(note);
    }
}
