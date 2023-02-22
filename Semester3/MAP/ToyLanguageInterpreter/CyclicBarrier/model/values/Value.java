package model.values;

import model.types.Type;

public interface Value {
    Type getType();
    String toString();
    Value deepCopy();
}
