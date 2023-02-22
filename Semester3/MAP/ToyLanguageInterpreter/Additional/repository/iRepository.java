package repository;

import exceptions.InterpreterException;
import model.programState.ProgramState;
import java.io.IOException;
import java.util.List;

public interface iRepository {
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);
    void addProgram(ProgramState program);
    void logPrgStateExec(ProgramState programState) throws InterpreterException, IOException;
}
