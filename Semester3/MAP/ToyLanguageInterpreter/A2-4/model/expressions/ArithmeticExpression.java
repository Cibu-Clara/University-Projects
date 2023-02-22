package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import model.ADTs.MyIHeap;
import model.values.Value;
import model.values.IntValue;
import model.types.IntType;
import model.ADTs.MyIDictionary;

public class ArithmeticExpression implements Expression{
    Expression expression1;
    Expression expression2;
    char operation;

    public ArithmeticExpression(char operation, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws ExpressionEvaluationException, ADTException {
        Value value1, value2;
        value1 = this.expression1.eval(symTable, heapTable);
        if (value1.getType().equals(new IntType())) {
            value2 = this.expression2.eval(symTable, heapTable);
            if (value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int n1, n2;
                n1 = int1.getValue();
                n2 = int2.getValue();
                if (this.operation == '+')
                    return new IntValue(n1 + n2);
                else if (this.operation == '-')
                    return new IntValue(n1 - n2);
                else if (this.operation == '*')
                    return new IntValue(n1 * n2);
                else if (this.operation == '/')
                    if (n2 == 0)
                        throw new ExpressionEvaluationException("Division by zero.");
                    else
                        return new IntValue(n1 / n2);
            } else
                throw new ExpressionEvaluationException("Second operand is not an integer.");
        } else
            throw new ExpressionEvaluationException("First operand is not an integer.");
        return null;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(operation, expression1.deepCopy(), expression2.deepCopy());
    }
}
