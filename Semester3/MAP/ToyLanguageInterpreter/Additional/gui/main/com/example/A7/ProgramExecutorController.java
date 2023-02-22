package com.example.A7;

import controller.Controller;
import exceptions.InterpreterException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.ADTs.MyIDictionary;
import model.ADTs.MyIHeap;
import model.programState.ProgramState;
import model.statements.iStatement;
import model.values.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProgramExecutorController {
    private Controller controller;

    @FXML
    private TextField numberProgramStatesTextField;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> varNameColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> varValueColumn;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    public Button runOneStepButton;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        varNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        varValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
    }

    private ProgramState getCurrentProgramState() {
        if (controller.getProgramStates().size() == 0)
            return null;
        else {
            int currentId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return controller.getProgramStates().get(0);
            else
                return controller.getProgramStates().get(currentId);
        }
    }

    private void populate() {
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();
    }

    private void populateHeapTableView() {
        ProgramState programState = getCurrentProgramState();
        MyIHeap heapTable = Objects.requireNonNull(programState).getHeapTable();
        ArrayList<Pair<Integer, Value>> heapEntries = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry: heapTable.getContent().entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateOutputListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<Value> outputList = Objects.requireNonNull(programState).getOut().getList();
        for (Value value : outputList) {
            output.add(value.toString());
        }
        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTableListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getContent().keySet());
        fileTableListView.setItems(FXCollections.observableList(files));
    }

    private void populateProgramStateIdentifiersListView() {
        List<ProgramState> programStates = controller.getProgramStates();
        List<Integer> idList = programStates.stream().map(ProgramState::getId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberProgramStatesTextField();
    }

    private void populateNumberProgramStatesTextField() {
        List<ProgramState> programStates = controller.getProgramStates();
        numberProgramStatesTextField.setText(String.valueOf(programStates.size()));
    }

    private void populateSymbolTableView() {
        ProgramState programState = getCurrentProgramState();
        MyIDictionary<String, Value> symTable = Objects.requireNonNull(programState).getSymTable();
        ArrayList<Pair<String, Value>> symTableEntries = new ArrayList<>();
        for (Map.Entry<String, Value> entry: symTable.getContent().entrySet()) {
            symTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symTableEntries));
    }

    private void populateExecutionStackListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> exeStackToString = new ArrayList<>();
        if (programState != null)
            for (iStatement statement: programState.getExeStack().getReversed()){
                exeStackToString.add(statement.toString());
            }
        executionStackListView.setItems(FXCollections.observableList(exeStackToString));
    }

    @FXML
    private void runOneStep(MouseEvent mouseEvent) {
        if (controller != null) {
            try {
                List<ProgramState> programStates = Objects.requireNonNull(controller.getProgramStates());
                if (programStates.size() > 0) {
                    controller.oneStep();
                    populate();
                    programStates = controller.removeCompletedPrograms(controller.getProgramStates());
                    controller.setProgramStates(programStates);
                    populateProgramStateIdentifiersListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occured!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }
            } catch (InterpreterException | InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error!");
                alert.setHeaderText("An execution error has occured!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occured!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }

    @FXML
    private void changeProgramState(MouseEvent event) {
        populateExecutionStackListView();
        populateSymbolTableView();
    }
}
