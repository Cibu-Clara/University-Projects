package repository;

import exceptions.ADTException;
import model.programState.ProgramState;
import java.io.IOException;

public interface iRepository {
    ProgramState getCurrentState();
    void addProgram(ProgramState program);
    void logPrgStateExec() throws ADTException, IOException;
}
