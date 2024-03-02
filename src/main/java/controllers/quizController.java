package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.Question;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class quizController {

    @FXML
    public Label question;

    @FXML
    private Label timerLabel;

    @FXML
    public Button opt1, opt2, opt3;

    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;

    private Connection connection;
    private int selectedQuizId;
    private int quizDuration;
    private Timeline timeline;
    private int quizDurationSeconds;

    public void setSelectedQuizId(int selectedQuizId) {
        this.selectedQuizId = selectedQuizId;
    }
    @FXML
    private AnchorPane quiz_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    public void close() {
        switchScene("/student.fxml");
    }

    private void switchScene(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = loader.load();

            // Access the current stage
            Stage stage = (Stage) quiz_form.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage)this.quiz_form.getScene().getWindow();
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
    private void initialize() {
        // Initialize the connection
        try {
            // Replace "jdbcUrl", "username", and "password" with your actual database connection details
            String jdbcUrl = "jdbc:mysql://localhost:3306/edu";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int selectedQuizId = getSelectedQuizId();
        loadQuestions(selectedQuizId);
        System.out.println("Quiz duration: " + quizDuration + " minutes");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Update the timer label every second
            updateTimer();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        startTimer();


    }

    public int getSelectedQuizId() {
        return selectedQuizId;
    }

    public void loadQuestions(Integer quizId) {
        try {
            List<Question> questionList = new ArrayList<>();

            // Prepare and execute the SQL query to fetch questions for the selected quiz
            String sql = "SELECT * FROM questions WHERE question_quiz_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedQuizId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question(resultSet.getInt("question_id"),
                        resultSet.getInt("question_quiz_id"),
                        resultSet.getString("question_text"),
                        resultSet.getString("question_difficulty_level"),
                        resultSet.getString("question_option1"),
                        resultSet.getString("question_option2"),
                        resultSet.getString("question_option3"),
                        resultSet.getString("question_correct_answer"));
                questionList.add(question);
            }

            if (!questionList.isEmpty()) {
                Question currentQuestion = questionList.get(counter);

                // Display the current question and options
                question.setText(currentQuestion.getQuestion_text());
                opt1.setText(currentQuestion.getQuestion_option1());
                opt2.setText(currentQuestion.getQuestion_option2());
                opt3.setText(currentQuestion.getQuestion_option3());
            } else {
                System.out.println("No questions found for the selected quiz.");
            }

            // Close the result set and statement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void opt1clicked(ActionEvent event) {
        checkAnswer(opt1.getText());
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        checkAnswer(opt2.getText());
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        checkAnswer(opt3.getText());
    }


    private Question getCurrentQuestion() {
        List<Question> questionList = new ArrayList<>();

        // Fetch the questions for the selected quiz from the database
        try {
            String sql = "SELECT * FROM questions WHERE question_quiz_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedQuizId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question(resultSet.getInt("question_id"),
                        resultSet.getInt("question_quiz_id"),
                        resultSet.getString("question_text"),
                        resultSet.getString("question_difficulty_level"),
                        resultSet.getString("question_option1"),
                        resultSet.getString("question_option2"),
                        resultSet.getString("question_option3"),
                        resultSet.getString("question_correct_answer"));
                questionList.add(question);
            }

            // Return the current question based on the counter value
            return questionList.get(counter);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Handle the error as per your requirements
        }
    }


    private void checkAnswer(String selectedAnswer) {
        // Get the correct answer for the current question
        String correctAnswer = getCurrentQuestion().getQuestion_correct_answer();

        if (selectedAnswer.equals(correctAnswer)) {
            // Increment correct count
            correct++;
        } else {
            wrong++;
        }

        if (counter == 9) {
            try {
                Stage thisStage = (Stage) question.getScene().getWindow();
                thisStage.close();

                // Load the result screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/result_quiz.fxml"));
                Scene quizScene = new Scene(loader.load());
                quizScene.setFill(Color.TRANSPARENT);
                Stage quizStage = new Stage();
                quizStage.setScene(quizScene);
                quizStage.initStyle(StageStyle.TRANSPARENT);
                quizStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions(selectedQuizId);
        }
    }

    public void setQuizDuration(String duration) {
        int quizDurationMinutes = Integer.parseInt(duration);
        this.quizDurationSeconds = quizDurationMinutes * 60; // Convert minutes to seconds
        updateTimer(); // Update timer immediately when duration is set
    }

    /*
    public void setQuizDuration(String duration) {
        int quizDurationSeconds = Integer.parseInt(duration);
        this.quizDurationSeconds = quizDurationSeconds; // Duration is already in seconds
        updateTimer(); // Update timer immediately when duration is set
    }

     */

    public void startTimer() {
        timeline.play();
    }

    private void showResultScreen() {
        try {
            Stage thisStage = (Stage) question.getScene().getWindow();
            thisStage.close();

            // Load the result screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/result_quiz.fxml"));
            Scene quizScene = new Scene(loader.load());
            quizScene.setFill(Color.TRANSPARENT);
            Stage quizStage = new Stage();
            quizStage.setScene(quizScene);
            quizStage.initStyle(StageStyle.TRANSPARENT);
            quizStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTimer() {
        int minutes = quizDurationSeconds / 60;
        int seconds = quizDurationSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

        if (quizDurationSeconds > 0) {
            quizDurationSeconds--; // Decrement the remaining time
        } else {
            // Timer reached zero
            timeline.stop(); // Stop the timer

            // Check if there are unanswered questions
            int unanswered = 10 - (correct + wrong);
            if (unanswered > 0) {
                // Mark all unanswered questions as wrong
                wrong += unanswered;
                showResultScreen(); // Proceed to the result screen
            } else {
                // All questions are answered, proceed to the result screen
                showResultScreen();
            }
        }
    }

}
