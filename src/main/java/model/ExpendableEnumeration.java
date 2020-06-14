package model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public enum ExpendableEnumeration {
    ROLLERS("Ролики", 1000),
    CARTRIDGE("Картридж", 1000);

    @NotNull
    private final String name;
    @NotNull
    @Min(0)
    private final Integer resource;

    ExpendableEnumeration(String name, Integer resource) {
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public Integer getResource() {
        return resource;
    }
}
