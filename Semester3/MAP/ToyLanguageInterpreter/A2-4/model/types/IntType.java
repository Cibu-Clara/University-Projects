package model.types;

import model.values.Value;
import model.values.IntValue;

public class IntType implements Type{
    @Override
    public boolean equals(Type anotherType) { return anotherType instanceof IntType; }

    @Override
    public String toString() { return "int"; }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
