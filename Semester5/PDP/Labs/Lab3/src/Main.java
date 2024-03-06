import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Calculator implements Runnable {
    private final int[][] x;
    private final int x_lines;
    private final int x_columns;
    private final int[][] y;
    private final int y_lines;
    private final int y_columns;
    private final int[][] result;
    private int start_column;
    private int start_line;
    private final int nr_elem;
    private final int type;
    private final int k;
    private final int taskId;
    public Calculator(int taskId, int[][] x, int[][] y, int x_lines, int x_columns, int y_lines, int y_columns, int[][] result, int start_line, int start_column, int nr_elem, int type )
    {
        this.taskId=taskId;
        this.x= x;
        this.y= y;
        this.x_lines = x_lines;
        this.x_columns = x_columns;
        this.y_columns=y_columns;
        this.y_lines=y_lines;
        this.start_column=start_column;
        this.start_line = start_line;
        this.nr_elem = nr_elem;
        this.result = result;
        this.type=type;
        this.k = 4; // set how many threads you want
    }

    public int CalculateOneElement(int x_line_position, int x_column_position) {
        int value = 0;
        for (int i = 0; i < x_columns; i++) {
            value += x[x_line_position][i] * y[i][x_column_position];
        }
        return value;
    }

    public int CalculateOneElementPerColumn(int x_line_position, int x_column_position) {
        int value = 0;
        for (int i = 0; i < x_lines; i++) {
            value += x[i][x_column_position] * y[x_line_position][i];
        }
        return value;
    }

    public void CalculateProductPerColumn() { // first column in matrix 1 with first line in matrix 2
        int counter = 0;
        while (counter < nr_elem) {
            result[start_line][start_column] = CalculateOneElementPerColumn(start_line, start_column);

            start_line++;

            if (start_line == y_lines) {
                start_line = 0;
                start_column++;

                if (start_column == x_columns) {
                    break;
                }
            }
            counter++;
        }
    }
    public void CalculateProductPerLine() {
        int counter = 0;
        while (counter < nr_elem) {
            result[start_line][start_column] = CalculateOneElement(start_line, start_column);
            start_column++;

            if (start_column == y_columns) {
                start_column = 0;
                start_line++;

                if (start_line == x_lines) {
                    break;
                }
            }
            counter++;
        }
    }

    public void CalculateProductKth() {
        int counter= 0;
        for (int row = start_line; row < x_lines; row++) {
            for (int col = start_column; col < y_columns; col++) {
                if (counter == k || row==taskId)  {
                    result[row][col] = CalculateOneElement(row, col);
                    counter =0;
                }
                counter ++;
            }
        }
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        if(this.type == 0 )
            CalculateProductPerLine();
        else if(this.type == 1)
            CalculateProductPerColumn();
        else CalculateProductKth();

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }
}

class Choice {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final String type;

    public Choice(String type) {
        this.type = type;
        this.matrixA = generateMatrix();
        this.matrixB = generateMatrix();
    }

    private int[][] generateMatrix() {
        int[][] matrix = new int[90][90];
        Random random = new Random();

        for (int i = 0; i < 90; i++) {
            for (int j = 0; j < 90; j++) {
                matrix[i][j] = 3; // Adjust the range of random values as needed
            }
        }
        return matrix;
    }

    public void  run(){
        long startTime = System.currentTimeMillis();
        int tasks = 4;
        int matrixSize = 90;

        if(Objects.equals(this.type, "normal")){
            int[][] result = new int[90][90];

            Thread[] threads = new Thread[tasks];

            for (int i = 0; i < tasks; i++) {
                threads[i] = new Thread(new Calculator(i, matrixA, matrixB, matrixSize, matrixSize, matrixSize, matrixSize, result, i * 20, i*20, 2025, 0));
                threads[i].start();
            }

            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Product calculated per line");
            printMatrix(result);
            System.out.println();

            int[][] resultColumn = new int[matrixSize][matrixSize];

            threads = new Thread[tasks];

            for (int i = 0; i < tasks; i++) {
                threads[i] = new Thread(new Calculator(i, matrixA, matrixB, matrixSize, matrixSize, matrixSize, matrixSize, resultColumn, i*20, i * 20, 2025, 1));
                threads[i].start();
            }

            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Product calculated per column");
            printMatrix(resultColumn);
            System.out.println();

            int[][] resultk= new int[90][90];

            threads = new Thread[tasks];

            for (int i = 0; i < tasks; i++) {
                threads[i] = new Thread(new Calculator(i, matrixA, matrixB, matrixSize, matrixSize, matrixSize, matrixSize, resultColumn, 0, 0, 0, 2));
                threads[i].start();
            }

            System.out.println("Product calculated by k-th");
            printMatrix(resultk);
            System.out.println();
        }
        else if (Objects.equals(type, "thread pool")){
            int[][] resultLine = new int[90][90];
            int[][] resultColumn = new int[90][90];
            int[][] resultK = new int[90][90];

            ExecutorService executorService = Executors.newFixedThreadPool(4);
            ExecutorService executorService1= Executors.newFixedThreadPool(4);
            ExecutorService executorService2= Executors.newFixedThreadPool(4);

            int elementsPerTask = 2025;

            for (int i = 0; i < tasks; i++) {
                Runnable task = new Calculator(i,matrixA, matrixB, 90, 90, 90, 90, resultLine,i * 20, i * 20, elementsPerTask, 0);
                executorService.execute(task);
            }

            executorService.shutdown();

            try {
                executorService.awaitTermination(1000, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < tasks; i++) {
                Runnable task = new Calculator(i,matrixA, matrixB, 90, 90, 90, 90, resultColumn,i * 20, i * 20, elementsPerTask, 1);
                executorService1.execute(task);
            }

            executorService1.shutdown();

            try {
                executorService1.awaitTermination(1000, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < tasks; i++) {
                Runnable task = new Calculator(i,matrixA, matrixB, 90, 90, 90, 90, resultK,0, 0, 0,2);
                executorService2.execute(task);
            }

            executorService2.shutdown();

            try {
                executorService2.awaitTermination(1000, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Product calculated per line");
            for(int i=0;i < 90;i++) {
                for (int j = 0; j < 90; j++) {
                    System.out.print(resultLine[i][j]+" ");
                }
                System.out.println();
            }

            System.out.println();

            System.out.println("Product calculated per column");
            for(int i=0;i < 90;i++) {
                for (int j = 0; j < 90; j++) {
                    System.out.print(resultColumn[i][j]+" ");
                }
                System.out.println();
            }

            System.out.println();

            System.out.println("Product calculated per k-th");
            for(int i=0;i < 90;i++) {
                for (int j = 0; j < 90; j++) {
                    System.out.print(resultK[i][j]+" ");
                }
                System.out.println();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
    }

    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter how you want the solution to be solved(normal/thread pool): ");
        String userInput = scanner.nextLine();
        scanner.close();

        long startTime = System.currentTimeMillis();

        if(userInput.equals("normal")){
            Choice choice = new Choice("normal");
            choice.run();
        }
        else if (userInput.equals("thread pool")){
            Choice choice = new Choice("thread pool");
            choice.run();
        }
        else
        {
            System.out.println("Not a valid option!");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total program execution time: " + (endTime - startTime) + " milliseconds");
    }
}