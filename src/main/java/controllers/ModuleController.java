package controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.Module;
import services.ModuleService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ModuleController {

    @FXML
    private TableView<Module> tableView;

    @FXML
    private TableColumn<Module, String> columnModuleName;

    @FXML
    private TableColumn<Module, String> columnLevelID;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private ModuleService moduleService;

    private ObservableList<Module> moduleList;

    public ModuleController() {
        moduleService = new ModuleService();
    }

    @FXML
    public void initialize() {
        columnModuleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModule_name()));
        columnLevelID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLevel_id())));

        try {
            loadModules();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadModules() throws SQLException {
        List<Module> modules = moduleService.read();
        moduleList = FXCollections.observableArrayList(modules);
        tableView.setItems(moduleList);
    }



    @FXML
    void handleAddButtonAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Module");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the name of the new module:");

        Optional<String> resultName = dialog.showAndWait();
        resultName.ifPresent(name -> {
            TextInputDialog levelDialog = new TextInputDialog();
            levelDialog.setTitle("Add New Module");
            levelDialog.setHeaderText(null);
            levelDialog.setContentText("Enter the level ID for the module:");

            Optional<String> resultLevel = levelDialog.showAndWait();
            resultLevel.ifPresent(levelId -> {
                try {
                    int levelID = Integer.parseInt(levelId);
                    Module newModule = new Module(levelID, name);
                    moduleService.create(newModule);
                    moduleList.add(newModule);
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                    // Handle error (parsing or database) appropriately
                }
            });
        });
    }


    @FXML

    void handleUpdateButtonAction(ActionEvent event) {
        Module selectedModule = tableView.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            TextInputDialog nameDialog = new TextInputDialog(selectedModule.getModule_name());
            nameDialog.setTitle("Update Module");
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Enter the updated name for the module:");

            TextInputDialog levelDialog = new TextInputDialog(String.valueOf(selectedModule.getLevel_id()));
            levelDialog.setTitle("Update Module");
            levelDialog.setHeaderText(null);
            levelDialog.setContentText("Enter the updated level ID for the module:");

            Optional<String> resultName = nameDialog.showAndWait();
            resultName.ifPresent(name -> {
                Optional<String> resultLevel = levelDialog.showAndWait();
                resultLevel.ifPresent(levelId -> {
                    try {
                        selectedModule.setModule_name(name);
                        selectedModule.setLevel_id(Integer.parseInt(levelId));
                        moduleService.update(selectedModule);
                        tableView.refresh(); // Refresh TableView to reflect changes
                    } catch (NumberFormatException | SQLException e) {
                        e.printStackTrace();
                        // Handle database error or parsing error appropriately
                    }
                });
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a module to update.");
            alert.showAndWait();
        }
    }


    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        Module selectedModule = tableView.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            try {
                moduleService.delete(selectedModule.getModule_id());
                moduleList.remove(selectedModule);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
