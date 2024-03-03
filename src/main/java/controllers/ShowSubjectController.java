package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Subject;
import services.SubjectService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowSubjectController {

    private final SubjectService ss = new SubjectService();

    @FXML
    private TableView<Subject> subtable;

    @FXML
    private TableColumn<Subject, String> subnamecol;

    @FXML
    private TableColumn<Subject, String> subdesccol;

    @FXML
    private TableColumn<Subject, Integer> subpricecol;

    @FXML
    private TableColumn<Subject, String> subtopiccol;

    @FXML
    private TableColumn<Subject, Subject> actionsubid;

    @FXML
    private Button addnewbutt;

    @FXML
    void initialize() {
        try {
            List<Subject> subjectList = ss.read();
            ObservableList<Subject> observableList = FXCollections.observableList(subjectList);
            subtable.setItems(observableList);
            subnamecol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            subdesccol.setCellValueFactory(new PropertyValueFactory<>("description"));
            subpricecol.setCellValueFactory(new PropertyValueFactory<>("subjectPrice"));
            subtopiccol.setCellValueFactory(new PropertyValueFactory<>("subjectTopic"));

            actionsubid.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
            actionsubid.setCellFactory(param -> new ButtonCell(this));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        addnewbutt.setOnAction(event -> openAddSubjectWindow());
    }

    private void openAddSubjectWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addsubject.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Subject");
            stage.setScene(new Scene(root));

            AddSubjectController addSubjectController = loader.getController();
            addSubjectController.initializeSubtopicComboBox();

            Stage currentStage = (Stage) subtable.getScene().getWindow();
            currentStage.setScene(stage.getScene());
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ButtonCell extends TableCell<Subject, Subject> {
        private final Button addButton = new Button("Add Session");
        private final Button deleteButton = new Button("Delete");
        private final Button updateButton = new Button("Update");
        private final Button showSessionButton = new Button("Show Sessions");
        private final ShowSubjectController parentController;

        public ButtonCell(ShowSubjectController parentController) {
            this.parentController = parentController;

            addButton.setMaxWidth(Double.MAX_VALUE);
            deleteButton.setMaxWidth(Double.MAX_VALUE);
            updateButton.setMaxWidth(Double.MAX_VALUE);
            showSessionButton.setMaxWidth(Double.MAX_VALUE);

            addButton.setOnAction(event -> {
                Subject selectedSubject = getTableView().getItems().get(getIndex());
                openAddSessionWindow(selectedSubject.getSubjectId());
            });

            deleteButton.setOnAction(event -> {
                Subject selectedSubject = getTableView().getItems().get(getIndex());

                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Confirmation");
                confirmDialog.setHeaderText("Delete Subject");
                confirmDialog.setContentText("Are you sure you want to delete this subject? It may contain sessions!");

                ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmDialog.getButtonTypes().setAll(confirmButton, cancelButton);

                confirmDialog.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == confirmButton) {
                        try {
                            ss.delete(selectedSubject.getSubjectId());
                            refreshTable();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
            updateButton.setOnAction(event -> {
                Subject selectedSubject = getTableView().getItems().get(getIndex());
                openUpdateSubjectWindow(selectedSubject);
            });
            showSessionButton.setOnAction(event -> {
                Subject selectedSubject = getTableView().getItems().get(getIndex());
                updateShowSessionsWindow(selectedSubject.getSubjectId());
            });
        }

        private void refreshTable() {
            try {
                List<Subject> subjectList = ss.read();
                ObservableList<Subject> observableList = FXCollections.observableList(subjectList);
                subtable.setItems(observableList);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

        private void openAddSessionWindow(int subjectId) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/addsession.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Add New Session");
                stage.setScene(new Scene(root));

                AddSessionController addSessionController = loader.getController();
                addSessionController.setSubjectId(subjectId);

                Stage currentStage = (Stage) subtable.getScene().getWindow();
                currentStage.setScene(stage.getScene());
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void openUpdateSubjectWindow(Subject subject) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatesubject.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Update Subject");
                stage.setScene(new Scene(root));

                UpdateSubjectController updateSubjectController = loader.getController();
                updateSubjectController.setSubject(subject);
                updateSubjectController.setStage((Stage) subtable.getScene().getWindow());

                Stage currentStage = (Stage) subtable.getScene().getWindow();
                currentStage.setScene(stage.getScene());
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void updateShowSessionsWindow(int subjectId) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showsession.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Show Sessions");
                stage.setScene(new Scene(root));

                ShowSessionController showSessionController = loader.getController();
                showSessionController.initialize(subjectId);

                Stage currentStage = (Stage) subtable.getScene().getWindow();
                currentStage.setScene(stage.getScene());
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void updateItem(Subject item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                HBox buttons = new HBox(addButton, deleteButton, updateButton, showSessionButton);
                buttons.setSpacing(5);
                setGraphic(buttons);
            }
        }
    }
}