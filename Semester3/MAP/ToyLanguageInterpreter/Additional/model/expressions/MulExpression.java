package model.expressions;

import exceptions.InterpreterException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.types.IntType;
import model.types.Type;
import model.values.Value;

public class MulExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;

    public MulExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()))
            return new IntType();
        else
            throw new InterpreterException("Expressions in the MUL should be int!");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws InterpreterException {
        Expression converted = new ArithmeticExpression('-',
                new ArithmeticExpression('*', expression1, expression2),
                new ArithmeticExpression('+', expression1, expression2));
        return converted.eval(table, heap);
    }

    @Override
    public Expression deepCopy() {
        return new MulExpression(expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("MUL(%s, %s)", expression1, expression2);
    }
}
