package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() { this.commands = new HashMap<>(); }

    public void addCommand(Command cmd) { this.commands.put(cmd.getKey(), cmd); }

    private void printMenu() {
        for (Command cmd : commands.values()){
            String line = String.format("%4s : %s", cmd.getKey(), cmd.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command cmd = commands.get(key);
            if (cmd == null) {
                System.out.println("Invalid option!\n");
                continue;
            }
            cmd.execute();
        }
    }
}

