package model.statements;

import exceptions.InterpreterException;
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
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new InterpreterException("CloseReadFile requires a String expression.");
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expression.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeapTable());
        if (result.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) result;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.isDefined(fileName.getValue())){
                BufferedReader br = fileTable.lookUp(fileName.getValue());
                try {
                    br.close();
                } catch (IOException e) {
                    throw new InterpreterException(String.format("Could not close the file %s.", fileName));
                }
                fileTable.remove(fileName.getValue());
                state.setFileTable(fileTable);
            }
            else {
                throw new InterpreterException(String.format("The file table doesn't contain the file %s.", fileName));
            }
        }
        else{
            throw new InterpreterException(String.format("%s is not a String.", expression.toString()));
        }
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }
}
