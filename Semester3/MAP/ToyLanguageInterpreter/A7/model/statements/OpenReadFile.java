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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements iStatement{
    Expression expression;

    public OpenReadFile(Expression expression) { this.expression = expression; }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new InterpreterException("OpenReadFile requires a String expression.");
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeapTable());
        if (result.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) result;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getValue())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));
                } catch (FileNotFoundException e) {
                    throw new InterpreterException(String.format("File %s could not be open.", fileName.getValue()));
                }
                fileTable.put(fileName.getValue(), br);
                state.setFileTable(fileTable);
            } else {
                throw new InterpreterException(String.format("File %s is already open.", fileName.getValue()));
            }
        }
        else {
            throw new InterpreterException(String.format("%s is not a String.", expression.toString()));
        }

        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

}
