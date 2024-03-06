package Domain;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Db_manager {

    private static final String connection_url = "jdbc:mysql://localhost:3306/jsp_exam_test_db";
    private static final String name = "SICOE";
    private static final String password = "sicoe";

    public Db_manager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public User Authentication(String user, String password){
        String statement = "select * from users where user ='" + user +"' and password = '" + password +"'";
        User userr = null;
        try {
            var connection = DriverManager.getConnection(connection_url, name, password);
            var preparedStatement = connection.prepareStatement(statement);
            var rs = preparedStatement.executeQuery();
            if (rs.next())
                userr = new User(rs.getInt("Id"), rs.getString("user"), rs.getString("password"), rs.getInt("role"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return userr;
    }
    public int auto(String user, String password){
        String statement = "select * from users where user ='" + user +"' and password = '" + password +"'";
        User userr = null;
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            if (rs.next()){
                userr = new User(rs.getInt("Id"), rs.getString("user"), rs.getString("password"), rs.getInt("role"));
                return 1;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return 0;
    }

    public User getUserFromName(String username) {
        User user = null;
        String statement = "select * from users where user='" + username + "';";
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            if (rs.next())
                user = new User(rs.getInt("Id"), rs.getString("user"), rs.getString("password"), rs.getInt("role"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return user;
    }
    public Content getContentFromId(int id) {
        Content content = null;
        String statement = "select * from content where Id='" + id + "';";
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            if (rs.next())
                content = new Content(rs.getInt("Id"), rs.getString("date"), rs.getString("title"), rs.getString("description"), rs.getString("url"), rs.getInt("user_id"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return content;
    }

    public void removeContent(int id) {
        String statement = "delete from content where Id = '" + id + "'";
        try {
            var connection = DriverManager.getConnection(connection_url, name, password);
            var preparedStatement = connection.prepareStatement(statement);
            var rs = preparedStatement.executeUpdate();

            } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public int insertContent(Content content) {

        int status = -1;
        String statement = "insert into content (date,title,description,url,user_id) values('"+ content.getDate() +"','"+ content.getTitle() +"','"+ content.getDescription() +"','"+ content.getUrl() +"','"+ content.getUser_id() +"' )";
        try {
            var connection = DriverManager.getConnection(connection_url, name, password);
            var preparedStatement = connection.prepareStatement(statement);
            var rs = preparedStatement.executeUpdate();
            return 1;
            } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
        }
    }

    public int updateContent(Content c){
        int status=0;
        try{
            String statement = "update content set date='"+ c.getDate() +"',title='" +c.getTitle()+ "',description='" +c.getDescription()+ "',url= '"+ c.getUrl()+ "' where Id='"+c.getId()+"'";
            var connection = DriverManager.getConnection(connection_url, name, password);
            var preparedStatement = connection.prepareStatement(statement);

            status = preparedStatement.executeUpdate();
        }catch(Exception ex){ex.printStackTrace();}

        return status;
    }
    public List<Content> getAllContents() {
        List<Content> contents = new ArrayList<>();
        String statement = "select * from content";
        try (var connection = DriverManager.getConnection(connection_url, name, password);
             var preparedStatement = connection.prepareStatement(statement);
             var rs = preparedStatement.executeQuery()) {
            while (rs.next())
                contents.add(new Content(rs.getInt("Id"), rs.getString("date"), rs.getString("title"), rs.getString("description"), rs.getString("url"), rs.getInt("user_id")));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return contents;
    }
}
