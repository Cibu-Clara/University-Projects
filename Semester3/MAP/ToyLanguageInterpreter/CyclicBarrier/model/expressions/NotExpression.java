package model.expressions;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class NotExpression implements Expression{
    private final Expression expression;

    public NotExpression(Expression expression) { this.expression = expression; }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return expression.typeCheck(typeEnv);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws InterpreterException{
        BoolValue value = (BoolValue) expression.eval(symTable, heapTable);
        if (!value.getValue())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public String toString() { return String.format("!(%s)", expression); }

    @Override
    public Expression deepCopy() { return new NotExpression(expression.deepCopy()); }
}
