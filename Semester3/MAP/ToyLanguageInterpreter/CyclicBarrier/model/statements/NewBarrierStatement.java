package model.statements;

import exceptions.InterpreterException;
import javafx.util.Pair;
import model.ADTs.MyIBarrier;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStatement implements iStatement{
    private final String var;
    private final Expression expression;
    private static final Lock lock = new ReentrantLock();

    public NewBarrierStatement(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeapTable();
        MyIBarrier barrierTable = state.getBarrierTable();
        IntValue number = (IntValue) (expression.eval(symTable, heap));
        int nr = number.getValue();
        int freeAddress = barrierTable.getFreeAddress();
        barrierTable.put(freeAddress, new Pair<>(nr, new ArrayList<>()));
        if (symTable.isDefined(var))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new InterpreterException(String.format("%s is not defined in the symbol table!", var));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            if (expression.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new InterpreterException("Expression is not of type int!");
        else
            throw new InterpreterException("Variable is not of type int!");
    }

    @Override
    public iStatement deepCopy() {
        return new NewBarrierStatement(var, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("newBarrier(%s, %s)", var, expression);
    }
}
