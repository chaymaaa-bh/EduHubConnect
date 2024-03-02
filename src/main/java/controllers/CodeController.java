package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeController {

    @FXML
    private TextField codeTextField;

    private String expectedCode;
    private String userEmail; // Add a variable to store the user's email


    public void setExpectedCode(String code) {
        this.expectedCode = code;
    }
    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public void submitCode() {
        String enteredCode = codeTextField.getText().trim();

        if (enteredCode.equals(expectedCode)) {
            // Code is valid
            showAlert(Alert.AlertType.INFORMATION, "Success", "Code accepted!");

            try {
                // Load the UpdatePassword.fxml scene and pass the user's email
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatepassword.fxml"));
                Parent root = loader.load();
                UpdatePasswordController updatePasswordController = loader.getController();
                updatePasswordController.setUserEmail(userEmail);
                codeTextField.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            }
        else {
            // Invalid code
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid code. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forgetpassword.fxml"));
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
