package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import models.Report;
import services.ReportService;

import java.sql.*;
import java.time.LocalDateTime;

public class AddReportController {

    private final ReportService rs = new ReportService();

    @FXML
    private Button reportButtonId;

    @FXML
    private TextArea reportDescriptionId;

    @FXML
    private ComboBox<String> reportTypeId;

    @FXML
    private ComboBox<String> reportedCourseId;

    @FXML
    public void initialize() {
        ObservableList<String> reportTypes = FXCollections.observableArrayList(
                "Harassment", "Scam & Fraud", "Inappropriate Content", "Technical Issues", "Others"
        );
        reportTypeId.setItems(reportTypes);

        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/ahmed";
            String username = "root";
            String password = "";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT courseBought FROM payment");
                ObservableList<String> coursesBought = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    coursesBought.add(resultSet.getString("courseBought"));
                }

                resultSet.close();
                statement.close();
                connection.close();

                reportedCourseId.setItems(coursesBought);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addReport(javafx.event.ActionEvent event) {
        try {
            String reportDescription = reportDescriptionId.getText();
            String reportType = reportTypeId.getValue();
            String reportedCourse = reportedCourseId.getValue();

            if (isValidInput(reportDescription, reportType, reportedCourse)) {

                int reporterUser = 1;

                Timestamp reportDate = Timestamp.valueOf(LocalDateTime.now());

                Report newReport = new Report(reportedCourse, reporterUser, reportDate, reportType, reportDescription);

                rs.create(newReport);

                showAlert(AlertType.INFORMATION, "Success", "Report Added Successfully", "Report added successfully!");


            } else {
                showAlert(AlertType.ERROR, "Error", "Invalid Input", "Please fill in all the required fields.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Error Adding Report", "An error occurred while adding the report.");
            e.printStackTrace();
        }
    }

    private boolean isValidInput(String reportDescription, String reportType, String reportedCourse) {
        return reportDescription != null && !reportDescription.isEmpty() &&
                reportType != null && !reportType.isEmpty() &&
                reportedCourse != null && !reportedCourse.isEmpty();
    }

    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
