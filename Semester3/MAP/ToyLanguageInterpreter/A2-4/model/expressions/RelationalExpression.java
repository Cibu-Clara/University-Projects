package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

import java.util.Objects;

public class RelationalExpression implements Expression{
    Expression expr1;
    Expression expr2;
    String operator;

    public RelationalExpression(String operator, Expression expr1, Expression expr2){
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heapTable) throws ExpressionEvaluationException, ADTException {
        Value value1, value2;
        value1 = this.expr1.eval(symTable, heapTable);
        if (value1.getType().equals(new IntType())) {
            value2 = this.expr2.eval(symTable, heapTable);
            if (value2.getType().equals(new IntType())) {
                IntValue val1 = (IntValue) value1;
                IntValue val2 = (IntValue) value2;
                int v1, v2;
                v1 = val1.getValue();
                v2 = val2.getValue();
                if (Objects.equals(this.operator, "<"))
                    return new BoolValue(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(v1 >= v2);
            } else
                throw new ExpressionEvaluationException("Second operand is not an integer.");
        } else
            throw new ExpressionEvaluationException("First operand in not an integer.");
        return null;
    }

    @Override
    public String toString() {
        return this.expr1.toString() + " " + this.operator + " " + this.expr2.toString();
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(operator, expr1.deepCopy(), expr2.deepCopy());
    }
}
