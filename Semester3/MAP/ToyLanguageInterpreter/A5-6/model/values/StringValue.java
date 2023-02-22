package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value{
    String value;

    public StringValue(String value) { this.value = value; }

    public String getValue() { return this.value; }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() { return "\"" + this.value + "\""; }

    public boolean equals (Value anotherValue) {
        if (!(anotherValue instanceof StringValue))
            return false;
        return this.value.equals(((StringValue) anotherValue).value);
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }
}
