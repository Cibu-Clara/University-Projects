package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.Type;

public class DoWhileStatement implements iStatement{
    private final iStatement statement;
    private final Expression expression;

    public DoWhileStatement(Expression expression, iStatement statement) {
        this.statement = statement;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        iStatement converted = new CompoundStatement(statement, new WhileStatement(expression, statement));
        state.getExeStack().push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExpression = expression.typeCheck(typeEnv);
        if (typeExpression.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new InterpreterException("Condition in the do while statement must be bool!");
    }

    @Override
    public iStatement deepCopy() {
        return new DoWhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("do {%s} while (%s)", statement, expression);
    }
}
