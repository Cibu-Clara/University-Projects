package Domain;

public class Content {

    private int Id, user_id;
    private String date,title,description,url;

    public Content(int id, String date, String title, String description, String url, int user_id) {
        Id = id;
        this.user_id = user_id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.url = url;
    }
    public Content( String date, String title, String description, String url, int user_id) {
        this.user_id = user_id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return Id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
