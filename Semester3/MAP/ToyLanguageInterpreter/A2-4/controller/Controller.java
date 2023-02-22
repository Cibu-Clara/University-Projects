package controller;

import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;
import model.programState.ProgramState;
import model.values.RefValue;
import model.values.Value;
import repository.iRepository;
import model.statements.iStatement;
import model.ADTs.MyIStack;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    iRepository repository;
    boolean displayFlag = false;

    public Controller(iRepository repository) { this.repository = repository;}

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapTableAddr, Map<Integer,Value> heapTable){
        return heapTable.entrySet().stream()
                .filter(elem -> (symTableAddr.contains(elem.getKey()) || heapTableAddr.contains(elem.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues) {
        return heapTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public ProgramState oneStep(ProgramState state) throws StatementExecutionException, ADTException, ExpressionEvaluationException {
        MyIStack<iStatement> stack = state.getExeStack();
        if (stack.isEmpty())
            throw new StatementExecutionException("Program state stack is empty.");
        iStatement currentStatement = stack.pop();
        state.setExeStack(stack);
        return currentStatement.execute(state);
    }

    public void allSteps() throws ExpressionEvaluationException, ADTException, StatementExecutionException, IOException {
        ProgramState program = this.repository.getCurrentState();
        display();
        this.repository.logPrgStateExec();
        while(!program.getExeStack().isEmpty()) {
            oneStep(program);
            this.repository.logPrgStateExec();
            program.getHeapTable().setContent((HashMap<Integer, Value>) safeGarbageCollector(
                    getAddrFromSymTable(program.getSymTable().getContent().values()),
                    getAddrFromHeapTable(program.getHeapTable().getContent().values()),
                    program.getHeapTable().getContent()));
            this.repository.logPrgStateExec();
            display();
        }
    }

    private void display() {
        if (displayFlag) {
            System.out.println(this.repository.getCurrentState().toString());
        }
    }
}
