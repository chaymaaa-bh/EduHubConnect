package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UpdatePasswordController {

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmpasswordField;

    @FXML
    private Button submitupdatetn;

    private String userEmail; // Add a variable to store the user's email

    private MyDatabase myDatabase;

    @FXML
    private void initialize() {
        myDatabase = MyDatabase.getInstance();
    }

    @FXML
    private void submitUpdate() {
        System.out.println(userEmail);
        // Handle submit action
        String password = passwordField.getText();
        String confirmPassword = confirmpasswordField.getText();

        // Ensure passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Update password in the database
        try (Connection conn = myDatabase.getConnection()) {
            String sql = "UPDATE users SET password = ? WHERE email = ?"; // Assuming email is the unique identifier
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters
                statement.setString(1, hashedPassword);
                statement.setString(2, userEmail);

                // Execute the update
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully!");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));

                        passwordField.getScene().setRoot(root);
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Failed to update password!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
