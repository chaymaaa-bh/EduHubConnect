package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Quiz;
import services.QuizService;

import java.sql.SQLException;
import java.util.List;

public class ShowQuizController {
    private final QuizService quizService = new QuizService();
    private ObservableList<Quiz> observableList;

    @FXML
    private TableColumn<Quiz, Integer> quizIdCol;

    @FXML
    private TableColumn<Quiz, String> quizTitleCol;

    @FXML
    private TableColumn<Quiz, Integer> quizDurationCol;

    @FXML
    private TableColumn<Quiz, String> quizSubjectCol;

    @FXML
    private TableColumn<Quiz, String> quizDescriptionCol;

    @FXML
    private TableView<Quiz> quizzesTableView;

    @FXML
    void initialize() {
        try {
            List<Quiz> quizzesList = quizService.read();
            observableList = FXCollections.observableList(quizzesList);
            quizzesTableView.setItems(observableList);
            quizIdCol.setCellValueFactory(new PropertyValueFactory<>("quiz_id"));
            quizTitleCol.setCellValueFactory(new PropertyValueFactory<>("quiz_title"));
            quizDurationCol.setCellValueFactory(new PropertyValueFactory<>("quiz_duration"));
            quizSubjectCol.setCellValueFactory(new PropertyValueFactory<>("quiz_subject"));
            quizDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("quiz_description"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void removeQuiz(ActionEvent event) {
        observableList.remove(0);
    }
}
