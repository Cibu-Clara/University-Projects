package model.statements;

import exceptions.InterpreterException;
import model.types.Type;
import model.values.Value;
import model.programState.ProgramState;
import model.ADTs.MyIDictionary;

public class VariableDeclarationStatement implements iStatement{
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public MyIDictionary<String,Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws InterpreterException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new InterpreterException("Variable " + name + " is already declared in the Symbol Table.");
        }
        symTable.put(name, type.defaultValue());
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new VariableDeclarationStatement(name, type);
    }
}
