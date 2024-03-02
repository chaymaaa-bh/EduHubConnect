package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class forgetpasswordController {

    @FXML
    private TextField emailTextField;

    @FXML
    private Button submitBtn;
    private String generatedCode;


    @FXML
    public void submitEmail() {
        String email = emailTextField.getText().trim();

        if (email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter your email.");
            return;
        }

        // Check if the email exists in the users table
        if (isEmailExists(email)) {
            // Generate the code and store it
            generatedCode = generateCode();

            // Send the code via email
            sendCodeByEmail(email, generatedCode);
            showAlert(Alert.AlertType.INFORMATION, "Success", "An email containing the code has been sent.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Email not found.");
        }
    }

    private boolean isEmailExists(String email) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "");
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            boolean emailExists = resultSet.next();
            resultSet.close();
            statement.close();
            connection.close();
            return emailExists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String generateCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int code = random.nextInt(max - min + 1) + min;
        return String.valueOf(code);
    }

    private void sendCodeByEmail(String email, String code) {
        final String username = "chayma.benhamida@esprit.tn";
        final String password = "Ephemere54021744";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Recovery Code");
            message.setText("Your password recovery code is: " + code);
            Transport.send(message);
            System.out.println("Email sent successfully!");

            // After sending the email, load the CodeController scene and pass the generated code
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/code.fxml"));
            Parent root = loader.load();
            CodeController codeController = loader.getController();
            codeController.setExpectedCode(code);
            codeController.setUserEmail(email);
            emailTextField.getScene().setRoot(root);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Access the source node of the event
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
