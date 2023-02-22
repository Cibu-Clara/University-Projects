package view;

import controller.Controller;
import exceptions.ADTException;
import exceptions.ExpressionEvaluationException;
import exceptions.StatementExecutionException;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExampleCommand extends Command{
    private final Controller ctrl;

    public RunExampleCommand(String key, String description, Controller ctrl){
        super(key, description);
        this.ctrl = ctrl;
    }

    @Override
    public void execute() {
        try{
            System.out.println("Do you want to display the steps?[y/n]");
            Scanner readOption = new Scanner(System.in);
            String option = readOption.next();
            ctrl.setDisplayFlag(Objects.equals(option, "y"));
            ctrl.allSteps();
        } catch (ExpressionEvaluationException | ADTException | StatementExecutionException | IOException | InterruptedException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}
