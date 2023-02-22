package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;

public class ForStatement implements iStatement{
    private final String variable;
    private final Expression expression1;
    private final Expression expression2;
    private final Expression expression3;
    private final iStatement statement;

    public ForStatement(String variable, Expression expression1, Expression expression2, Expression expression3, iStatement statement) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIStack<iStatement> exeStack = state.getExeStack();
        iStatement converted = new CompoundStatement(new AssignmentStatement("v", expression1),
                new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), expression2),
                        new CompoundStatement(statement, new AssignmentStatement("v", expression3))));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);
        Type type3 = expression3.typeCheck(typeEnv);

        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        else
            throw new InterpreterException("The for statement is invalid!");
    }

    @Override
    public iStatement deepCopy() {
        return new ForStatement(variable, expression1.deepCopy(), expression2.deepCopy(), expression3.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", variable, expression1, variable, expression2, variable, expression3, statement);
    }
}
