package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.programState.ProgramState;
import model.types.Type;

public class NopStatement implements iStatement{
    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new NopStatement();
    }
}
