package model;

import java.util.Objects;

public class ReplaceableExpendable {
    private final String expendableName;
    private Boolean replaceable;

    public ReplaceableExpendable(String expendableName, Boolean replaceable) {
        this.expendableName = expendableName;
        this.replaceable = replaceable;
    }

    public String getExpendableName() {
        return expendableName;
    }

    public Boolean getReplaceable() {
        return replaceable;
    }

    public void setReplaceable(Boolean replaceable) {
        this.replaceable = replaceable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplaceableExpendable that = (ReplaceableExpendable) o;
        return Objects.equals(getExpendableName(), that.getExpendableName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpendableName());
    }
}