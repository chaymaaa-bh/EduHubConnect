package controllers;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Quiz;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;


public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Label username;
    @FXML
    private Button home_btn;
    @FXML
    private Button addQuiz_btn;
    @FXML
    private Button statistics_btn;
    @FXML
    private Button logout;
    @FXML
    private AnchorPane home_form;
    @FXML
    private Label home_totalQuizzes;
    @FXML
    private BarChart<?, ?> home_chart;
    @FXML
    private AnchorPane addQuiz_form;
    @FXML
    private TableView<Quiz> addQuiz_tableView;
    @FXML
    private TableColumn<Quiz, String> addQuiz_col_quizID;
    @FXML
    private TableColumn<Quiz, Integer> addQuiz_col_duration;
    @FXML
    private TableColumn<Quiz, String> addQuiz_col_title;
    @FXML
    private TableColumn<Quiz, String> addQuiz_col_description;
    @FXML
    private TableColumn<Quiz, String> addQuiz_col_subject;
    @FXML
    private TextField addQuiz_search;
    @FXML
    private TextField addQuiz_quizID;
    @FXML
    private TextField addQuiz_duration;
    @FXML
    private TextField addQuiz_title;
    @FXML
    private TextField addQuiz_description;
    @FXML
    private ComboBox<String> addQuiz_subject;
    @FXML
    private ImageView addQuiz_image;
    @FXML
    private Button addQuiz_importBtn;
    @FXML
    private Button addQuiz_addBtn;
    @FXML
    private Button addQuiz_updateBtn;
    @FXML
    private Button addQuiz_deleteBtn;
    @FXML
    private Button addQuiz_clearBtn;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private Image image;
    private ObservableList<Quiz> addQuizList;
    private double x = 0.0;
    private double y = 0.0;

    @FXML
    private Button viewQuizzes_btn;

    @FXML
    private Label home_totalCreators;
    @FXML
    private Label home_totalSubjects;
    @FXML
    private TableView<Quiz> quiz_tableView;
    @FXML
    private TableColumn<Quiz, Integer> quiz_col_quizID;
    @FXML
    private TableColumn<Quiz, String> quiz_col_title;
    @FXML
    private TableColumn<Quiz, Integer> quiz_col_creatorID;
    @FXML
    private TableColumn<Quiz, String> quiz_col_description;
    @FXML
    private TableColumn<Quiz, Integer> quiz_col_duration;
    @FXML
    private TableColumn<Quiz, String> quiz_col_subject;
    @FXML
    private TextField quiz_search;

    private ObservableList<Quiz> quizList;
    private String[] positionList = {
            "Marketer Coordinator",
            "Web Developer (Back End)",
            "Web Developer (Front End)",
            "App Developer"
    };


    public void homeTotalQuizzes() {
        String sql = "SELECT COUNT(quiz_id) FROM Quizzes"; // Assuming 'quiz_id' is the primary key of the Quizzes table
        this.connect = utils.MyDatabase.connectDb();
        int countData = 0;

        try {
            this.prepare = this.connect.prepareStatement(sql);

            for (this.result = this.prepare.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(quiz_id)")) {
            }

            this.home_totalQuizzes.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public void homeChart() {
        this.home_chart.getData().clear();
        String sql = "SELECT quiz_subject, COUNT(quiz_id) AS quiz_count FROM Quizzes GROUP BY quiz_subject";
        this.connect = utils.MyDatabase.connectDb();

        try {
            XYChart.Series series = new XYChart.Series();
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                String subject = this.result.getString("quiz_subject");
                int count = this.result.getInt("quiz_count");
                series.getData().add(new XYChart.Data(subject, count));
            }

            this.home_chart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void addQuizAdd() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "INSERT INTO Quiz (quiz_id, quiz_duration, quiz_title, quiz_description, quiz_subject) VALUES (?, ?, ?, ?, ?)";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert;
            if (!this.addQuiz_quizID.getText().isEmpty() && !this.addQuiz_duration.getText().isEmpty() && !this.addQuiz_title.getText().isEmpty() && !this.addQuiz_description.getText().isEmpty() && this.addQuiz_subject.getSelectionModel().getSelectedItem() != null) {
                String check = "SELECT quiz_id FROM Quiz WHERE quiz_id = '" + this.addQuiz_quizID.getText() + "'";
                this.statement = this.connect.createStatement();
                this.result = this.statement.executeQuery(check);
                if (this.result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quiz ID: " + this.addQuiz_quizID.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    this.prepare = this.connect.prepareStatement(sql);
                    this.prepare.setString(1, this.addQuiz_quizID.getText());
                    this.prepare.setInt(2, Integer.parseInt(this.addQuiz_duration.getText()));
                    this.prepare.setString(3, this.addQuiz_title.getText());
                    this.prepare.setString(4, this.addQuiz_description.getText());
                    this.prepare.setString(5, (String) this.addQuiz_subject.getSelectionModel().getSelectedItem());
                    this.prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quiz successfully added!");
                    alert.showAndWait();
                    // Update UI or perform any other necessary actions
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addQuizUpdate() {
        String sql = "UPDATE quiz SET quiz_title = '" + this.addQuiz_title.getText() + "', quiz_description = '" + this.addQuiz_description.getText() + "', quiz_subject = '" + this.addQuiz_subject.getSelectionModel().getSelectedItem() + "', quiz_duration = '" + this.addQuiz_duration.getText() + "' WHERE quiz_id = '" + this.addQuiz_quizID.getText() + "'";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert;
            if (!this.addQuiz_quizID.getText().isEmpty() && !this.addQuiz_title.getText().isEmpty() && !this.addQuiz_description.getText().isEmpty() && this.addQuiz_subject.getSelectionModel().getSelectedItem() != null && !this.addQuiz_duration.getText().isEmpty() ) {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Quiz ID: " + this.addQuiz_quizID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.isPresent() && option.get() == ButtonType.OK) {
                    this.statement = this.connect.createStatement();
                    this.statement.executeUpdate(sql);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    // Update UI or perform any other necessary actions
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addQuizDelete() {
        String sql = "DELETE FROM quiz WHERE quiz_id = '" + this.addQuiz_quizID.getText() + "'";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert;
            if (!this.addQuiz_quizID.getText().isEmpty() && !this.addQuiz_title.getText().isEmpty() && !this.addQuiz_description.getText().isEmpty() && this.addQuiz_subject.getSelectionModel().getSelectedItem() != null && !this.addQuiz_duration.getText().isEmpty()) {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Quiz ID: " + this.addQuiz_quizID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                    this.statement = this.connect.createStatement();
                    this.statement.executeUpdate(sql);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    // Update UI or perform any other necessary actions
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addQuizReset() {
        this.addQuiz_quizID.setText("");
        this.addQuiz_title.setText("");
        this.addQuiz_description.setText("");
        this.addQuiz_subject.getSelectionModel().clearSelection();
        this.addQuiz_duration.setText("");

    }

    public void addQuizPositionList() {
        List<String> listP = new ArrayList<>();
        for (String data : positionList) {
            listP.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listP);
        this.addQuiz_subject.setItems(listData);
    }



    public void addQuizSearch() {
        FilteredList<Quiz> filter = new FilteredList<>(this.quizList, (q) -> true);
        this.quiz_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate((predicateQuiz) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    String searchKey = newValue.toLowerCase();
                    if (Integer.toString(predicateQuiz.getQuiz_id()).contains(searchKey)) {
                        return true;
                    } else if (predicateQuiz.getQuiz_title().toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (predicateQuiz.getQuiz_description().toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (predicateQuiz.getQuiz_subject().toLowerCase().contains(searchKey)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            });
        });
        SortedList<Quiz> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(this.quiz_tableView.comparatorProperty());
        this.quiz_tableView.setItems(sortList);
    }


    public ObservableList<Quiz> addQuizListData() {
        ObservableList<Quiz> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM quiz";
        this.connect = utils.MyDatabase.connectDb();

        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                Quiz quiz = new Quiz(
                        this.result.getInt("quiz_id"),
                        this.result.getInt("quiz_duration"),
                        this.result.getString("quiz_title"),
                        this.result.getString("quiz_description"),
                        this.result.getString("quiz_subject")
                );
                listData.add(quiz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }



    public void addQuizShowListData() {
        this.addQuizList = this.addQuizListData();
        this.addQuiz_col_quizID.setCellValueFactory(new PropertyValueFactory<>("quizId"));
        this.addQuiz_col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.addQuiz_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.addQuiz_col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        this.addQuiz_col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        this.addQuiz_tableView.setItems(this.addQuizList);
    }

    public void addQuizSelect() {
        Quiz quiz = this.addQuiz_tableView.getSelectionModel().getSelectedItem();
        int num = this.addQuiz_tableView.getSelectionModel().getSelectedIndex();
        if (num - 1 >= -1) {
            this.addQuiz_quizID.setText(String.valueOf(quiz.getQuiz_id()));
            this.addQuiz_title.setText(quiz.getQuiz_title());
            this.addQuiz_description.setText(quiz.getQuiz_description());
            this.addQuiz_subject.getSelectionModel().select(quiz.getQuiz_subject());
        }
    }

    @FXML
    private void switchForm(ActionEvent event) {
        // Determine which button was clicked
        Button clickedButton = (Button) event.getSource();

        // Handle switching forms based on the button clicked
        if (clickedButton == home_btn) {
            home_form.setVisible(true);
            addQuiz_form.setVisible(false);
        } else if (clickedButton == addQuiz_btn) {
            home_form.setVisible(false);
            addQuiz_form.setVisible(true);
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.homeTotalQuizzes();
        this.homeChart();
        // Initialize other UI elements and data
        // ...
    }
}
