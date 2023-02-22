package view;

import controller.Controller;
import exceptions.InterpreterException;
import model.ADTs.MyDictionary;
import model.ADTs.MyHeap;
import model.ADTs.MyList;
import model.ADTs.MyStack;
import model.expressions.*;
import model.programState.ProgramState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.Repository;
import repository.iRepository;

import java.io.IOException;

public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        iStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        try {
            ex1.typeCheck(new MyDictionary<>());
            ProgramState prg1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex1);
            iRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctrl1 = new Controller(repo1);
            menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctrl1));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        try {
            ex2.typeCheck(new MyDictionary<>());
            ProgramState prg2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex2);
            iRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctrl2 = new Controller(repo2);
            menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctrl2));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        try {
            ex3.typeCheck(new MyDictionary<>());
            ProgramState prg3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex3);
            iRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctrl3 = new Controller(repo3);
            menu.addCommand(new RunExampleCommand("3", ex2.toString(), ctrl3));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))))))))));

        try {
            ex4.typeCheck(new MyDictionary<>());
            ProgramState prg4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex4);
            iRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctrl4 = new Controller(repo4);
            menu.addCommand(new RunExampleCommand("4", ex2.toString(), ctrl4));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(5))),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(7))),
                                        new IfStatement(new RelationalExpression(">", new VariableExpression("a"),
                                                new VariableExpression("b")),new PrintStatement(new VariableExpression("a")),
                                                new PrintStatement(new VariableExpression("b")))))));
        try {
            ex5.typeCheck(new MyDictionary<>());
            ProgramState prg5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex5);
            iRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctrl5 = new Controller(repo5);
            menu.addCommand(new RunExampleCommand("5", ex2.toString(), ctrl5));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        try {
            ex6.typeCheck(new MyDictionary<>());
            ProgramState prg6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex6);
            iRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctrl6 = new Controller(repo6);
            menu.addCommand(new RunExampleCommand("6", ex2.toString(), ctrl6));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

        try {
            ex7.typeCheck(new MyDictionary<>());
            ProgramState prg7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
            iRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctrl7 = new Controller(repo7);
            menu.addCommand(new RunExampleCommand("7", ex2.toString(), ctrl7));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

        try {
            ex8.typeCheck(new MyDictionary<>());
            ProgramState prg8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
            iRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctrl8 = new Controller(repo8);
            menu.addCommand(new RunExampleCommand("8", ex2.toString(), ctrl8));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement( new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        try {
            ex9.typeCheck(new MyDictionary<>());
            ProgramState prg9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
            iRepository repo9 = new Repository(prg9, "log9.txt");
            Controller ctrl9 = new Controller(repo9);
            menu.addCommand(new RunExampleCommand("9", ex2.toString(), ctrl9));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

        try{
            ex10.typeCheck(new MyDictionary<>());
            ProgramState prg10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
            iRepository repo10 = new Repository(prg10, "log10.txt");
            Controller ctrl10 = new Controller(repo10);
            menu.addCommand(new RunExampleCommand("10", ex2.toString(), ctrl10));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        iStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));

        try {
            ex11.typeCheck(new MyDictionary<>());
            ProgramState prg11 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
            iRepository repo11 = new Repository(prg11, "log11.txt");
            Controller ctrl11 = new Controller(repo11);
            menu.addCommand(new RunExampleCommand("11", ex11.toString(), ctrl11));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //MUL EXPRESSION
        iStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v1", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("v2", new IntType()),
                        new CompoundStatement(new AssignmentStatement("v1", new ValueExpression(new IntValue(2))),
                                new CompoundStatement(new AssignmentStatement("v2", new ValueExpression(new IntValue(3))),
                                        new IfStatement(new RelationalExpression("!=", new VariableExpression("v1"), new ValueExpression(new IntValue(0))),
                                                new PrintStatement(new MulExpression(new VariableExpression("v1"), new VariableExpression("v2"))),
                                                new PrintStatement(new VariableExpression("v1")))))));

        try {
            ex12.typeCheck(new MyDictionary<>());
            ProgramState prg12 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex12);
            iRepository repo12 = new Repository(prg12, "log12.txt");
            Controller ctrl12 = new Controller(repo12);
            menu.addCommand(new RunExampleCommand("12", ex12.toString(), ctrl12));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //WAIT STATEMENT
        iStatement ex13 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new WaitStatement(10),
                                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

        try {
            ex13.typeCheck(new MyDictionary<>());
            ProgramState prg13 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex13);
            iRepository repo13 = new Repository(prg13, "log13.txt");
            Controller ctrl13 = new Controller(repo13);
            menu.addCommand(new RunExampleCommand("13", ex12.toString(), ctrl13));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //SWITCH STATEMENT
        iStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(new SwitchStatement(
                                                                new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                                                                new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                new ValueExpression(new IntValue(10)),
                                                                new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                        ), new PrintStatement(new ValueExpression(new IntValue(300))))))))));
        try {
            ex14.typeCheck(new MyDictionary<>());
            ProgramState prg14 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex14);
            iRepository repo14 = new Repository(prg14, "log14.txt");
            Controller ctrl14 = new Controller(repo14);
            menu.addCommand(new RunExampleCommand("14", ex12.toString(), ctrl14));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //SLEEP STATEMENT
        iStatement ex15 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(3))),
                                new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new CompoundStatement(new SleepStatement(5), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))))));

        try {
            ex15.typeCheck(new MyDictionary<>());
            ProgramState prg15 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex15);
            iRepository repo15 = new Repository(prg15, "log15.txt");
            Controller ctrl15 = new Controller(repo15);
            menu.addCommand(new RunExampleCommand("15", ex12.toString(), ctrl15));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //REPEAT UNTIL STATEMENT
        iStatement ex16 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new RepeatUntilStatement(
                                new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))),
                                new RelationalExpression("==", new VariableExpression("v"), new ValueExpression(new IntValue(3)))
                        ),
                                new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                                        new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                                                new CompoundStatement(new VariableDeclarationStatement("z", new IntType()),
                                                        new CompoundStatement(new VariableDeclarationStatement("w", new IntType()),
                                                                new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                                                        new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(2))),
                                                                                new CompoundStatement(new AssignmentStatement("z", new ValueExpression(new IntValue(3))),
                                                                                        new CompoundStatement(new AssignmentStatement("w", new ValueExpression(new IntValue(4))),
                                                                                                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))))))))))));
        try {
            ex16.typeCheck(new MyDictionary<>());
            ProgramState prg16 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex16);
            iRepository repo16 = new Repository(prg16, "log16.txt");
            Controller ctrl16 = new Controller(repo16);
            menu.addCommand(new RunExampleCommand("16", ex12.toString(), ctrl16));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //CONDITIONAL ASSIGNMENT
        iStatement ex17 = new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                        new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new ConditionalAssignmentStatement("c",
                                        new VariableExpression("b"),
                                        new ValueExpression(new IntValue(100)),
                                        new ValueExpression(new IntValue(200))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                                                new CompoundStatement(new ConditionalAssignmentStatement("c",
                                                        new ValueExpression(new BoolValue(false)),
                                                        new ValueExpression(new IntValue(100)),
                                                        new ValueExpression(new IntValue(200))),
                                                        new PrintStatement(new VariableExpression("c"))))))));
        try {
            ex17.typeCheck(new MyDictionary<>());
            ProgramState prg17 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex17);
            iRepository repo17 = new Repository(prg17, "log17.txt");
            Controller ctrl17 = new Controller(repo17);
            menu.addCommand(new RunExampleCommand("17", ex12.toString(), ctrl17));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //FOR STATEMENT
        iStatement ex18 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)),
                                new ValueExpression(new IntValue(3)),
                                new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))))),
                                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

        try {
            ex18.typeCheck(new MyDictionary<>());
            ProgramState prg18 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex18);
            iRepository repo18 = new Repository(prg18, "log18.txt");
            Controller ctrl18 = new Controller(repo18);
            menu.addCommand(new RunExampleCommand("18", ex12.toString(), ctrl18));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        //DO WHILE STATEMENT
        iStatement ex19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        try {
            ex19.typeCheck(new MyDictionary<>());
            ProgramState prg19 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex19);
            iRepository repo19 = new Repository(prg19, "log19.txt");
            Controller ctrl19 = new Controller(repo19);
            menu.addCommand(new RunExampleCommand("19", ex12.toString(), ctrl19));
        } catch (InterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }

        menu.show();
    }
}
