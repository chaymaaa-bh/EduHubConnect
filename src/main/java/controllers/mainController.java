package controllers;


import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import java.util.Date;
import java.util.Optional;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;




import java.net.URL;
import java.util.ResourceBundle;
import models.Quiz;
import utils.MyDatabase;
import java.sql.PreparedStatement;


public class mainController implements Initializable {


    @FXML
    private Button close;

    @FXML
    private ComboBox<?> comboBox_question_difficulty;

    @FXML
    private ComboBox<Integer> comboBox_question_quiz_id;

    @FXML
    private ComboBox<String> comboBox_quiz_subject;

    @FXML
    private Label home_avgDuration;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<?, ?> home_chartQuizSubject;

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
    private Button manageQuestion_btn;

    @FXML
    private AnchorPane manageQuestion_form;

    @FXML
    private Button manageQuiz_btn;

    @FXML
    private AnchorPane manageQuiz_form;

    @FXML
    private Button minimize;

    @FXML
    private TextField question_search;

    @FXML
    private TableView<?> question_tableView;

    @FXML
    private Button quiz_addBtn;

    @FXML
    private Button quiz_addBtn1;

    @FXML
    private Button quiz_clearBtn;

    @FXML
    private Button quiz_clearBtn1;

    @FXML
    private TableColumn<Quiz, String> quiz_col_description;

    @FXML
    private TableColumn<Quiz, String> quiz_col_description1;

    @FXML
    private TableColumn<Quiz, Integer> quiz_col_duration;

    @FXML
    private TableColumn<Quiz, Integer> quiz_col_id;

    @FXML
    private TableColumn<Quiz, String> quiz_col_subject;

    @FXML
    private TableColumn<Quiz, String> quiz_col_title;

    @FXML
    private Button quiz_deleteBtn;


    @FXML
    private TextField quiz_search;

    @FXML
    private TableView<?> quiz_tableView;

    @FXML
    private Button quiz_updateBtn;



    @FXML
    private TextArea textArea_question_options;


    @FXML
    private TextArea textArea_question_text;

    @FXML
    private TextField tf_question_id;

    @FXML
    private TextArea tf_quiz_description;

    @FXML
    private Spinner<?> tf_quiz_duration;

    @FXML
    private TextField tf_quiz_id;

    @FXML
    private TextField tf_quiz_title;

    @FXML
    private Label username;



    public void quizAdd() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "INSERT INTO Quizzes (quiz_id, quiz_title, quiz_duration, quiz_subject, quiz_description) VALUES (?, ?, ?, ?, ?)";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert;
            if (!this.tf_quiz_id.getText().isEmpty() && !this.tf_quiz_duration.getText().isEmpty() && !this.addQuiz_title.getText().isEmpty() && !this.addQuiz_description.getText().isEmpty() && this.addQuiz_subject.getSelectionModel().getSelectedItem() != null) {
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

    public void logout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText((String)null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                System.exit(0);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

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
    void addEmployeeAdd(ActionEvent event) {

    }

    @FXML
    void addEmployeeDelete(ActionEvent event) {

    }

    @FXML
    void addEmployeeGendernList(ActionEvent event) {

    }

    @FXML
    void addEmployeeReset(ActionEvent event) {

    }

    @FXML
    void addEmployeeSearch(KeyEvent event) {

    }

    @FXML
    void addEmployeeSelect(MouseEvent event) {

    }

    @FXML
    void addEmployeeUpdate(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        close();

    }

    @FXML
    void logout(ActionEvent event) {
        logout();

    }

    @FXML
    void minimize(ActionEvent event) {
minimize();
    }

    @FXML
    void quizSearch(KeyEvent event) {

    }

    @FXML
    void quizSelect(MouseEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == this.home_btn) {
            this.home_form.setVisible(true);
            this.manageQuiz_form.setVisible(false);
            this.manageQuestion_form.setVisible(false);
            this.home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            this.manageQuiz_btn.setStyle("-fx-background-color:transparent");
            this.manageQuestion_btn.setStyle("-fx-background-color:transparent");
            //this.homeTotalQuizzes();
           // this.homeEmployeeTotalPresent();
          //  this.homeTotalInactive();
            //this.homeChart();
        } else if (event.getSource() == this.manageQuiz_btn) {
            this.home_form.setVisible(false);
            this.manageQuiz_form.setVisible(true);
            this.manageQuestion_form.setVisible(false);
            this.manageQuiz_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            this.home_btn.setStyle("-fx-background-color:transparent");
            this.manageQuestion_btn.setStyle("-fx-background-color:transparent");
           // this.addEmployeeGendernList();
         //   this.manageQuizPositionList();
           // this.addEmployeeSearch();
        } else if (event.getSource() == this.manageQuestion_btn) {
            this.home_form.setVisible(false);
            this.manageQuiz_form.setVisible(false);
            this.manageQuestion_form.setVisible(true);
            this.manageQuestion_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            this.manageQuiz_btn.setStyle("-fx-background-color:transparent");
            this.home_btn.setStyle("-fx-background-color:transparent");
            //this.salaryShowListData();
        }


    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize other UI elements and data
        // ...

    }
}
