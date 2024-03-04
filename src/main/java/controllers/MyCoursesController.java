package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Payment;
import services.PaymentService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyCoursesController {

    @FXML
    private VBox coursesVBox;

    private final PaymentService paymentService = new PaymentService();

    @FXML
    public void initialize() {
        loadCourseCards();
    }

    private void loadCourseCards() {
        try {
            List<Payment> payments = paymentService.read();

            for (Payment payment : payments) {
                VBox card = createCourseCard(payment);
                card.setOnMouseClicked(event -> handleCardClick(payment));
                coursesVBox.getChildren().add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createCourseCard(Payment payment) {
        VBox box = new VBox();
        box.getStyleClass().add("course-box");

        Label courseLabel = new Label("Course: " + payment.getCourseBought());
        Label subjectLabel = new Label("Subject: " + payment.getSubject());
        Label priceLabel = new Label("Price: " + payment.getPricePaid());
        Label dateLabel = new Label("Booked On: " + formatDate(payment.getTimePaid()));

        box.getChildren().addAll(courseLabel, subjectLabel, priceLabel, dateLabel);

        box.setOnMouseClicked(event -> handleCardClick(payment));

        return box;
    }

    private String formatDate(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        return sdf.format(timestamp);
    }

    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student.fxml"));
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
    public void close()
    {
        System.exit(0);
    }


    public void minimize() {
        Stage stage = (Stage)this.coursesVBox.getScene().getWindow();
        stage.setIconified(true);
    }

    private void handleCardClick(Payment payment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mysessions.fxml"));
            Parent root = loader.load();

            MySessionsController sessionsController = loader.getController();
            sessionsController.setBoughtSubjectId(payment.getSubjectBoughtId());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}