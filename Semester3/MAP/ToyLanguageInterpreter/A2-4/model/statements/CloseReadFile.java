package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements iStatement{
    Expression expression;

    public CloseReadFile(Expression expression) { this.expression = expression; }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expression.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeapTable());
        if (result.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) result;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.isDefined(fileName.getValue())){
                BufferedReader br = fileTable.lookUp(fileName.getValue());
                try {
                    br.close();
                } catch (IOException e) {
                    throw new StatementExecutionException(String.format("Could not close the file %s.", fileName));
                }
                fileTable.remove(fileName.getValue());
                state.setFileTable(fileTable);
            }
            else {
                throw new StatementExecutionException(String.format("The file table doesn't contain the file %s.", fileName));
            }
        }
        else{
            throw new StatementExecutionException(String.format("%s is not a String.", expression.toString()));
        }
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }
}
