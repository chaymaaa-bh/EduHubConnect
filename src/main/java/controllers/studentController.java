package controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Quiz;
import models.Result;
import models.Question;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Button;



public class studentController implements Initializable {

    @FXML
    private TableView<Result> result_tableView;

    @FXML
    private TableColumn<Result, String> result_col_quiz_title;

    @FXML
    private TableColumn<Result, String> result_col_quiz_subject;

    @FXML
    private TableColumn<Result, String> result_col_date;

    @FXML
    private TextField result_search;

    // Global variable to store the user ID
    private int globalUserId;

    @FXML
    private Button close;

    @FXML
    private Label home_avgDuration;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private AnchorPane home_totalQuiz;

    @FXML
    private Label home_totalSubjects;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private TableColumn<?, ?> question_col_difficulty;

    @FXML
    private Button quiz_history_btn;

    @FXML
    private Button ratings_btn;

    @FXML
    private Button reports_btn;

    @FXML
    private Button forum_btn;

    @FXML
    private AnchorPane quiz_history_form;

    @FXML
    private Button take_quiz_btn;

    @FXML
    private Button courses_btn;

    @FXML
    private Button purchased_courses_btn;

    @FXML
    private Button create_course_btn;

    @FXML
    private Button export_excel_btn;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    private PreparedStatement prepare;
    private Connection connect;
    private Statement statement;
    private ResultSet result;

    private ObservableList<Result> resultList;

    public void resultShowListData() {
        this.resultList = this.resultListData();
        this.result_col_quiz_title.setCellValueFactory(new PropertyValueFactory("result_quiz_title"));
        this.result_col_quiz_subject.setCellValueFactory(new PropertyValueFactory("result_quiz_subject"));
        this.result_col_date.setCellValueFactory(new PropertyValueFactory("result_date"));
        this.result_tableView.setItems(this.resultList);
    }

    public ObservableList<Result> resultListData() {
        ObservableList<Result> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user_results WHERE result_user_id = ?";


        this.connect = utils.MyDatabase.connectDb();

        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.prepare.setInt(1, GlobalVariables.userId);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                Result result = new Result(
                        this.result.getInt("result_user_id"),
                        this.result.getInt("result_quiz_id"),
                        this.result.getInt("result_score"),
                        this.result.getDate("result_date"),
                        this.result.getString("result_quiz_title"),
                        this.result.getString("result_quiz_subject")
                );
                // Your logic to handle the result object here


                listData.add(result);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return listData;
    }


    public void close()
    {
        System.exit(0);
    }


    public void minimize() {
        Stage stage = (Stage)this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }



    @FXML
    private void exportToExcelHandler() {
        String filePath = "/Users/macbook/Desktop/ExcelResults/Results_history.xlsx"; // Specify the file path where you want to save the Excel file
        ExcelExporter.exportToExcel(result_tableView, filePath);
    }


    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == this.take_quiz_btn) {
            switchScene(event, "/home_quiz.fxml");



        } else if (event.getSource() == this.quiz_history_btn) {
            this.home_form.setVisible(false);
            this.quiz_history_form.setVisible(true);


        } else if(event.getSource() == this.home_btn){
            this.home_form.setVisible(true);
            this.quiz_history_form.setVisible(false);


        } else if (event.getSource() == this.forum_btn) {
            switchScene(event, "/showpost.fxml");


        } else if (event.getSource() == this.courses_btn) {
            switchScene(event, "/ViewCourses.fxml");


        } else if (event.getSource() == this.purchased_courses_btn) {
            switchScene(event, "/MyCourses.fxml");


        } else if (event.getSource() == this.create_course_btn) {
            switchScene(event, "/ShowSubject.fxml");


        } else if (event.getSource() == this.ratings_btn) {
            switchScene(event, "/addRating.fxml");


        } else if (event.getSource() == this.reports_btn) {
            switchScene(event, "/ShowReport.fxml");


        }
    }
    private void switchScene(ActionEvent actionEvent, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
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



    @FXML
    void close(javafx.event.ActionEvent event) {
        close();

    }

    @FXML

    void logout(ActionEvent actionEvent) {
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



    @FXML
    void minimize(javafx.event.ActionEvent event) {
        minimize();
    }

    @FXML
    void questionSearch(KeyEvent event) {

    }

    @FXML
    void questionSelect(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(GlobalVariables.userName);

        resultListData();
        resultShowListData();


    }
}