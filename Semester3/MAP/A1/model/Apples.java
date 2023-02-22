package model;

public class Apples implements iTrees{
     int age;

    public Apples(int age) { this.age = age;}

    public String toString() {
        return "Apple, age = " + this.age + "\n";
    }

    public Boolean problemSolver(int age) {
        return this.age > age;
    }
}
