package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Quiz;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;






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
    private TableView<Quiz> quiz_tableView;

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
    private TextField tf_quiz_duration;

    @FXML
    private TextField tf_quiz_id;

    @FXML
    private TextField tf_quiz_title;

    @FXML
    private Label username;
    private PreparedStatement prepare;
     private Connection connect;
     private Statement statement;
     private ResultSet result;

     private String[] subjectList = new String[]{"JAVA", "Financial Analysis", "Mathematics", "English"};
     private ObservableList<Quiz> quizList;
     private FilteredList<Quiz> filter;


     public void quizSearch() {
         filter = new FilteredList<>(quizList, e -> true); // Initialize filter here
         quiz_search.textProperty().addListener((observable, oldValue, newValue) -> {
             filter.setPredicate(quiz -> {
                 if (newValue == null || newValue.isEmpty()) {
                     return true; // Show all items when search field is empty
                 }
                 String searchText = newValue.toLowerCase();
                 // Apply search logic here based on the properties of the Quiz class
                 return quiz.getQuiz_title().toLowerCase().contains(searchText)
                         || quiz.getQuiz_duration().toLowerCase().contains(searchText)
                         || quiz.getQuiz_subject().toLowerCase().contains(searchText)
                         || quiz.getQuiz_description().toLowerCase().contains(searchText);
             });
             // Call method to refresh TableView after applying the filter
             quizTableViewRefresh();
         });
     }

     private void quizTableViewRefresh() {
         // Apply the filtered list to the TableView
         SortedList<Quiz> sortedList = new SortedList<>(filter);
         sortedList.comparatorProperty().bind(quiz_tableView.comparatorProperty());
         quiz_tableView.setItems(sortedList);
     }



     public void quizUpdate() {
         String sql = "UPDATE quizzes SET quiz_title = '" + this.tf_quiz_title.getText() + "', quiz_duration = '" + this.tf_quiz_duration.getText() + "', quiz_subject = '" + this.comboBox_quiz_subject.getSelectionModel().getSelectedItem() +  "', quiz_description = '" + this.tf_quiz_description.getText() + "' WHERE quiz_id ='" + this.tf_quiz_id.getText() + "'";
         this.connect = utils.MyDatabase.connectDb();

         try {
             Alert alert;
             if (!this.tf_quiz_id.getText().isEmpty() && !this.tf_quiz_title.getText().isEmpty() && !this.tf_quiz_duration.getText().isEmpty() && this.comboBox_quiz_subject.getSelectionModel().getSelectedItem() != null && !this.tf_quiz_description.getText().isEmpty()) {
                 alert = new Alert(AlertType.CONFIRMATION);
                 alert.setTitle("Cofirmation Message");
                 alert.setHeaderText((String)null);
                 alert.setContentText("Are you sure you want to UPDATE Quiz ID: " + this.tf_quiz_id.getText() + "?");
                 Optional<ButtonType> option = alert.showAndWait();
                 if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                     this.statement = this.connect.createStatement();
                     this.statement.executeUpdate(sql);

                     alert = new Alert(AlertType.INFORMATION);
                     alert.setTitle("Information Message");
                     alert.setHeaderText((String)null);
                     alert.setContentText("Successfully Updated!");
                     alert.showAndWait();
                     this.quizShowListData();
                     this.quizReset();
                 }
             } else {
                 alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error Message");
                 alert.setHeaderText((String)null);
                 alert.setContentText("Please fill all blank fields");
                 alert.showAndWait();
             }
         } catch (Exception var11) {
             var11.printStackTrace();
         }

     }


     public void quizDelete() {
         // Check if the quiz ID field is not empty
         if (!this.tf_quiz_id.getText().trim().isEmpty()) {
             String sql = "DELETE FROM quizzes WHERE quiz_id = ?";
             try {
                 this.connect = utils.MyDatabase.connectDb();
                 PreparedStatement ps = this.connect.prepareStatement(sql);
                 // Set the quiz ID from the text field
                 ps.setInt(1, Integer.parseInt(this.tf_quiz_id.getText().trim()));

                 // Confirmation dialog
                 Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                 confirmationAlert.setTitle("Confirmation Dialog");
                 confirmationAlert.setHeaderText(null);
                 confirmationAlert.setContentText("Are you sure you want to delete Quiz ID: " + this.tf_quiz_id.getText() + "?");

                 Optional<ButtonType> result = confirmationAlert.showAndWait();
                 if (result.get() == ButtonType.OK) {
                     // Execute update
                     ps.executeUpdate();

                     // Inform the user of success
                     Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                     infoAlert.setTitle("Information Dialog");
                     infoAlert.setHeaderText(null);
                     infoAlert.setContentText("Successfully Deleted!");
                     infoAlert.showAndWait();

                     // Reset form and refresh list
                     this.quizReset();
                     this.quizShowListData();
                 }
             } catch (NumberFormatException nfe) {
                 // Handle invalid integer input for quiz ID
                 Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                 errorAlert.setTitle("Error Dialog");
                 errorAlert.setHeaderText(null);
                 errorAlert.setContentText("Invalid Quiz ID. Please enter a numeric value.");
                 errorAlert.showAndWait();
             } catch (Exception e) {
                 // General error handling
                 Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                 errorAlert.setTitle("Error Dialog");
                 errorAlert.setHeaderText(null);
                 errorAlert.setContentText("An error occurred: " + e.getMessage());
                 errorAlert.showAndWait();
                 e.printStackTrace();
             }
         } else {
             // If quiz ID is empty, inform the user
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setHeaderText(null);
             alert.setContentText("Quiz ID cannot be empty.");
             alert.showAndWait();
         }
     }

     public void quizSubjectList() {
         List<String> listP = new ArrayList();
         String[] var2 = this.subjectList;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
             String data = var2[var4];
             listP.add(data);
         }

         ObservableList listData = FXCollections.observableArrayList(listP);
         this.comboBox_quiz_subject.setItems(listData);
     }

     public void quizReset() {
         this.tf_quiz_id.setText("");
         this.tf_quiz_title.setText("");
         this.tf_quiz_duration.setText("");
         this.comboBox_quiz_subject.getSelectionModel().clearSelection();
         this.tf_quiz_description.setText("");
     }

     public void quizAdd() {
         String sql = "INSERT INTO quizzes (quiz_id, quiz_title, quiz_duration, quiz_subject, quiz_description) VALUES(?,?,?,?,?)";
         this.connect = utils.MyDatabase.connectDb();

         try {
             Alert alert;
             if (!this.tf_quiz_id.getText().isEmpty() && !this.tf_quiz_title.getText().isEmpty() && !this.tf_quiz_duration.getText().isEmpty() && this.comboBox_quiz_subject.getSelectionModel().getSelectedItem() != null && !this.tf_quiz_description.getText().isEmpty()) {
                 String check = "SELECT quiz_id FROM quizzes WHERE quiz_id = '" + this.tf_quiz_id.getText() + "'";
                 this.statement = this.connect.createStatement();
                 this.result = this.statement.executeQuery(check);
                 if (this.result.next()) {
                     alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Error Message");
                     alert.setHeaderText((String)null);
                     alert.setContentText("Quiz ID: " + this.tf_quiz_id.getText() + " already exists!");
                     alert.showAndWait();
                 } else {
                     this.prepare = this.connect.prepareStatement(sql);
                     this.prepare.setString(1, this.tf_quiz_id.getText());
                     this.prepare.setString(2, this.tf_quiz_title.getText());
                     this.prepare.setString(3, this.tf_quiz_duration.getText());
                     this.prepare.setString(4, (String)this.comboBox_quiz_subject.getSelectionModel().getSelectedItem());
                     this.prepare.setString(5, this.tf_quiz_description.getText());
                     this.prepare.executeUpdate();

                     alert = new Alert(AlertType.INFORMATION);
                     alert.setTitle("Information Message");
                     alert.setHeaderText((String)null);
                     alert.setContentText("Successfully Added!");
                     alert.showAndWait();
                     this.quizShowListData();
                     this.quizReset();
                 }
             } else {
                 alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error Message");
                 alert.setHeaderText((String)null);
                 alert.setContentText("Please fill all blank fields");
                 alert.showAndWait();
             }
         } catch (Exception var8) {
             var8.printStackTrace();
         }

     }

     public void quizSelect() {
         Quiz selectedQuiz = this.quiz_tableView.getSelectionModel().getSelectedItem();
         if (selectedQuiz != null) {
             this.tf_quiz_id.setText(String.valueOf(selectedQuiz.getQuiz_id()));
             this.tf_quiz_title.setText(selectedQuiz.getQuiz_title());
             this.tf_quiz_duration.setText(selectedQuiz.getQuiz_duration());
             this.tf_quiz_description.setText(selectedQuiz.getQuiz_description());
         }
     }


     public void quizShowListData() {
         this.quizList = this.quizListData();
         this.quiz_col_id.setCellValueFactory(new PropertyValueFactory("quiz_id"));
         this.quiz_col_title.setCellValueFactory(new PropertyValueFactory("quiz_title"));
         this.quiz_col_duration.setCellValueFactory(new PropertyValueFactory("quiz_duration"));
         this.quiz_col_subject.setCellValueFactory(new PropertyValueFactory("quiz_subject"));
         this.quiz_col_description.setCellValueFactory(new PropertyValueFactory("quiz_description"));
         this.quiz_tableView.setItems(this.quizList);
     }

     public ObservableList<Quiz> quizListData() {
         ObservableList<Quiz> listData = FXCollections.observableArrayList();
         String sql = "SELECT * FROM quizzes";
         this.connect = utils.MyDatabase.connectDb();

         try {
             this.prepare = this.connect.prepareStatement(sql);
             this.result = this.prepare.executeQuery();

             while(this.result.next()) {
                 Quiz quiz = new Quiz(this.result.getInt("quiz_id"), this.result.getString("quiz_title"), this.result.getString("quiz_duration"), this.result.getString("quiz_subject"), this.result.getString("quiz_description"));
                 listData.add(quiz);
             }
         } catch (Exception var4) {
             var4.printStackTrace();
         }

         return listData;
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

    


   /*  @FXML
     void quizSubjectList(ActionEvent event) {
   quizSubjectList();
     }

    */

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
quizSearch();
    }

    @FXML
    void quizSelect(MouseEvent event) {
   quizSelect();
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
            this.quizSearch();
            this.quizSubjectList();
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
        quizSubjectList();
        quizListData();
        quizShowListData();
        quizSearch();




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
     void quizAdd(ActionEvent event) {
 quizAdd();
     }

     @FXML
     void quizDelete(ActionEvent event) {
quizDelete();
     }

     @FXML
     void quizReset(ActionEvent event) {
quizReset();
     }





     @FXML
     void quizUpdate(ActionEvent event) {
quizUpdate();
     }
 }


 // question



