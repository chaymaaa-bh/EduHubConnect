package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Session;
import services.SessionService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MySessionsController implements Initializable {

    @FXML
    private VBox sessionsVBox;

    private final SessionService sessionService = new SessionService();

    private int boughtSubjectId;

    public void setBoughtSubjectId(int boughtSubjectId) {
        this.boughtSubjectId = boughtSubjectId;
        loadSessionCards();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadSessionCards() {
        try {
            List<Session> sessionList = sessionService.readBySubjectId(boughtSubjectId);
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
        Label descriptionLabel = new Label("Drive Link: " + session.getSessionDriveLink());
        contentContainer.getChildren().addAll(nameLabel, descriptionLabel);
        card.getChildren().add(contentContainer);

        return card;
    }

}