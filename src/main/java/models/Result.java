package models;

import java.util.Date;

public class Result {

    private Integer result_user_id;
    private Integer result_quiz_id;
    private Integer result_score;
    private Date result_date;
    private String result_quiz_title;
    private String result_quiz_subject;

    public Result() {
    }

    public Result(int result_user_id, int result_quiz_id, int result_score, Date result_date, String result_quiz_title, String result_quiz_subject) {
        this.result_user_id = result_user_id;
        this.result_quiz_id = result_quiz_id;
        this.result_score = result_score;
        this.result_date = result_date;
        this.result_quiz_title = result_quiz_title;
        this.result_quiz_subject = result_quiz_subject;
    }

    public Integer getResult_user_id() {
        return result_user_id;
    }

    public void setResult_user_id(int result_user_id) {
        this.result_user_id = result_user_id;
    }

    public Integer getResult_quiz_id() {
        return result_quiz_id;
    }

    public void setResult_quiz_id(int result_quiz_id) {
        this.result_quiz_id = result_quiz_id;
    }

    public Integer getResult_score() {
        return result_score;
    }

    public void setResult_score(int result_score) {
        this.result_score = result_score;
    }

    public Date getResult_date() {
        return result_date;
    }

    public void setResult_date(Date result_date) {
        this.result_date = result_date;
    }

    public String getResult_quiz_title() {
        return result_quiz_title;
    }

    public void setResult_quiz_title(String result_quiz_title) {
        this.result_quiz_title = result_quiz_title;
    }

    public String getResult_quiz_subject() {
        return result_quiz_subject;
    }

    public void setResult_quiz_subject(String result_quiz_subject) {
        this.result_quiz_subject = result_quiz_subject;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result_user_id=" + result_user_id +
                ", result_quiz_id=" + result_quiz_id +
                ", result_score=" + result_score +
                ", result_date=" + result_date +
                ", result_quiz_title='" + result_quiz_title + '\'' +
                ", result_quiz_subject='" + result_quiz_subject + '\'' +
                '}';
    }
}
