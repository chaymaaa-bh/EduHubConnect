package models;

public class Session {

    private int sessionId;
    private String sessionName;
    private String sessionDescription;
    private int subjectId;
    private String sessionSubjectName;

    private String sessionDriveLink;

    public Session() {
    }

    public Session(int sessionId, String sessionName, String sessionDescription, int subjectId, String sessionDriveLink) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionDescription = sessionDescription;
        this.subjectId = subjectId;
        this.sessionDriveLink = sessionDriveLink;
    }

    public Session(String sessionName, String sessionDescription, int subjectId, String sessionDriveLink) {
        this.sessionName = sessionName;
        this.sessionDescription = sessionDescription;
        this.subjectId = subjectId;
        this.sessionDriveLink = sessionDriveLink;
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

    public String getSessionDriveLink() {
        return sessionDriveLink;
    }

    public void setSessionDriveLink(String sessionDriveLink) {
        this.sessionDriveLink = sessionDriveLink;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", sessionName='" + sessionName + '\'' +
                ", sessionDescription='" + sessionDescription + '\'' +
                ", subjectId=" + subjectId +
                ", sessionSubjectName='" + sessionSubjectName + '\'' +
                ", sessionDriveLink='" + sessionDriveLink + '\'' +
                '}';
    }
}
