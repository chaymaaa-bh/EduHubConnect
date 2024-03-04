package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Session;
import models.Subject;
import services.SessionService;

import java.io.IOException;
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "");

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

    public void close()
    {
        System.exit(0);
    }


    public void minimize() {
        Stage stage = (Stage)this.nmses.getScene().getWindow();
        stage.setIconified(true);
    }

    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddSubject.fxml"));
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
}
