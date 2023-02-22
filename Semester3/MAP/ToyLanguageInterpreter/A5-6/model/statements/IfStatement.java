package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.ADTs.MyIDictionary;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.ADTs.MyIStack;

public class IfStatement implements iStatement{
    Expression expression;
    iStatement thenStatement;
    iStatement elseStatement;

    public IfStatement(Expression expression, iStatement thenStatement, iStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnv.deepCopy());
            elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementExecutionException("The condition of IF does not have the type Bool.");
    }

    @Override
    public String toString() {
        return String.format("IF(%s)THEN{%s}ELSE{%s}", expression.toString(), thenStatement.toString(), elseStatement.toString());
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeapTable());
        if (result instanceof BoolValue boolResult) {
            iStatement statement;
            if (boolResult.getValue()) {
                statement = thenStatement;
            } else {
                statement = elseStatement;
            }
            MyIStack<iStatement> stack = state.getExeStack();
            stack.push(statement);
            state.setExeStack(stack);
            return null;
        } else {
            throw new StatementExecutionException("Conditional expression is not a boolean.");
        }
    }

    @Override
    public iStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }
}
