package model;

public class ExpendableResourceMileage {
    private final ExpendableResource expendableResource;
    private final ExpendableMileage expendableMileage;
    private Boolean replaceable;

    public ExpendableResourceMileage(ExpendableResource expendableResource, ExpendableMileage expendableMileage) {
        this.expendableResource = expendableResource;
        this.expendableMileage = expendableMileage;
    }

    public ExpendableResource getExpendableResource() {
        return expendableResource;
    }

    public Boolean getReplaceable() {
        if (replaceable == null) {
            replaceable = expendableMileage == null || expendableMileage.getMileage() > expendableResource.getResource();
        }
        return replaceable;
    }

    public void setReplaceable(Boolean replaceable) {
        this.replaceable = replaceable;
    }
}