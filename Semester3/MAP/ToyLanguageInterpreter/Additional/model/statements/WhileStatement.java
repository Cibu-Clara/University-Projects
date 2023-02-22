package model.statements;

import exceptions.InterpreterException;
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
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws InterpreterException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new InterpreterException("The condition of WHILE does not have the type Bool.");
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException
    {
        Value value = expression.eval(state.getSymTable(), state.getHeapTable());
        MyIStack<iStatement> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new InterpreterException(String.format("%s is not a boolean", value));
        BoolValue boolValue = (BoolValue) value;
        if(boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public iStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }
}
