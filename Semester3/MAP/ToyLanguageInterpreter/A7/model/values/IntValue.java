package model.values;

import model.types.Type;
import model.types.IntType;

public class IntValue implements Value{
    int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.format("%d", this.value);
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof IntValue))
            return false;
        return this.value == ((IntValue) anotherValue).value;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }
}
