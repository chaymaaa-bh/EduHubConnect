package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class quizResultController {

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    private int correct;
    private int wrong;
    private int quiz_user_id;
    private int quizId;

    public void setQuizUserId(int userId) {
        this.quiz_user_id = userId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @FXML
    private AnchorPane result_form;

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
            Stage stage = (Stage) result_form.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage)this.result_form.getScene().getWindow();
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

        storeUserResult();
        sendQuizResultEmail();
    }

    private void storeUserResult() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "");
            String sql = "INSERT INTO user_results (result_user_id, result_quiz_id, result_score, result_date, result_quiz_title, result_quiz_subject) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, GlobalVariables.userId);
            statement.setInt(2, quizId);
            statement.setInt(3, correct);
            LocalDateTime now = LocalDateTime.now();
            statement.setObject(4, now);
            statement.setObject(5, GlobalVariables.selectedQuizTitle);
            statement.setObject(6, GlobalVariables.selectedQuizSubject);

            statement.executeUpdate();
            statement.close();
            connection.close();
            System.out.println("User result stored successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendQuizResultEmail() {
        String recipientEmail = GlobalVariables.userEmail;

        // Check if recipientEmail is not null before using it
        if (recipientEmail != null) {
            String senderEmail = "chayma.benhamida@esprit.tn";
            String password = "Ephemere54021744";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));

                // Create InternetAddress object only if recipientEmail is not null
                InternetAddress recipientAddress = new InternetAddress(recipientEmail);
                message.setRecipient(Message.RecipientType.TO, recipientAddress);

                message.setSubject("Quiz Result Notification");
                message.setText("Dear User,\n\nThis is to inform you that your quiz result is now available.\n\nBest regards,\nEduHub Connect");

                Transport.send(message);

                System.out.println("Quiz result email sent successfully to " + recipientEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Recipient email is null.");
        }
    }

}
