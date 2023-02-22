package model.statements;

import model.ADTs.MyIDictionary;
import model.programState.ProgramState;
import exceptions.InterpreterException;
import model.types.Type;

public interface iStatement {
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws InterpreterException;
    String toString();
    ProgramState execute(ProgramState state) throws InterpreterException;
    iStatement deepCopy();
}
