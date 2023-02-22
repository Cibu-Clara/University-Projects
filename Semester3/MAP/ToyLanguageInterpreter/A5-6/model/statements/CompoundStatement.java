package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.programState.ProgramState;
import model.ADTs.MyIStack;
import model.types.Type;

public class CompoundStatement implements iStatement{
    iStatement first;
    iStatement second;

    public CompoundStatement(iStatement first, iStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return String.format("(%s ; %s)", first.toString(), second.toString());
    }

    @Override
    public ProgramState execute(ProgramState state){
        MyIStack<iStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }
}
