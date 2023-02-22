package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class HeapReadingExpression implements Expression{
    Expression expression;

    public HeapReadingExpression(Expression expression) { this.expression = expression; }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws ExpressionEvaluationException, ADTException {
        Value value = expression.eval(symTable, heapTable);
        if (!(value instanceof RefValue refValue))
            throw new ExpressionEvaluationException(String.format("%s is not a RefType", value));
        return heapTable.get(refValue.getAddress());
    }

    @Override
    public String toString() { return String.format("ReadHeap(%s)", expression); }

    @Override
    public Expression deepCopy() { return new HeapReadingExpression(expression.deepCopy());}
}
