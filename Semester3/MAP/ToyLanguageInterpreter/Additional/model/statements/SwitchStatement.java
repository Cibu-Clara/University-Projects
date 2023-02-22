package model.statements;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIStack;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.programState.ProgramState;
import model.types.Type;

public class SwitchStatement implements iStatement{
    private final Expression mainExpression;
    private final Expression expression1;
    private final iStatement statement1;
    private final Expression expression2;
    private final iStatement statement2;
    private final iStatement defaultStatement;

    public SwitchStatement(Expression mainExpression, Expression expression1, iStatement statement1, Expression expression2, iStatement statement2, iStatement defaultStatement) {
        this.mainExpression = mainExpression;
        this.expression1 = expression1;
        this.statement1 = statement1;
        this.expression2 = expression2;
        this.statement2 = statement2;
        this.defaultStatement = defaultStatement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIStack<iStatement> exeStack = state.getExeStack();
        iStatement converted = new IfStatement(new RelationalExpression("==", mainExpression, expression1),
                statement1, new IfStatement(new RelationalExpression("==", mainExpression, expression2), statement2, defaultStatement));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type mainType = mainExpression.typeCheck(typeEnv);
        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);
        if (mainType.equals(type1) && mainType.equals(type2)) {
            statement1.typeCheck(typeEnv.deepCopy());
            statement2.typeCheck(typeEnv.deepCopy());
            defaultStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new InterpreterException("The expression types don't match in the switch statement!");
        }
    }

    @Override
    public iStatement deepCopy() {
        return new SwitchStatement(mainExpression.deepCopy(), expression1.deepCopy(), statement1.deepCopy(), expression2.deepCopy(), statement2.deepCopy(), defaultStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", mainExpression, expression1, statement1, expression2, statement2, defaultStatement);
    }
}
