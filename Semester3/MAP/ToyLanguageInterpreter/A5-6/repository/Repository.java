package repository;

import exceptions.ADTException;
import model.programState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements iRepository{
    List<ProgramState> programStates;
    String logFilePath;

    public Repository(ProgramState programState, String logFilePath){
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<>();
        this.addProgram(programState);
    }

    @Override
    public List<ProgramState> getProgramStates() { return this.programStates; }

    @Override
    public void setProgramStates(List <ProgramState> programStates) { this.programStates = programStates; }

    @Override
    public void addProgram(ProgramState program) { this.programStates.add(program); }

    @Override
    public void logPrgStateExec(ProgramState programState) throws ADTException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.programStateToString());
        logFile.close();
    }
}
