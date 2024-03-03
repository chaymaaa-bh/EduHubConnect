package models;

public class Subject {

    private int subjectId;
    private String subjectName;
    private String subjectPrice;
    private String description;
    private String subjectTopic;

    public Subject(String text, String descSubText, int i) {
    }

    public Subject(){

    }

    public Subject(int subjectId, String subjectName, String subjectPrice, String description, String subjectTopic) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectPrice = subjectPrice;
        this.description = description;
        this.subjectTopic=subjectTopic;
    }

    public Subject(String subjectName, String subjectPrice, String description, String subjectTopic) {
        this.subjectName = subjectName;
        this.subjectPrice = subjectPrice;
        this.description = description;
        this.subjectTopic=subjectTopic;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectPrice() {
        return subjectPrice;
    }

    public void setSubjectPrice(String subjectPrice) {
        this.subjectPrice = subjectPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectTopic() {return subjectTopic;}

    public void setSubjectTopic(String subjectTopic){this.subjectTopic=subjectTopic; }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectPrice='" + subjectPrice + '\'' +
                ", description='" + description + '\'' +
                ", subjectTopic='"+ subjectTopic + '\''+
                '}';
    }
}
