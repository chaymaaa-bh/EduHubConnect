package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Subject;
import services.SubjectService;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddSubjectController implements Initializable {
    private final SubjectService ss = new SubjectService();

    @FXML
    private Button addButton;

    @FXML
    private TextField nmsub;

    @FXML
    private TextField descSub;

    @FXML
    private TextField prSub;

    @FXML
    private ComboBox<String> subtopic;

    @FXML
    void addSubject(ActionEvent event) {
        String subjectName = nmsub.getText();
        String priceText = prSub.getText();

        if (validateFields(subjectName, priceText)) {
            if (isSubjectNameUnique(subjectName)) {
                if (isInteger(priceText)) {
                    try {
                        ss.create(new Subject(subjectName, priceText, descSub.getText(), subtopic.getValue()));
                        showAlert("Success", "Course added successfully");
                    } catch (SQLException e) {
                        showAlert("Error", e.getMessage());
                    }
                } else {
                    showAlert("Failed", "Invalid price.");
                }
            } else {
                showAlert("Failed", "Course name already exists");
            }
        } else {
            showAlert("Failed", "All fields must be filled out");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSubtopicComboBox();
    }

    void initializeSubtopicComboBox() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/3a2", "root", "");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT `module_name` FROM `module`");

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

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isSubjectNameUnique(String subjectName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/3a2", "root", "");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM subject WHERE subjectname = '" + subjectName + "'");

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
