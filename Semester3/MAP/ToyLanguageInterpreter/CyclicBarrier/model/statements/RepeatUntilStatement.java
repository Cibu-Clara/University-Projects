package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.expressions.Expression;
import model.expressions.NotExpression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.Type;

public class RepeatUntilStatement implements iStatement{
    private final iStatement statement;
    private final Expression expression;

    public RepeatUntilStatement(iStatement statement, Expression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException{
        Type type = expression.typeCheck(typeEnv);
        if (type.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new InterpreterException("Expression in the repeat statement must be of Bool Type.");
        }
    }

    @Override
    public String toString() { return String.format("repeat{%s} until (%s)", statement, expression); }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException{
        MyIStack<iStatement> exeStack = state.getExeStack();
        iStatement stmt = new CompoundStatement(statement, new WhileStatement(new NotExpression(expression), statement));
        exeStack.push(stmt);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public iStatement deepCopy() { return new RepeatUntilStatement(statement.deepCopy(), expression.deepCopy()); }
 }
