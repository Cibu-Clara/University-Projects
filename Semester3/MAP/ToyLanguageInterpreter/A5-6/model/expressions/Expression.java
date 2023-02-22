package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import model.ADTs.MyIHeap;
import model.types.Type;
import model.values.Value;
import model.ADTs.MyIDictionary;

public interface Expression {
    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws ExpressionEvaluationException, ADTException;
    Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws ExpressionEvaluationException, ADTException;
    String toString();
    Expression deepCopy();
}
