package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import models.User;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterUserController {
    @FXML
    private ComboBox<String> subtopic;


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
    private TextField moduleNameField;

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
        String moduleName = subtopic.getValue();
        String bio = bioField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String profilePicture = profilePictureField.getText().trim();

        // Input Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || gender == null || dateOfBirth == null || moduleName.isEmpty()) {
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


        // Creating User
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(moduleName, firstName, lastName, email, hashedPassword, gender, "scholar", bio, phoneNumber, profilePicture, dateOfBirth);

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


    public void getback(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            emailField.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    public void initialize() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT module_name FROM modules");

            ObservableList<String> moduleNames = FXCollections.observableArrayList();

            while (resultSet.next()) {
                moduleNames.add(resultSet.getString("module_name"));
            }

            resultSet.close();
            statement.close();
            connection.close();

            subtopic.setItems(moduleNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
