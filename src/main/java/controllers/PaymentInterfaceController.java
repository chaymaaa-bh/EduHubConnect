package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Payment;
import services.PaymentService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

public class PaymentInterfaceController {

    @FXML
    private TextField cardNumberId;

    @FXML
    private Label paymentMessageLabel;

    @FXML
    private Button booksubjectID;

    private int subjectId;
    private String subjectName;
    private int subjectPrice;
    private final PaymentService paymentService = new PaymentService();

    public void setSubjectInfo(int subjectId, String subjectName, int subjectPrice) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectPrice = subjectPrice;

        String paymentMessage = "You are about to pay " + subjectPrice +
                " for the subject " + subjectName + ". Click book to confirm.";
        paymentMessageLabel.setText(paymentMessage);
    }

    @FXML
    private void handleBookSubjectClick(ActionEvent event) {
        if (isNumeric(cardNumberId.getText())) {
            processPayment();
        } else {
            showAlert("Invalid Input", "Card number must be a valid number.");
        }
    }

    private void processPayment() {
        try {
            System.out.println("Start processing payment...");
            Stripe.apiKey = "sk_test_51OqDlxBqep0nzT2voylW1Mha2dChDZ5LtMulps6UwywXUmAJhyncEcbqLJ3DqV1aUZ1H5RxxAnKzYvl6LWnCEGgN00Dvo6eWO6";
            System.out.println("API Key set successfully.");
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) subjectPrice * 100)  // Convert dollars to cents
                    .setCurrency("usd")
                    .build();

            System.out.println("About to create PaymentIntent...");
            PaymentIntent intent = PaymentIntent.create(params);

            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            Payment payment = new Payment();
            payment.setCourseBought(subjectName);
            payment.setPricePaid(Integer.parseInt(String.valueOf(subjectPrice)));
            payment.setTimePaid(new Timestamp(System.currentTimeMillis()));
            payment.setSubjectBoughtId(subjectId);

            paymentService.create(payment);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Course purchased successfully");
            purchaseEmail();
            alert.showAndWait();
        } catch (StripeException | NumberFormatException e) {
            System.out.println("Payment failed. Error: " + e.getMessage());
            showAlert("Payment failed", "Error: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void purchaseEmail() {
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

                message.setSubject("Course purchase sucess!");
                message.setText("Dear User,\n\nThis is to inform you that your course is purchased successfully.\n\nBest regards,\nEduHub Connect");

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
