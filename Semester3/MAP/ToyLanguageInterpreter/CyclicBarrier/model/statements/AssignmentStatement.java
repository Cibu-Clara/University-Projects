package model.statements;

import model.programState.ProgramState;
import exceptions.InterpreterException;
import model.types.Type;
import model.values.Value;
import model.expressions.Expression;
import model.ADTs.MyIDictionary;

public class AssignmentStatement implements iStatement{
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String,Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws InterpreterException {
        Type typeVar = typeEnv.lookUp(id);
        Type typExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(typExp))
            return typeEnv;
        else
            throw new InterpreterException("Assignment: right hand side and left hand side have different types.");
    }

    @Override
    public String toString() {
        return String.format("%s = %s", id, expression.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();

        if (symbolTable.isDefined(id)) {
            Value value = expression.eval(symbolTable, state.getHeapTable());
            Type typeId = (symbolTable.lookUp(id)).getType();
            if (value.getType().equals(typeId)) {
                symbolTable.update(id, value);
            } else {
                throw new InterpreterException("Declared type of variable " + id + " and type of the assigned expression do not match.");
            }
        } else {
            throw new InterpreterException("The used variable " + id + " was not declared before.");
        }
        state.setSymTable(symbolTable);
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new AssignmentStatement(id, expression.deepCopy());
    }
}
