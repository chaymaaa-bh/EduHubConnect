package controllers;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.User;
import services.UserService;


import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button close;

    @FXML
    private TextField emailField;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField passwordField;

    private UserService userService;

    @FXML
    void close(ActionEvent event) {
        // Handle close action
    }


    @FXML
    void loginAdmin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                // Hash the entered password
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                // Compare the hashed password with the stored hash
                if (BCrypt.checkpw(password, user.getPassword())) {
                    if (user.getRole().equals("admin")) {
                        // If user is admin, navigate to dashboard.fxml
                        System.out.println("Admin login successful");
                        showAlert("Login Successful", "Welcome, Admin " + user.getFirst_name() + " " + "!");
                        GlobalVariables.userId = user.getUser_id();
                        GlobalVariables.userName = user.getFirst_name();
                        GlobalVariables.userEmail = user.getEmail();
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
                            emailField.getScene().setRoot(root);
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }  if (user.getRole().equals("scholar")) {
                        // If user is admin, navigate to dashboard.fxml
                        showAlert("Login Successful", "Welcome,  " + user.getFirst_name() + " " +  "!");
                        GlobalVariables.userId = user.getUser_id();
                        GlobalVariables.userName = user.getFirst_name();
                        GlobalVariables.userEmail = user.getEmail();
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/student.fxml"));
                            emailField.getScene().setRoot(root);
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                } else {
                    // Authentication failed, display error message
                    showAlert("Invalid Credentials", "The email or password is incorrect.");
                }
            } else {
                // User not found, display error message
                showAlert("User not found", "No user found with the provided email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during authentication. Please try again later.");
        }
    }




    @FXML
    void initialize() {
        assert close != null : "fx:id=\"close\" was not injected: check your FXML file 'Login.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert main_form != null : "fx:id=\"main_form\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Login.fxml'.";

        userService = new UserService();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML

    void createAccount(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/RegisterUser.fxml"));
            emailField.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void forgetpassword(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/forgetpassword.fxml"));
            emailField.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}