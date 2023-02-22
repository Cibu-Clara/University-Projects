package model.programState;
import exceptions.ADTException;
import model.ADTs.MyIHeap;
import model.statements.iStatement;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIList;
import model.ADTs.MyIStack;
import model.values.Value;
import java.io.BufferedReader;
import java.util.List;

public class ProgramState {
    private MyIStack<iStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heapTable;
    iStatement originalProgram;

    public ProgramState(MyIStack<iStatement> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heapTable, iStatement program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.originalProgram = program.deepCopy();
        this.exeStack.push(this.originalProgram);
    }

    public void setExeStack(MyIStack<iStatement> newStack) {
        this.exeStack = newStack;
    }

    public void setSymTable(MyIDictionary<String, Value> newSymTable) {
        this.symTable = newSymTable;
    }

    public void setOut(MyIList<Value> newOut) {
        this.out = newOut;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> newFileTable) {
        this.fileTable = newFileTable;
    }

    public void setHeapTable(MyIHeap newHeapTable) { this.heapTable = newHeapTable; }

    public MyIStack<iStatement> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() { return out; }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap getHeapTable() { return heapTable; }

    @Override
    public String toString() {
        return "Execution stack: \n" + exeStack.getReversed() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() + "\nFile table: \n" + fileTable.keySet().toString() + "\nHeap table: \n" + heapTable.toString();
    }
    public String exeStackToString() {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<iStatement> stack = exeStack.getReversed();
        for (iStatement statement: stack) {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key: symTable.keySet()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder outStringBuilder = new StringBuilder();
        for (Value elem: out.getList()) {
            outStringBuilder.append(String.format("%s\n", elem.toString()));
        }
        return outStringBuilder.toString();
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapTableToString() throws ADTException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heapTable.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heapTable.get(key)));
        }
        return heapStringBuilder.toString();
    }
    public String programStateToString() throws ADTException {
        return "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n" + outToString() + "File table:\n" + fileTableToString() + "Heap table:\n" + heapTableToString();
    }
}
