package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Printer {
    @Id
    private String inventoryNumber;
    private String model;
    private String location;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Integer counter = 0;
    @OneToMany(mappedBy = "printer")
    private Set<PrinterExpendable> expendables = new LinkedHashSet<>();

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public Set<PrinterExpendable> getExpendables() {
        return expendables;
    }

    public void setExpendables(Set<PrinterExpendable> expendables) {
        this.expendables = expendables;
    }

    public Set<PrinterExpendable> getReplaceableExpendables() {
        //TODO extract to transient property
        return expendables.stream().filter(PrinterExpendable::getReplaceable).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
