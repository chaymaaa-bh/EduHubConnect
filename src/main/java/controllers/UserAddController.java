package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.User;
import services.UserService;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserAddController {

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
    private ComboBox<String> genderField;

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
    private ComboBox<String> roleField;


    @FXML
    private Button choosePictureBtn;

    @FXML
    private Button registerUserBtn;

    @FXML
    private Label statusLabel;

    private UserService userService;

    public UserAddController() {
        userService = new UserService();
    }

    @FXML
    public void initialize() {
        genderField.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    @FXML
    public void choosePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            profilePictureField.setText(selectedFile.getPath());
        }
    }

    @FXML
    public void handleRegisterUser(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleField.getValue();
        String gender = genderField.getValue();
        LocalDate dateOfBirth = dateOfBirthPicker.getValue();

        // Parse module ID as an integer
        int moduleID = 0;
        try {
            moduleID = Integer.parseInt(moduleIDField.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("Module ID must be an integer");
            return;
        }

        String bio = bioField.getText();
        String phoneNumber = phoneNumberField.getText();
        String profilePicture = profilePictureField.getText();

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match");
            return;
        }

        User newUser = new User(moduleID,firstName, lastName, email, password, gender, role, bio, phoneNumber, profilePicture,dateOfBirth);

        try {
            userService.create(newUser);
            statusLabel.setText("User registered successfully");
            clearFields();
        } catch (SQLException e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }


    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        genderField.setValue(null);
        dateOfBirthPicker.setValue(null);
        moduleIDField.clear();
        bioField.clear();
        phoneNumberField.clear();
        profilePictureField.clear();
    }
}
