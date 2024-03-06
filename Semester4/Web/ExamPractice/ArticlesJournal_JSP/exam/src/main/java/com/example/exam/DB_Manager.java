package com.example.exam;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DB_Manager {
    private static final String connection_url = "jdbc:mysql://localhost:3306/exam_jsp";
    private static final String name = "lucas";
    private static final String password = "password";

    public DB_Manager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<Article> getArticles(String user) {
        List<Article> articles = new ArrayList<>();
        String statement = "select * from Article where user='" + user + "'";
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            while (rs.next())
                articles.add(new Article(
                        rs.getInt("id"),
                        rs.getInt("journal_id"),
                        rs.getString("user"),
                        rs.getString("summary"),
                        rs.getInt("date")
                ));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return articles;
    }

    public List<Article> getArticlesFromJournal(String user, String journal) {
        String statement = "select id from Journal where name='" + journal + "'";
        int journal_id = 0;
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            if (rs.next())
                journal_id = rs.getInt("id");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        List<Article> articles = new ArrayList<>();
        statement = String.format("select * from Article where journal_id=%d and user='%s'", journal_id, user);
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            while (rs.next())
                articles.add(new Article(
                        rs.getInt("id"),
                        rs.getInt("journal_id"),
                        rs.getString("user"),
                        rs.getString("summary"),
                        rs.getInt("date")
                ));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return articles;
    }
}
