package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Report {
    private int reportId, reporterUser;
    private ObjectProperty<Timestamp> reportDate;
    private StringProperty reportType, reportDescription, reportedCourse;

    public Report() {
        this.reportDate = new SimpleObjectProperty<>();
        this.reportType = new SimpleStringProperty();
        this.reportDescription = new SimpleStringProperty();
        this.reportedCourse = new SimpleStringProperty();
    }

    public Report(String reportedCourse, int reporterUser, Timestamp reportDate, String reportType, String reportDescription) {
        this();
        this.reportedCourse.set(reportedCourse);
        this.reporterUser = reporterUser;
        this.reportDate.set(reportDate);
        this.reportType.set(reportType);
        this.reportDescription.set(reportDescription);
    }

    public Report(int reportId, String reportedCourse, int reporterUser, Timestamp reportDate, String reportType, String reportDescription) {
        this(reportedCourse, reporterUser, reportDate, reportType, reportDescription);
        this.reportId = reportId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportedCourse() {
        return reportedCourse.get();
    }

    public void setReportedCourse(String reportedCourse) {
        this.reportedCourse.set(reportedCourse);
    }

    public int getReporterUser() {
        return reporterUser;
    }

    public void setReporterUser(int reporterUser) {
        this.reporterUser = reporterUser;
    }

    public Timestamp getReportDate() {
        return reportDate.get();
    }

    public void setReportDate(Timestamp reportDate) {
        this.reportDate.set(reportDate);
    }

    public String getReportType() {
        return reportType.get();
    }

    public void setReportType(String reportType) {
        this.reportType.set(reportType);
    }

    public String getReportDescription() {
        return reportDescription.get();
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription.set(reportDescription);
    }

    public StringProperty reportedCourseProperty() {
        return reportedCourse;
    }

    public ObjectProperty<Timestamp> reportDateProperty() {
        return reportDate;
    }

    public StringProperty reportTypeProperty() {
        return reportType;
    }

    public StringProperty reportDescriptionProperty() {
        return reportDescription;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reportedCourse=" + reportedCourse +
                ", reporterUser=" + reporterUser +
                ", reportDate=" + reportDate +
                ", reportType='" + reportType + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                '}';
    }
}
