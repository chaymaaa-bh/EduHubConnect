package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Report;
import services.ReportService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateReportController {

    private final ReportService reportService = new ReportService();

    @FXML
    private ComboBox<String> modReportTypeId;

    @FXML
    private ComboBox<String> modReportedCourseId;

    @FXML
    private Button modReportButtonId;

    @FXML
    private TextArea modReportDescriptionId;

    private Stage stage;
    private Report selectedReport;

    @FXML
    void initialize() {
        List<String> reportTypes = List.of("Harassment", "Scam & Fraud", "Inappropriate Content", "Technical Issues", "Others");
        modReportTypeId.getItems().addAll(reportTypes);

        // Populate ComboBox for reported courses
        List<String> reportedCourses = getReportedCoursesFromDatabase(); // Implement this method
        modReportedCourseId.getItems().addAll(reportedCourses);
    }

    @FXML
    void updateReport(ActionEvent event) {
        if (selectedReport != null) {
            String updatedCourseReported = modReportedCourseId.getValue();
            String updatedReportDescription = modReportDescriptionId.getText();
            String updatedReportType = modReportTypeId.getValue();

            if (validateFields(updatedCourseReported, updatedReportDescription, updatedReportType)) {
                selectedReport.setReportedCourse(updatedCourseReported);
                selectedReport.setReportDescription(updatedReportDescription);
                selectedReport.setReportType(updatedReportType);

                try {
                    reportService.update(selectedReport);
                    closeWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Failed", "All fields are mandatory. Please fill in all the details.");
            }
        }
    }

    private List<String> getReportedCoursesFromDatabase() {
        List<String> coursesBought = new ArrayList<>();

        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/ahmed";
            String username = "root";
            String password = "";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT courseBought FROM payment");

                while (resultSet.next()) {
                    coursesBought.add(resultSet.getString("courseBought"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coursesBought;
    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setReport(Report report) {
        this.selectedReport = report;
        initializeFields();
    }

    private void initializeFields() {
        modReportDescriptionId.setText(selectedReport.getReportDescription());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
