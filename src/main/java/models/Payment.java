package models;

import java.sql.Timestamp;

public class Payment {

    private int paymentId;
    private String courseBought;
    private int pricePaid;
    private Timestamp timePaid;
    private String subject;
    private int subjectBoughtId; // New column

    public Payment() {
    }

    public Payment(int paymentId, String courseBought, int pricePaid, Timestamp timePaid, String subject, int subjectBoughtId) {
        this.paymentId = paymentId;
        this.courseBought = courseBought;
        this.pricePaid = pricePaid;
        this.timePaid = timePaid;
        this.subject = subject;
        this.subjectBoughtId = subjectBoughtId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getCourseBought() {
        return courseBought;
    }

    public void setCourseBought(String courseBought) {
        this.courseBought = courseBought;
    }

    public int getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(int pricePaid) {
        this.pricePaid = pricePaid;
    }

    public Timestamp getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(Timestamp timePaid) {
        this.timePaid = timePaid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSubjectBoughtId() {
        return subjectBoughtId;
    }

    public void setSubjectBoughtId(int subjectBoughtId) {
        this.subjectBoughtId = subjectBoughtId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", courseBought='" + courseBought + '\'' +
                ", pricePaid=" + pricePaid +
                ", timePaid=" + timePaid +
                ", subject='" + subject + '\'' +
                ", subjectBoughtId=" + subjectBoughtId +
                '}';
    }
}

