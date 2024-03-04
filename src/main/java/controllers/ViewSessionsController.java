package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Session;
import services.SessionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewSessionsController implements Initializable {

    @FXML
    private VBox sessionsVBox;

    private final SessionService sessionService = new SessionService();

    private int subjectId;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
        loadSessionCards();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadSessionCards() {
        try {
            List<Session> sessionList = sessionService.readBySubjectId(subjectId);

            for (Session session : sessionList) {
                VBox card = createSessionCard(session);
                sessionsVBox.getChildren().add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createSessionCard(Session session) {
        VBox card = new VBox();
        card.getStyleClass().add("session-card");

        VBox contentContainer = new VBox();
        contentContainer.getStyleClass().add("content-container");

        Label nameLabel = new Label("Session Name: " + session.getSessionName());
        nameLabel.getStyleClass().add("name-label");
        HBox descriptionPriceBox = new HBox();
        descriptionPriceBox.getStyleClass().add("description-price-box");
        Label descriptionLabel = new Label("Description: " + session.getSessionDescription());

        descriptionPriceBox.getChildren().addAll(descriptionLabel);

        HBox buttonContainer = new HBox();

        contentContainer.getChildren().addAll(nameLabel, descriptionPriceBox, buttonContainer);
        card.getChildren().add(contentContainer);

        return card;
    }

    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewCourses.fxml"));
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