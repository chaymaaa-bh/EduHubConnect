package models;

public class Quiz {

    private int quiz_id, quiz_duration;
    private String quiz_title, quiz_description, quiz_subject;

    public Quiz() {
    }

    public Quiz(int quiz_id, int quiz_duration, String quiz_title, String quiz_description, String quiz_subject) {
        this.quiz_id = quiz_id;
        this.quiz_duration = quiz_duration;
        this.quiz_title = quiz_title;
        this.quiz_description = quiz_description;
        this.quiz_subject = quiz_subject;
    }

    public Quiz(int quiz_duration, String quiz_title, String quiz_description, String quiz_subject) {
        this.quiz_duration = quiz_duration;
        this.quiz_title = quiz_title;
        this.quiz_description = quiz_description;
        this.quiz_subject = quiz_subject;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getQuiz_duration() {
        return quiz_duration;
    }

    public void setQuiz_duration(int quiz_duration) {
        this.quiz_duration = quiz_duration;
    }

    public String getQuiz_title() {
        return quiz_title;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }

    public String getQuiz_description() {
        return quiz_description;
    }

    public void setQuiz_description(String quiz_description) {
        this.quiz_description = quiz_description;
    }

    public String getQuiz_subject() {
        return quiz_subject;
    }

    public void setQuiz_subject(String quiz_subject) {
        this.quiz_subject = quiz_subject;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quiz_id=" + quiz_id +
                ", quiz_duration=" + quiz_duration +
                ", quiz_title='" + quiz_title + '\'' +
                ", quiz_description='" + quiz_description + '\'' +
                ", quiz_subject='" + quiz_subject + '\'' +
                '}';
    }
}
