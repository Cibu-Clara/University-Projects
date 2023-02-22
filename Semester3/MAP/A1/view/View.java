package view;
import controller.Controller;
import model.Apples;
import model.Pears;
import model.Cherries;
import model.iTrees;
import repository.Repository;
import repository.iRepository;

import java.util.Scanner;

public class View {
    public static void start() {
        iRepository repository = new Repository(10);
        Controller controller = new Controller(repository);
        Scanner in = new Scanner(System.in);
        iTrees t1 = new Apples(10);
        iTrees t2 = new Pears(7);
        iTrees t3 = new Cherries(20);
        iTrees t4 = new Cherries(2);
        iTrees t5 = new Apples(5);

        try {
            controller.add(t1);
            controller.add(t2);
            controller.add(t3);
            controller.add(t4);
            controller.add(t5);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        while(true) {
            System.out.println("\n1. List all trees\n2. Add new tree\n3. Remove a tree\n4. Display all trees older than 3 years\n0. Exit\n");
            int input = in.nextInt();
            if(input == 0)
                return;
            if(input == 1)
            {
                controller.printAll();
            }
            else if(input == 2)
            {
                Scanner in1 = new Scanner(System.in);
                Scanner in2 = new Scanner(System.in);
                System.out.println("\nAge:");
                int age = in1.nextInt();
                System.out.println("Type (apple/cherry/pear):");
                String type = in2.nextLine();

                try {
                    switch (type) {
                        case "apple" -> {
                            iTrees apple = new Apples(age);
                            controller.add(apple);
                        }
                        case "cherry" -> {
                            iTrees cherry = new Cherries(age);
                            controller.add(cherry);
                        }
                        case "pear" -> {
                            iTrees pear = new Pears(age);
                            controller.add(pear);
                        }
                        default -> System.out.println("Error: incorrect type");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
            else if(input == 3)
            {
                System.out.println("\nType the index of the tree you want to remove: ");
                Scanner in3 = new Scanner(System.in);
                int index = in3.nextInt();
                try {
                    controller.remove(index);
                }catch (Exception e){
                    System.out.println("Error: "+ e.getMessage());
                }
            }
            else if(input == 4)
            {
                // view
                controller.printSolution(3);
            }

        }

    }
}
