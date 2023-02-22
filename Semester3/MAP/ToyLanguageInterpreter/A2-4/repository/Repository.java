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
    int currentPosition;
    String logFilePath;

    public Repository(ProgramState programState, String logFilePath){
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<>();
        this.currentPosition = 0;
        this.addProgram(programState);
    }

    @Override
    public ProgramState getCurrentState() {
        return this.programStates.get(this.currentPosition);
    }

    @Override
    public void addProgram(ProgramState program) {
        this.programStates.add(program);
    }

    @Override
    public void logPrgStateExec() throws ADTException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(this.programStates.get(0).programStateToString());
        logFile.close();
    }
}
