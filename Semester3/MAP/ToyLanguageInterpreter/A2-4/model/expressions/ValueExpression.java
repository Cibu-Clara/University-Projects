package model.expressions;

import exceptions.ExpressionEvaluationException;
import model.ADTs.MyIHeap;
import model.types.Type;
import model.values.Value;
import model.ADTs.MyIDictionary;

public class ValueExpression implements Expression{
    Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value);
    }
}
