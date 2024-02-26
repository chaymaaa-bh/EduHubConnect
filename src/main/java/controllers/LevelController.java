package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Level;
import services.LevelService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LevelController {

    @FXML
    private TableView<Level> tableView;

    @FXML
    private TableColumn<Level, String> columnLevelName;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private LevelService levelService;

    private ObservableList<Level> levelList;

    public LevelController() {
        levelService = new LevelService();
    }

    @FXML
    public void initialize() {
        columnLevelName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel_name()));

        try {
            loadLevels();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadLevels() throws SQLException {
        List<Level> levels = levelService.read();
        levelList = FXCollections.observableArrayList(levels);
        tableView.setItems(levelList);
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Level");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the name of the new level:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Level newLevel = new Level(name);
            try {
                levelService.create(newLevel);
                levelList.add(newLevel);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database error appropriately
            }
        });
    }


    @FXML
    void handleUpdateButtonAction(ActionEvent event) {
        Level selectedLevel = tableView.getSelectionModel().getSelectedItem();
        if (selectedLevel != null) {
            TextInputDialog dialog = new TextInputDialog(selectedLevel.getLevel_name());
            dialog.setTitle("Update Level");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the updated name for the level:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                selectedLevel.setLevel_name(name);
                try {
                    levelService.update(selectedLevel);
                    tableView.refresh(); // Refresh TableView to reflect changes
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle database error appropriately
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a level to update.");
            alert.showAndWait();
        }
    }


    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        Level selectedLevel = tableView.getSelectionModel().getSelectedItem();
        if (selectedLevel != null) {
            try {
                levelService.delete(selectedLevel.getLevel_id());
                levelList.remove(selectedLevel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
