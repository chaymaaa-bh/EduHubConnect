package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Session;
import models.Subject;
import services.SessionService;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddSessionController implements Initializable {

    private final SessionService sessionService = new SessionService();
    private int subjectId;

    @FXML
    private Button addButton;

    @FXML
    private TextField driverLinkID;

    @FXML
    private TextField nmses;

    @FXML
    private TextField descSes;


    private Subject selectedSubject;

    @FXML
    void addSession(ActionEvent event) {
        String sessionName = nmses.getText();
        String description = descSes.getText();
        String sessionDriveLink = driverLinkID.getText();

        if (!validateFields(sessionName, description, sessionDriveLink)) {
            showAlert("Failed", "All fields must be filled out");
            return;
        }

        if (isSessionNameUnique(sessionName)) {
                try {
                    Session session = new Session();
                    session.setSubjectId(subjectId);
                    session.setSessionName(sessionName);
                    session.setSessionDescription(description);
                    session.setSessionDriveLink(sessionDriveLink);

                    sessionService.create(session);

                    showAlert("Success", "Session added successfully");
                } catch (SQLException e) {
                    showAlert("Error", e.getMessage());
                }
        } else {
            showAlert("Failed", "Session name already exists");
        }
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setSubject(Subject selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    private boolean isSessionNameUnique(String sessionName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/3a2", "root", "");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM session WHERE sessionname = '" + sessionName + "'");

            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            statement.close();
            connection.close();

            return count == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
