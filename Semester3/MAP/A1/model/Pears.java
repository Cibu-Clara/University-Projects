package model;

public class Pears implements iTrees {
     int age;

    public Pears(int age) { this.age = age;}

    public String toString() {
        return "Pear, age = " + this.age + "\n";
    }

    public Boolean problemSolver(int age) {
        return this.age > age;
    }
}
