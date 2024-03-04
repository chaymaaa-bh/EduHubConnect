package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Report;
import services.ReportService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ShowReportController {
    @FXML
    private PieChart harassmentPieChart;

    @FXML
    private TableView<Report> reportsTable;

    @FXML
    private TableColumn<Report, String> showCourseReportedId;

    @FXML
    private TableColumn<Report, String> reportDescId;

    @FXML
    private TableColumn<Report, String> reasonReportId;

    @FXML
    private TableColumn<Report, String> reportDateId;

    @FXML
    private TableColumn<Report, Report> reportActionsId;

    @FXML
    private Button AddNewReportId;

    private final ReportService reportService = new ReportService();

    public void initialize() {
        showCourseReportedId.setCellValueFactory(new PropertyValueFactory<>("reportedCourse"));
        reportDescId.setCellValueFactory(new PropertyValueFactory<>("reportDescription"));
        reasonReportId.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        reportDateId.setCellValueFactory(cellData -> {
            String formattedDate = formatDate(cellData.getValue().getReportDate());
            return new ReadOnlyObjectWrapper<>(formattedDate);
        });
        reportActionsId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        reportActionsId.setCellFactory(param -> new ButtonCell(this));
        loadData();

        AddNewReportId.setOnAction(event -> openAddReportWindow());
        populateHarassmentPieChart();
    }
    private void populateHarassmentPieChart() {
        ObservableList<Report> reports = reportsTable.getItems();

        // Map to store count of each type of harassment
        Map<String, Integer> harassmentCountMap = new HashMap<>();

        // Count occurrences of each type of harassment
        for (Report report : reports) {
            String reportType = report.getReportType();
            harassmentCountMap.put(reportType, harassmentCountMap.getOrDefault(reportType, 0) + 1);
        }

        // Create PieChart data from the map
        ObservableList<Data> pieChartData = harassmentPieChart.getData();
        pieChartData.clear(); // Clear previous data

        for (Map.Entry<String, Integer> entry : harassmentCountMap.entrySet()) {
            pieChartData.add(new Data(entry.getKey(), entry.getValue()));
        }
    }
    private String formatDate(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        return sdf.format(timestamp);
    }
    public void loadData() {
        try {
            // Fetch all reports from the database
            List<Report> reports = reportService.read();

            // Convert the list to an ObservableList
            ObservableList<Report> reportList = FXCollections.observableArrayList(reports);

            // Set the items list of the table view
            reportsTable.setItems(reportList);
        } catch (SQLException e) {
            showAlert("Error fetching reports from the database: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void openAddReportWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReport.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Report");
            stage.setScene(new Scene(root));

            AddReportController addReportController = loader.getController();
            addReportController.initialize();

            Stage currentStage = (Stage) reportsTable.getScene().getWindow();
            currentStage.setScene(stage.getScene());
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ButtonCell extends TableCell<Report, Report> {
        private final Button deleteButton = new Button("Delete");
        private final Button updateButton = new Button("Update");
        private final ShowReportController parentController;

        public ButtonCell(ShowReportController parentController) {
            this.parentController = parentController;

            deleteButton.setMaxWidth(Double.MAX_VALUE);
            updateButton.setMaxWidth(Double.MAX_VALUE);


            deleteButton.setOnAction(event -> {
                Report selectedReport = getTableView().getItems().get(getIndex());

                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Confirmation");
                confirmDialog.setHeaderText("Delete Report");
                confirmDialog.setContentText("Are you sure you want to delete this report? It may contain sessions!");

                ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmDialog.getButtonTypes().setAll(confirmButton, cancelButton);

                confirmDialog.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == confirmButton) {
                        try {
                            reportService.delete(selectedReport.getReportId());
                            refreshTable();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
            updateButton.setOnAction(event -> {
                Report selectedReport = getTableView().getItems().get(getIndex());
                openUpdateReportWindow(selectedReport);
            });
        }
        private void refreshTable() {
            try {
                List<Report> reportList = reportService.read();
                ObservableList<Report> observableList = FXCollections.observableList(reportList);
                reportsTable.setItems(observableList);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        private void openUpdateReportWindow(Report report) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatereport.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Update Report");
                stage.setScene(new Scene(root));

                UpdateReportController updateReportController = loader.getController();
                updateReportController.setReport(report);
                updateReportController.setStage((Stage) reportsTable.getScene().getWindow());

                Stage currentStage = (Stage) reportsTable.getScene().getWindow();
                currentStage.setScene(stage.getScene());
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void updateItem(Report item, boolean empty) {
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

}
