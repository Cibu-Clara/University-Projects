package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import model.types.Type;
import model.values.Value;
import model.programState.ProgramState;
import model.ADTs.MyIDictionary;
import exceptions.StatementExecutionException;

public class VariableDeclarationStatement implements iStatement{
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new StatementExecutionException("Variable " + name + " is already declared in the Symbol Table.");
        }
        symTable.put(name, type.defaultValue());
        state.setSymTable(symTable);
        return state;
    }

    @Override
    public iStatement deepCopy() {
        return new VariableDeclarationStatement(name, type);
    }
}
