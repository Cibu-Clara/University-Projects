package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.programState.ProgramState;
import model.types.Type;

public class NopStatement implements iStatement{
    @Override
    public String toString() {
        return "No operation statement";
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
