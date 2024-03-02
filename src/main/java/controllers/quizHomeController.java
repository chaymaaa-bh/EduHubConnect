package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Quiz;
import utils.MyDatabase;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Random;

public class quizHomeController implements Initializable {



    @FXML
    private Button playquizbtn;

    @FXML
    private ComboBox<String> comboBox_tq_subject;

    @FXML
    private ComboBox<Integer> comboBox_tq_duration;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane home_quiz_form;

    private MyDatabase myDatabase;

    private Connection connection;

    private ObservableList<Quiz> quizzes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = MyDatabase.connectDb();
        quizzes = FXCollections.observableArrayList();
        loadSubjects();
        loadDurations();
    }

    @FXML
    public void close() {
        switchScene("/student.fxml");
    }

    private void switchScene(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = loader.load();

            // Access the current stage
            Stage stage = (Stage) home_quiz_form.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage)this.home_quiz_form.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void close(javafx.event.ActionEvent event) {
        close();

    }
    @FXML
    void minimize(javafx.event.ActionEvent event) {
        minimize();
    }

    @FXML
    void durationList(ActionEvent event) {
        loadDurations();
    }

    @FXML
    void subjectList(ActionEvent event) {
        loadSubjects();
    }

    private void loadSubjects() {
        try {
            String sql = "SELECT module_name FROM modules";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<String> subjectList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                subjectList.add(resultSet.getString("module_name"));
            }
            comboBox_tq_subject.setItems(subjectList);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadDurations() {
        ObservableList<Integer> durationList = FXCollections.observableArrayList(15, 20, 30, 40, 60);
        comboBox_tq_duration.setItems(durationList);
    }

    @FXML
    void playQuiz(ActionEvent event) {
        String selectedSubject = comboBox_tq_subject.getValue();
        Integer selectedDuration = comboBox_tq_duration.getValue();
        if (selectedSubject != null && selectedDuration != null) {
            try {
                String sql = "SELECT * FROM Quizzes WHERE quiz_subject = ? AND quiz_duration <= ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, selectedSubject);
                statement.setInt(2, selectedDuration);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    // Check if the quiz has at least 10 questions before adding it to the list
                    int quizId = resultSet.getInt("quiz_id");
                    if (countQuestions(quizId) >= 10) {
                        Quiz quiz = new Quiz(
                                quizId,
                                resultSet.getString("quiz_title"),
                                resultSet.getString("quiz_duration"),
                                resultSet.getString("quiz_subject"),
                                resultSet.getString("quiz_description")
                        );
                        quizzes.add(quiz);
                    }
                }
                resultSet.close();
                statement.close();

                if (!quizzes.isEmpty()) {
                    Random random = new Random();
                    Quiz selectedQuiz = quizzes.get(random.nextInt(quizzes.size()));
                    GlobalVariables.quizId = selectedQuiz.getQuiz_id(); // Set the quizId as a global variable
                    GlobalVariables.selectedQuizTitle = selectedQuiz.getQuiz_title();
                    GlobalVariables.selectedQuizSubject = selectedQuiz.getQuiz_subject();
                    openQuiz(selectedQuiz);

                    System.out.println("Selected Quiz: " + selectedQuiz);
                    openQuiz(selectedQuiz);
                } else {
                    System.out.println("No quizzes found for the selected criteria.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select both subject and duration.");
        }
    }

    private int countQuestions(int quizId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Questions WHERE question_quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, quizId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        resultSet.close();
        statement.close();
        return count;
    }




    private void openQuiz(Quiz selectedQuiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
            Scene quizScene = new Scene(loader.load());
            quizScene.setFill(Color.TRANSPARENT);
            Stage quizStage = new Stage();
            quizStage.setScene(quizScene);
            quizStage.initStyle(StageStyle.TRANSPARENT);
            quizStage.show();

            quizController quizController = loader.getController();
            quizController.setSelectedQuizId(selectedQuiz.getQuiz_id());
            quizController.setQuizDuration(selectedQuiz.getQuiz_duration()); // Pass selected quiz duration
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}