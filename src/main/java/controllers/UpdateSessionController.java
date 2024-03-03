package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Session;
import services.SessionService;

public class UpdateSessionController {

    private final SessionService sessionService = new SessionService();
    private Session selectedSession;

    @FXML
    private Button upButtonses;

    @FXML
    private TextField upnmses;

    @FXML
    private TextField updescSes;

    @FXML
    private TextField newsessionLinkId;


    public void setSession(Session session) {
        this.selectedSession = session;
        initializeFields();
    }

    private void initializeFields() {
        upnmses.setText(selectedSession.getSessionName());
        updescSes.setText(selectedSession.getSessionDescription());
        newsessionLinkId.setText(selectedSession.getSessionDriveLink());
    }

    @FXML
    void updateSession(ActionEvent event) {
        if (selectedSession != null) {
            String updatedName = upnmses.getText();
            String updatedDescription = updescSes.getText();
            String updatedLink = newsessionLinkId.getText();

            if (validateFields(updatedName, updatedDescription)) {
                selectedSession.setSessionName(updatedName);
                selectedSession.setSessionDescription(updatedDescription);
                selectedSession.setSessionDriveLink(updatedLink);

                try {
                    sessionService.update(selectedSession);
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
        upButtonses.getScene().getWindow().hide();
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
}