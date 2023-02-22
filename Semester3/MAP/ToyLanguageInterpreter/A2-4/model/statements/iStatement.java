package model.statements;

import model.ADTs.MyIDictionary;
import model.programState.ProgramState;
import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.types.Type;

public interface iStatement {
    String toString();
    ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException;
    iStatement deepCopy();
}
