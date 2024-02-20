package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Question;
import services.QuestionService;

import java.sql.SQLException;
import java.util.List;

public class ShowQuestionController {
    private final QuestionService questionService = new QuestionService();
    private ObservableList<Question> observableList;

    @FXML
    private TableColumn<Question, Integer> questionIdCol;

    @FXML
    private TableColumn<Question, Integer> quizIdCol;

    @FXML
    private TableColumn<Question, String> questionTextCol;

    @FXML
    private TableColumn<Question, String> difficultyLevelCol;

    @FXML
    private TableColumn<Question, String> optionsCol;

    @FXML
    private TableColumn<Question, String> correctAnswersCol;

    @FXML
    private TableView<Question> questionsTableView;

    @FXML
    void initialize() {
        try {
            List<Question> questionsList = questionService.read();
            observableList = FXCollections.observableList(questionsList);
            questionsTableView.setItems(observableList);
            questionIdCol.setCellValueFactory(new PropertyValueFactory<>("question_id"));
            quizIdCol.setCellValueFactory(new PropertyValueFactory<>("quiz_id"));
            questionTextCol.setCellValueFactory(new PropertyValueFactory<>("question_text"));
            difficultyLevelCol.setCellValueFactory(new PropertyValueFactory<>("difficulty_level"));
            optionsCol.setCellValueFactory(new PropertyValueFactory<>("options"));
            correctAnswersCol.setCellValueFactory(new PropertyValueFactory<>("correct_answers"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void removeQuestion(ActionEvent event) {
        observableList.remove(0);
    }
}
