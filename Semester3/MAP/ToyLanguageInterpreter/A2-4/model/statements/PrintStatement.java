package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.types.Type;
import model.values.Value;
import model.expressions.Expression;
import model.ADTs.MyIList;
import model.programState.ProgramState;
public class PrintStatement implements iStatement{
    Expression expression;

    public PrintStatement(Expression expression) {this.expression = expression;}

    @Override
    public String toString() {
        return String.format("Print(%s)", expression.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionEvaluationException, ADTException {
        MyIList<Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable(), state.getHeapTable()));
        state.setOut(out);
        return state;
    }

    @Override
    public iStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
