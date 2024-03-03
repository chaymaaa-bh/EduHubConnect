package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Comment {
    private final StringProperty c_title;
    private final StringProperty content;
    private final StringProperty date;
    private final int idC;

    // Default constructor
    public Comment() {
        this.c_title = new SimpleStringProperty();
        this.content = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.idC = 0;
    }

    // Parameterized constructor with three strings
    public Comment(String c_title, String content, String date) {
        this.c_title = new SimpleStringProperty(c_title);
        this.content = new SimpleStringProperty(content);
        this.date = new SimpleStringProperty(date);
        this.idC = 0; // You may need to set a default value for idC
    }

    // Parameterized constructor
    public Comment(String c_title, String content, String date, int idC) {
        this.c_title = new SimpleStringProperty(c_title);
        this.content = new SimpleStringProperty(content);
        this.date = new SimpleStringProperty(date);
        this.idC = idC;
    }



    // Getter methods
    public String getC_title() {
        return c_title.get();
    }

    public String getContent() {
        return content.get();
    }

    public String getDate() {
        return date.get();
    }

    public int getIdC() {
        return idC;
    }

    // Setter methods
    public void setContent(String content) {
        this.content.set(content);
    }

    // Property methods
    public StringProperty c_titleProperty() {
        return c_title;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public StringProperty dateProperty() {
        return date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "c_title=" + c_title.get() +
                ", content=" + content.get() +
                ", date=" + date.get() +
                ", idC=" + idC +
                '}';
    }
}
