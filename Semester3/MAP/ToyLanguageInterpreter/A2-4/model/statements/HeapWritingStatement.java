package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.values.RefValue;
import model.values.Value;

public class HeapWritingStatement implements iStatement{
    String varName;
    Expression expression;

    public HeapWritingStatement(String varName, Expression expression) { this.varName = varName; this.expression = expression; }

    @Override
    public String toString() { return String.format("WriteHeap(%s, %s)", varName, expression);}

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException
    {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heapTable = state.getHeapTable();
        if(!symTable.isDefined(varName))
            throw new StatementExecutionException(String.format("%s is not present in the symbol table", varName));
        Value value = symTable.lookUp(varName);
        if(!(value instanceof RefValue refValue))
            throw new StatementExecutionException(String.format("%s is not of RefType", value));
        Value evaluated = expression.eval(symTable, heapTable);
        if (!evaluated.getType().equals(refValue.getLocationType()))
            throw new StatementExecutionException(String.format("%s is not of %s", evaluated, refValue.getLocationType()));
        heapTable.update(refValue.getAddress(), evaluated);
        state.setHeapTable(heapTable);
        return state;
    }

    @Override
    public iStatement deepCopy() {
        return new HeapWritingStatement(varName, expression.deepCopy());
    }
}
