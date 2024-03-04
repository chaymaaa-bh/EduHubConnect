package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Session;
import services.SessionService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowSessionController {

    private final SessionService sessionService = new SessionService();
    private int subjectId;

    @FXML
    private TableView<Session> subtable;

    @FXML
    private TableColumn<Session, String> sesnamecol;

    @FXML
    private TableColumn<Session, String> sesdescol;

    @FXML
    private TableColumn<Session, String> sessionDriveLinkId;

    @FXML
    private TableColumn<Session, Session> actionsesid;

    @FXML
    private Button addnewsessbutt;

    @FXML
    void initialize(int subjectId) {
        this.subjectId = subjectId;
        try {
            List<Session> sessionList = sessionService.readBySubjectId(subjectId);
            ObservableList<Session> observableList = FXCollections.observableList(sessionList);
            subtable.setItems(observableList);
            sesnamecol.setCellValueFactory(new PropertyValueFactory<>("sessionName"));
            sesdescol.setCellValueFactory(new PropertyValueFactory<>("sessionDescription"));
            sessionDriveLinkId.setCellValueFactory(new PropertyValueFactory<>("sessionDriveLink"));

            actionsesid.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
            actionsesid.setCellFactory(param -> new ButtonCell(this));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        addnewsessbutt.setOnAction(event -> openAddSessionWindow());

    }

    private void openAddSessionWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addsession.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Session");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ButtonCell extends TableCell<Session, Session> {
        private final Button deleteButton = new Button("Delete");
        private final Button updateButton = new Button("Update");
        private final ShowSessionController parentController;

        public ButtonCell(ShowSessionController parentController) {
            this.parentController = parentController;

            deleteButton.setMaxWidth(Double.MAX_VALUE);
            updateButton.setMaxWidth(Double.MAX_VALUE);

            deleteButton.setOnAction(event -> {
                Session selectedSession = getTableView().getItems().get(getIndex());

                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Confirmation");
                confirmDialog.setHeaderText("Delete Session");
                confirmDialog.setContentText("Are you sure you want to delete this session?");

                ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmDialog.getButtonTypes().setAll(confirmButton, cancelButton);

                confirmDialog.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == confirmButton) {
                        try {
                            sessionService.delete(selectedSession.getSessionId());
                            refreshTable();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });

            updateButton.setOnAction(event -> {
                Session selectedSession = getTableView().getItems().get(getIndex());
                openUpdateSessionWindow(selectedSession);
            });

        }

        private void refreshTable() {
            try {
                List<Session> sessionList = sessionService.readBySubjectId(subjectId);
                ObservableList<Session> observableList = FXCollections.observableList(sessionList);
                subtable.setItems(observableList);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

        private void openUpdateSessionWindow(Session session) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatesession.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Update Session");
                stage.setScene(new Scene(root));

                UpdateSessionController updateSessionController = loader.getController();
                updateSessionController.setSession(session);

                Stage currentStage = (Stage) subtable.getScene().getWindow();
                currentStage.setScene(stage.getScene());
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void updateItem(Session item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                HBox buttons = new HBox(deleteButton, updateButton);
                buttons.setSpacing(5);
                setGraphic(buttons);
            }
        }
    }
    public void getback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowSubject.fxml"));
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
