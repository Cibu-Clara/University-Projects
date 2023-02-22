package model.types;

import model.values.RefValue;
import model.values.Value;

public class RefType implements Type{
    Type inner;

    public RefType(Type inner) { this.inner = inner; }

    public Type getInner() { return this.inner; }

    @Override
    public boolean equals(Type anotherType) {
        if (anotherType instanceof RefType)
            return inner.equals(((RefType) anotherType).getInner());
        else
            return false;
    }

    @Override
    public String toString() { return String.format("ref(%s)", inner); }

    @Override
    public Value defaultValue() { return new RefValue(0,inner);}
}
