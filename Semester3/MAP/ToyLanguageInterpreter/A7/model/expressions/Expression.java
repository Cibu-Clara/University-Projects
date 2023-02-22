package model.expressions;

import exceptions.InterpreterException;
import model.ADTs.MyIHeap;
import model.types.Type;
import model.values.Value;
import model.ADTs.MyIDictionary;

public interface Expression {
    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws  InterpreterException;
    Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws InterpreterException;
    String toString();
    Expression deepCopy();
}
