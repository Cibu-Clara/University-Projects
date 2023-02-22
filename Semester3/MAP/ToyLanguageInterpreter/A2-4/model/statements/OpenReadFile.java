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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFile implements iStatement{
    Expression expression;

    public OpenReadFile(Expression expression) { this.expression = expression; }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeapTable());
        if (result.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) result;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getValue())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));
                } catch (FileNotFoundException e) {
                    throw new StatementExecutionException(String.format("File %s could not be open.", fileName.getValue()));
                }
                fileTable.put(fileName.getValue(), br);
                state.setFileTable(fileTable);
            } else {
                throw new StatementExecutionException(String.format("File %s is already open.", fileName.getValue()));
            }
        }
        else {
            throw new StatementExecutionException(String.format("%s is not a String.", expression.toString()));
        }

        return state;
    }

    @Override
    public iStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

}
