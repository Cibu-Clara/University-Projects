package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.programState.ProgramState;
import model.types.Type;

public class SleepStatement implements iStatement{
    private final int value;

    public SleepStatement(int value) {
        this.value = value;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (value != 0) {
            MyIStack<iStatement> exeStack = state.getExeStack();
            exeStack.push(new SleepStatement(value - 1));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new SleepStatement(value);
    }

    @Override
    public String toString() {
        return String.format("sleep(%s)", value);
    }
}
