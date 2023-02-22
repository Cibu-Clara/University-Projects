package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements iStatement{
    Expression expression;
    iStatement statement;

    public WhileStatement(Expression expression, iStatement statement) { this.expression = expression; this.statement = statement; }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException
    {
        Value value = expression.eval(state.getSymTable(), state.getHeapTable());
        MyIStack<iStatement> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new StatementExecutionException(String.format("%s is not a boolean", value));
        BoolValue boolValue = (BoolValue) value;
        if(boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return state;
    }

    @Override
    public iStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }
}
