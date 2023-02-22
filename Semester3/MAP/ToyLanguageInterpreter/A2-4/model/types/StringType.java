package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type{
    @Override
    public boolean equals(Type anotherType) { return anotherType instanceof StringType; }

    @Override
    public String toString() { return "string"; }

    @Override
    public Value defaultValue() { return new StringValue(""); }
}
