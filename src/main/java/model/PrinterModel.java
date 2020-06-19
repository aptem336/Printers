package model;

import controller.QueryController;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = QueryController.getAllPrinterModels, query = "SELECT pm FROM PrinterModel pm")
public class PrinterModel {
    @Id
    private String model;
    @OneToMany(mappedBy = "printerModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpendableResource> expendableResources = new ArrayList<>();

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ExpendableResource> getExpendableResources() {
        return expendableResources;
    }

    public void setExpendableResources(List<ExpendableResource> expendables) {
        this.expendableResources = expendables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrinterModel that = (PrinterModel) o;
        return Objects.equals(getModel(), that.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel());
    }
}
