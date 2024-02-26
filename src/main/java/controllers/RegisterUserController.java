package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import models.User;
import services.UserService;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterUserController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> genderField; // Update to ComboBox

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextField moduleIDField;

    @FXML
    private TextField bioField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField profilePictureField;

    @FXML
    private Label statusLabel;

    private UserService userService;

    public RegisterUserController() {
        userService = new UserService();
    }

    @FXML
    private void handleRegisterUser() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String gender = genderField.getValue(); // Get selected gender from ComboBox
        LocalDate dateOfBirth = dateOfBirthPicker.getValue();
        String moduleIDText = moduleIDField.getText().trim();
        String bio = bioField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String profilePicture = profilePictureField.getText().trim();

        // Input Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || gender == null || dateOfBirth == null || moduleIDText.isEmpty()) {
            statusLabel.setText("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match");
            return;
        }

        if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
            statusLabel.setText("Please select a valid gender");
            return;
        }

        // Validating Email format
        if (!email.matches("^[\\w.-]+@[a-zA-Z]+\\.[a-zA-Z]{2,3}$")) {
            statusLabel.setText("Please enter a valid email address");
            return;
        }

        // Validating First Name and Last Name (no numbers and spaces)
        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            statusLabel.setText("First name and last name must contain only letters");
            return;
        }

        // Validating Phone Number format (exactly 8 digits)
        if (!phoneNumber.matches("\\d{8}")) {
            statusLabel.setText("Please enter a valid 8-digit phone number");
            return;
        }

        int moduleID;
        try {
            moduleID = Integer.parseInt(moduleIDText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Module ID must be a number");
            return;
        }

        // Creating User
        User user = new User(moduleID, firstName, lastName, email, password, gender, "scholar", bio, phoneNumber, profilePicture, dateOfBirth);

        // Persisting User
        try {
            userService.create(user);
            statusLabel.setText("User registered successfully");
        } catch (SQLException e) {
            statusLabel.setText("Error registering user: " + e.getMessage());
        }
    }



    @FXML
    private void choosePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            profilePictureField.setText(selectedFile.getAbsolutePath());
        }
    }


}
