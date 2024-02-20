package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Quiz;
import services.QuizService;

import java.io.IOException;
import java.sql.SQLException;

public class AddQuizController {

    private final QuizService quizService = new QuizService();

    @FXML
    private TextField quizTitleTf;

    @FXML
    private TextField quizDurationTf;

    @FXML
    private TextField quizSubjectTf;

    @FXML
    private TextField quizDescriptionTf;

    @FXML
    void addQuiz(ActionEvent event) {
        try {
            quizService.create(new Quiz(
                    Integer.parseInt(quizDurationTf.getText()),
                    quizTitleTf.getText(),
                    quizDescriptionTf.getText(),
                    quizSubjectTf.getText()
            ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Quiz added successfully");
            alert.showAndWait();
            clearFields();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void clearFields() {
        quizTitleTf.setText("");
        quizDurationTf.setText("");
        quizSubjectTf.setText("");
        quizDescriptionTf.setText("");
    }

    @FXML
    void navigate(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ShowQuiz.fxml"));
            quizTitleTf.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
