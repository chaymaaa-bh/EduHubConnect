package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class quizResultController {

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    private int correct;
    private int wrong;
    private int quiz_user_id;
    private int quizId; // Assuming you have a variable to store the quiz ID

    // Setter method for quiz_user_id
    public void setQuizUserId(int userId) {
        this.quiz_user_id = userId;
    }

    // Setter method for quizId
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @FXML
    private void initialize() {
        correct = quizController.correct;
        wrong = quizController.wrong;

        correcttext.setText("Correct Answers : " + correct);
        wrongtext.setText("Incorrect Answers : " + String.valueOf(wrong));

        marks.setText(correct + "/10");
        float correctf = (float) correct / 10;
        correct_progress.setProgress(correctf);

        float wrongf = (float) wrong / 10;
        wrong_progress.setProgress(wrongf);

        markstext.setText(correct + " Marks Scored");

        String remarkText;
        if (correct < 2) {
            remarkText = "Oh no..! You have failed the quiz. It seems that you need to improve your general knowledge. Practice daily! Check your results here.";
        } else if (correct >= 2 && correct < 5) {
            remarkText = "Oops..! You have scored fewer marks. It seems like you need to improve your general knowledge. Check your results here.";
        } else if (correct >= 5 && correct <= 7) {
            remarkText = "Good. A bit more improvement might help you to get better results. Practice is the key to success. Check your results here.";
        } else if (correct == 8 || correct == 9) {
            remarkText = "Congratulations! Its your hard work and determination which helped you to score good marks. Check your results here.";
        } else { // correct == 10
            remarkText = "Congratulations! You have passed the quiz with full marks because of your hard work and dedication towards studies. Keep it up! Check your results here.";
        }
        remark.setText(remarkText);

        // Store user result
        storeUserResult();
    }

    // Method to store user results in the user_results table
    private void storeUserResult() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EduHubConnect", "root", "root");
            String sql = "INSERT INTO user_results (result_user_id, result_quiz_id, result_score, result_date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quiz_user_id);
            statement.setInt(2, quizId); // Replace quizId with the actual variable containing the quiz ID
            statement.setInt(3, correct); // Store the correct count as the score
            // Set the current date and time
            LocalDateTime now = LocalDateTime.now();
            statement.setObject(4, now);
            statement.executeUpdate();
            statement.close();
            connection.close();
            System.out.println("User result stored successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
