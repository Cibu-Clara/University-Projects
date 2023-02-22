package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import model.ADTs.MyIHeap;
import model.types.Type;
import model.values.Value;
import model.ADTs.MyIDictionary;
public class VariableExpression implements Expression{
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws ADTException {
        return symTable.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(id);
    }
}
