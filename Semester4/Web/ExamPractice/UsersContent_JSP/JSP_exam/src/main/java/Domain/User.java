package Domain;

public class User {
    private int id;
    private final String user;
    private final String password;
    private int role;

    public User(int id, String name, String password, int role) {
        this.id = id;
        this.user = name;
        this.password = password;
        this.role = role;
    }
    public User( String name, String password, int role) {

        this.user = name;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + user + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
