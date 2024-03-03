package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Post{
    private boolean disliked;
    private final StringProperty content;
    private int IdP;
    private int likes;
    private int dislikes;


    // Default constructor
    public Post() {
        this.content = new SimpleStringProperty();
    }

    // Parameterized constructor
    public Post(int idP, String content, int likes, int dislikes) {
        this.IdP = idP; // Assigning postId to idP
        this.content = new SimpleStringProperty(content);
        this.likes = likes;
        this.dislikes = dislikes;
    }

    // Parameterized constructor without postId
    public Post(String content, int likes, int dislikes) {
        this.content = new SimpleStringProperty(content);
        this.likes = likes;
        this.dislikes = dislikes;
    }

    // Getters and setters
    public int getIdP() {
        return IdP;
    }

    public void setIdP(int idP) {
        this.IdP = idP;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    // Content property for JavaFX binding
    public StringProperty contentProperty() {
        return content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "idP=" + IdP +
                ", content='" + content.get() + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }

    public boolean isDisliked() {
        return disliked;
    }
}
