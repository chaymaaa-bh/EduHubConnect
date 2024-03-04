package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Subject;
import services.SubjectService;

import java.io.IOException;

public class UpdateSubjectController {
    private final SubjectService subjectService = new SubjectService();

    private Subject selectedSubject;
    private Stage stage;
    @FXML
    private Button upButtoncourse;

    @FXML
    private TextField upnmcourse;

    @FXML
    private TextField updescourse;

    @FXML
    private TextField upprcourse;

    public void setSubject(Subject subject) {
        this.selectedSubject = subject;
        initializeFields();
    }

    private void initializeFields() {
        upnmcourse.setText(selectedSubject.getSubjectName());
        updescourse.setText(selectedSubject.getDescription());
        upprcourse.setText(selectedSubject.getSubjectPrice());
    }

    @FXML
    void updateSubject(ActionEvent event) {
        if (selectedSubject != null) {
            String updatedName = upnmcourse.getText();
            String updatedDescription = updescourse.getText();
            String updatedPrice = upprcourse.getText();

            if (validateFields(updatedName, updatedDescription, updatedPrice)) {
                selectedSubject.setSubjectName(updatedName);
                selectedSubject.setDescription(updatedDescription);
                selectedSubject.setSubjectPrice(updatedPrice);

                try {
                    subjectService.update(selectedSubject);
                    closeWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Failed", "All fields are mandatory. Please fill in all the details.");
            }
        }
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
    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student.fxml"));
            Parent root = loader.load();

            // Access the source node of the event
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}