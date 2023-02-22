package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.expressions.ValueExpression;
import model.programState.ProgramState;
import model.types.Type;
import model.values.IntValue;

public class WaitStatement implements iStatement{
    private final int value;

    public WaitStatement(int value) {
        this.value = value;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (value != 0) {
            MyIStack<iStatement> exeStack = state.getExeStack();
            exeStack.push(new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(value))),
                    new WaitStatement(value - 1)));
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
        return new WaitStatement(value);
    }

    @Override
    public String toString() {
        return String.format("wait(%s)", value);
    }
}
