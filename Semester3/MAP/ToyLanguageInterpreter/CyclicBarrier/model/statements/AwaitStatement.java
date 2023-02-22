package model.statements;

import exceptions.InterpreterException;
import javafx.util.Pair;
import model.ADTs.MyIBarrier;
import model.ADTs.MyIDictionary;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements iStatement{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public AwaitStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIBarrier barrierTable = state.getBarrierTable();
        if (symTable.isDefined(var)) {
            IntValue f = (IntValue) symTable.lookUp(var);
            int foundIndex = f.getValue();
            if (barrierTable.containsKey(foundIndex)) {
                Pair<Integer, List<Integer>> foundBarrier = barrierTable.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                ArrayList<Integer> list = (ArrayList<Integer>) foundBarrier.getValue();
                if (N1 > NL) {
                    if (list.contains(state.getId()))
                        state.getExeStack().push(this);
                    else {
                        list.add(state.getId());
                        barrierTable.update(foundIndex, new Pair<>(N1, list));
                        state.setBarrierTable(barrierTable);
                    }
                }
            } else {
                throw new InterpreterException("Index not in Barrier Table!");
            }
        } else {
            throw new InterpreterException("Var is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            return typeEnv;
        else
            throw new InterpreterException("Var is not of type int!");
    }

    @Override
    public iStatement deepCopy() {
        return new AwaitStatement(var);
    }

    @Override
    public String toString() {
        return String.format("await(%s)", var);
    }
}
