package models;

public class SoftwareDeveloper {
    private int id;
    private String name;
    private int age;
    private String skills;

    public SoftwareDeveloper() {}

    public SoftwareDeveloper(String name, int age, String skills) {
        this.name = name;
        this.age = age;
        this.skills = skills;
    }

    public SoftwareDeveloper(int id, String name, int age, String skills) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
}
