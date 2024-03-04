package models;

import java.sql.Timestamp;

public class Rating {
    private int ratingId, ratingValue, ratedCourse, ratingUser;
    private Timestamp  ratingDate;

    public Rating() {
    }

    public Rating(int ratingId, int ratingValue, int ratedCourse, int ratingUser, Timestamp ratingDate) {
        this.ratingId = ratingId;
        this.ratingValue = ratingValue;
        this.ratedCourse = ratedCourse;
        this.ratingUser = ratingUser;
        this.ratingDate = ratingDate;
    }

    public Rating(int ratingValue, int ratedCourse, int ratingUser, Timestamp ratingDate) {
        this.ratingValue = ratingValue;
        this.ratedCourse = ratedCourse;
        this.ratingUser = ratingUser;
        this.ratingDate = ratingDate;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getRatedCourse() {
        return ratedCourse;
    }

    public void setRatedCourse(int ratedCourse) {
        this.ratedCourse = ratedCourse;
    }

    public Timestamp getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Timestamp ratingDate) {
        this.ratingDate = ratingDate;
    }

    public int getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(int ratingUser) {
        this.ratingUser = ratingUser;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", ratingValue=" + ratingValue +
                ", ratedCourse=" + ratedCourse +
                ", ratingDate=" + ratingDate +
                ", ratingUser=" + ratingUser +
                '}';
    }
}