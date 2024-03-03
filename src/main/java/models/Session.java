package models;

public class Session {

    private int sessionId;
    private String sessionName;
    private String sessionDescription;
    private int subjectId;
    private String sessionSubjectName;

    public Session() {
    }

    public Session(int sessionId, String sessionName, String sessionDescription, int subjectId) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionDescription = sessionDescription;
        this.subjectId = subjectId;
    }

    public Session(String sessionName, String sessionDescription, int subjectId) {
        this.sessionName = sessionName;
        this.sessionDescription = sessionDescription;
        this.subjectId = subjectId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    public String getSessionSubjectName() {
        return sessionSubjectName;
    }

    public void setSessionSubjectName(String sessionSubjectName) {
        this.sessionSubjectName = sessionSubjectName;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", sessionName='" + sessionName + '\'' +
                ", sessionDescription='" + sessionDescription + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }
}
