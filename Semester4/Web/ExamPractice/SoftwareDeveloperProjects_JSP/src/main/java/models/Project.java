package models;

public class Project {
    private int id;
    private String name;
    private String description;
    private String members;
    private int project_manager_id;

    public Project() {}

    public Project(String name, String description, String members, int projectManagerID) {
        this.name = name;
        this.description = description;
        this.members = members;
        this.project_manager_id = projectManagerID;
    }

    public Project(int id, String name, String description, String members, int projectManagerID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = members;
        this.project_manager_id = projectManagerID;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMembers() {
        return members;
    }
    public void setMembers(String members) {
        this.members = members;
    }
    public int getProject_manager_id() {
        return project_manager_id;
    }
    public void setProject_manager_id(int project_manager_id) {
        this.project_manager_id = project_manager_id;
    }
}
