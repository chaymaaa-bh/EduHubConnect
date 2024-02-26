package controllers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserController {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Integer> columnUserID;

    @FXML
    private TableColumn<User, String> columnFirstName;

    @FXML
    private TableColumn<User, String> columnLastName;

    @FXML
    private TableColumn<User, String> columnEmail;

    @FXML
    private TableColumn<User, String> columnPassword;

    @FXML
    private TableColumn<User, String> columnDateOfBirth;

    @FXML
    private TableColumn<User, String> columnGender;

    @FXML
    private TableColumn<User, String> columnRole;

    @FXML
    private TableColumn<User, String> columnBio;

    @FXML
    private TableColumn<User, String> columnPhoneNumber;

    @FXML
    private TableColumn<User, String> columnProfilePicture;

    @FXML
    private TableColumn<User, Integer> columnModuleID;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField dateOfBirthField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField bioField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField profilePictureField;

    @FXML
    private TextField moduleIDField;

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }


    @FXML
    public void initialize() {
        // Set cell value factory for UserID column
        columnUserID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUser_id()).asObject());

        // Set cell value factory for other columns (assuming appropriate getter methods exist)
        columnFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        columnLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        columnEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        columnPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        columnDateOfBirth.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_of_birth().toString()));
        columnGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        columnRole.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        columnBio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBio()));
        columnPhoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone_number()));
        columnProfilePicture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfile_picture()));
        columnModuleID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getModule_id()).asObject());

        loadUserData();
    }


    private void loadUserData() {
        try {
            List<User> userList = userService.read();
            userTableView.getItems().addAll(userList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }
    }

    @FXML
    public void handleAddButtonAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/useradd.fxml"));
            emailField.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    @FXML
    public void handleUpdateButtonAction() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Update User");
            alert.setContentText("Are you sure you want to update this user?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    userService.update(selectedUser);
                    userTableView.refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle SQLException
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to update.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleDeleteButtonAction() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete User");
            alert.setContentText("Are you sure you want to delete this user?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    userService.delete(selectedUser.getUser_id());
                    userTableView.getItems().remove(selectedUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle SQLException
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }

    private void clearInputFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        dateOfBirthField.clear();
        genderField.clear();
        roleField.clear();
        bioField.clear();
        phoneNumberField.clear();
        profilePictureField.clear();
        moduleIDField.clear();
    }
}
