package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class HeapAllocationStatement implements iStatement{
    String varName;
    Expression expression;

    public HeapAllocationStatement(String varName, Expression expression)
    {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String,Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws InterpreterException {
        Type typeVar = typeEnv.lookUp(varName);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new InterpreterException("NEW statement: right hand side and left hand side have different types.");
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heapTable = state.getHeapTable();
        if (!symTable.isDefined(varName))
            throw new InterpreterException(String.format("%s is not in the symbol table", varName));
        Value varValue = symTable.lookUp(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new InterpreterException(String.format("%s in not a RefType", varName));
        Value evaluated = expression.eval(symTable, heapTable);
        Type locationType = ((RefValue)varValue).getLocationType();
        if (!locationType.equals(evaluated.getType()))
            throw new InterpreterException(String.format("%s not of %s", varName, evaluated.getType()));
        int newPosition = heapTable.add(evaluated);
        symTable.put(varName, new RefValue(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeapTable(heapTable);
        return null;
    }

    @Override
    public iStatement deepCopy() { return new HeapAllocationStatement(varName, expression.deepCopy());}
}
