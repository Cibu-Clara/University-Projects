package utils;
import models.Project;
import com.mysql.jdbc.Driver;
import models.SoftwareDeveloper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionManager {
    private static final String url = "jdbc:mysql://localhost:3306/ProjectManagement";
    private static final String username = "root";
    private static final String password = "root";

    public DBConnectionManager() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String stmt = "SELECT * FROM Project";
        try (Connection conn = DriverManager.getConnection(url, username, password);
            var preparedStmt = conn.prepareStatement(stmt);
            var rs = preparedStmt.executeQuery()) {
            while (rs.next())
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("members"),
                        rs.getInt("project_manager_id")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public List<String> getUserProjects(String name) {
        List<String> projects = new ArrayList<>();
        String stmt = "SELECT * FROM Project WHERE members LIKE '%" + name + "%'";
        try (Connection conn = DriverManager.getConnection(url, username, password);
            var preparedStmt = conn.prepareStatement(stmt);
            var rs = preparedStmt.executeQuery()) {
            while (rs.next())
                projects.add(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public List<SoftwareDeveloper> getAllDevelopers() {
        List<SoftwareDeveloper> developers = new ArrayList<>();
        String stmt = "SELECT * FROM SoftwareDeveloper";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             var preparedStmt = conn.prepareStatement(stmt);
             var rs = preparedStmt.executeQuery()) {
            while (rs.next())
                developers.add(new SoftwareDeveloper(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("skills")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    public List<SoftwareDeveloper> getDevelopersBySkill(String skill) {
        List<SoftwareDeveloper> filteredDevelopers = new ArrayList<>();
        String stmt = "SELECT * FROM SoftwareDeveloper WHERE skills LIKE ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStmt = conn.prepareStatement(stmt)) {
            preparedStmt.setString(1, "%" + skill + "%");
            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    filteredDevelopers.add(new SoftwareDeveloper(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("skills")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredDevelopers;
    }

}
