package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Question;
import services.QuestionService;

import java.io.IOException;
import java.sql.SQLException;

public class AddQuestionController {

    private final QuestionService questionService = new QuestionService();

    @FXML
    private TextField quizIdTf;

    @FXML
    private TextField questionTextTf;

    @FXML
    private TextField difficultyLevelTf;

    @FXML
    private TextField optionsTf;

    @FXML
    private TextField correctAnswersTf;

    @FXML
    void addQuestion(ActionEvent event) {
        try {
            questionService.create(new Question(
                    Integer.parseInt(quizIdTf.getText()),
                    questionTextTf.getText(),
                    difficultyLevelTf.getText(),
                    optionsTf.getText(),
                    correctAnswersTf.getText()
            ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Question added successfully");
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
        quizIdTf.setText("");
        questionTextTf.setText("");
        difficultyLevelTf.setText("");
        optionsTf.setText("");
        correctAnswersTf.setText("");
    }

    @FXML
    void navigate(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ShowQuestion.fxml"));
            quizIdTf.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
