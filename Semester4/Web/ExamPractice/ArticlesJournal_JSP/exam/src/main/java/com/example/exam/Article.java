package com.example.exam;

public class Article {
    private final int id;
    private final int journal_id;
    private final String user;
    private final String summary;
    private final int date;

    public Article(int id, int journalId, String user, String summary, int date) {
        this.id = id;
        this.user = user;
        journal_id = journalId;
        this.summary = summary;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getJournal_id() {
        return journal_id;
    }

    public String getSummary() {
        return summary;
    }

    public int getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", journal_id=" + journal_id +
                ", summary='" + summary + '\'' +
                ", date=" + date +
                '}';
    }
}
