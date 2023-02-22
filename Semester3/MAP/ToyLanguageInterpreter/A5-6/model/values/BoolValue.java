package model.values;

import model.types.Type;
import model.types.BoolType;
public class BoolValue implements Value{
    boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public Type getType() {return new BoolType();}

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof BoolValue))
            return false;
        return this.value == ((BoolValue) anotherValue).value;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }
}
