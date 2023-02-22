package model;

public class Cherries implements iTrees {
     int age;

    public Cherries(int age) { this.age = age;}

    public String toString() {
        return "Cherry, age = " + this.age + "\n";
    }

    public Boolean problemSolver(int age) {
        return this.age > age;
    }
}
