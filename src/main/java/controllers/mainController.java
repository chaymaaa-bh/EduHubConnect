package controllers;


import javafx.application.Platform;
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
import models.Question;

import java.net.URL;
import java.sql.*;
import java.util.*;


public class mainController implements Initializable {


    @FXML
    private Button close;

    @FXML
    private ComboBox<String> comboBox_question_difficulty;

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
    private TableView<Question> question_tableView;

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

     @FXML
     private TableColumn<Question, String> question_col_correct_answer;

     @FXML
     private TableColumn<Question, String> question_col_difficulty;

     @FXML
     private TableColumn<Question, Integer> question_col_id;

     @FXML
     private TableColumn<Question, String> question_col_option1;

     @FXML
     private TableColumn<Question, String> question_col_option2;

     @FXML
     private TableColumn<Question, String> question_col_option3;

     @FXML
     private TableColumn<Question, Integer> question_col_quiz_id;

     @FXML
     private TableColumn<Question, String> question_col_text;

     @FXML
     private Button question_deleteBtn;



     @FXML
     private Button question_updateBtn;

     @FXML
     private TextField tf_question_correct_answer;

     @FXML
     private TextField tf_question_option1;

     @FXML
     private TextField tf_question_option2;

     @FXML
     private TextField tf_question_option3;

     @FXML
     private TextArea tf_question_text;









    private PreparedStatement prepare;
     private Connection connect;
     private Statement statement;
     private ResultSet result;

     private final String[] subjectList = new String[]{"JAVA", "Financial Analysis", "Mathematics", "English"};
     private final String[] difficultyList = new String[]{"Easy", "Medium", "Hard", "Advanced"};
     private ObservableList<Quiz> quizList;
    private ObservableList<Question> questionList;
    private FilteredList<Quiz> filter;
     private FilteredList<Question> filter2;



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

     public void questionSearch() {
         filter2 = new FilteredList<>(questionList, e -> true); // Initialize filter here
         question_search.textProperty().addListener((observable, oldValue, newValue) -> {
             filter2.setPredicate(question -> {
                 if (newValue == null || newValue.isEmpty()) {
                     return true; // Show all items when search field is empty
                 }
                 String searchText = newValue.toLowerCase();
                 // Apply search logic here based on the properties of the Quiz class
                 return question.getQuestion_text().toLowerCase().contains(searchText)
                         || question.getQuestion_difficulty_level().toLowerCase().contains(searchText)
                         || question.getQuestion_option1().toLowerCase().contains(searchText)
                         || question.getQuestion_option2().toLowerCase().contains(searchText)
                         || question.getQuestion_option3().toLowerCase().contains(searchText)
                         || question.getQuestion_correct_answer().toLowerCase().contains(searchText);
             });

             // Call method to refresh TableView after applying the filter
             questionTableViewRefresh();
         });
     }

     private void questionTableViewRefresh() {
         // Apply the filtered list to the TableView
         SortedList<Question> sortedList = new SortedList<>(filter2);
         sortedList.comparatorProperty().bind(question_tableView.comparatorProperty());
         question_tableView.setItems(sortedList);
     }



     private void quizTableViewRefresh() {
         // Apply the filtered list to the TableView
         SortedList<Quiz> sortedList = new SortedList<>(filter);
         sortedList.comparatorProperty().bind(quiz_tableView.comparatorProperty());
         quiz_tableView.setItems(sortedList);
     }



    public void quizUpdate() {
        String quizId = this.tf_quiz_id.getText();
        String quizTitle = this.tf_quiz_title.getText();
        String quizDuration = this.tf_quiz_duration.getText();

        // Validate input fields
        if (quizId.isEmpty() || quizTitle.isEmpty() || quizDuration.isEmpty() ||
                this.comboBox_quiz_subject.getSelectionModel().getSelectedItem() == null ||
                this.tf_quiz_description.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Validate quiz title format (only letters and numbers, start with a letter)
        if (!quizTitle.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Quiz Title must only include letters and numbers, and start with a letter");
            alert.showAndWait();
            return;
        }

        // Validate quiz duration format (only numbers between 10 and 120)
        int duration;
        try {
            duration = Integer.parseInt(quizDuration);
            if (duration < 10 || duration > 120) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Quiz Duration must be a number between 10 and 120");
            alert.showAndWait();
            return;
        }

        String sql = "UPDATE quizzes SET quiz_title = ?, quiz_duration = ?, quiz_subject = ?, quiz_description = ? WHERE quiz_id = ?";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Quiz ID: " + quizId + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                this.prepare = this.connect.prepareStatement(sql);
                this.prepare.setString(1, quizTitle);
                this.prepare.setString(2, quizDuration);
                this.prepare.setString(3, (String) this.comboBox_quiz_subject.getSelectionModel().getSelectedItem());
                this.prepare.setString(4, this.tf_quiz_description.getText());
                this.prepare.setString(5, quizId);
                this.prepare.executeUpdate();

                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Information Message");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Successfully Updated!");
                successAlert.showAndWait();
                this.quizShowListData();
                this.quizReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void questionUpdate() {
        String questionId = this.tf_question_id.getText();
        String questionText = this.tf_question_text.getText();
        String correctAnswer = this.tf_question_correct_answer.getText();
        String option1 = this.tf_question_option1.getText();
        String option2 = this.tf_question_option2.getText();
        String option3 = this.tf_question_option3.getText();

        // Validate input fields
        if (questionId.isEmpty() || questionText.isEmpty() || this.comboBox_question_difficulty.getSelectionModel().getSelectedItem() == null ||
                option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || correctAnswer.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Validate question text format (only letters, numbers, ",", and ends with "?" or ":")
        if (!questionText.matches("^[a-zA-Z0-9,]+[?|:]$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Question text must only include letters, numbers, ',' and end with '?' or ':'");
            alert.showAndWait();
            return;
        }

        // Validate correct answer (must match one of the options)
        if (!correctAnswer.equals(option1) && !correctAnswer.equals(option2) && !correctAnswer.equals(option3)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Correct answer must be equal to option 1, option 2, or option 3");
            alert.showAndWait();
            return;
        }

        String sql = "UPDATE questions SET question_text = ?, question_difficulty_level = ?, question_option1 = ?, question_option2 = ?, question_option3 = ?, question_correct_answer = ? WHERE question_id = ?";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Question ID: " + questionId + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                this.prepare = this.connect.prepareStatement(sql);
                this.prepare.setString(1, questionText);
                this.prepare.setString(2, this.comboBox_question_difficulty.getSelectionModel().getSelectedItem());
                this.prepare.setString(3, option1);
                this.prepare.setString(4, option2);
                this.prepare.setString(5, option3);
                this.prepare.setString(6, correctAnswer);
                this.prepare.setString(7, questionId);
                this.prepare.executeUpdate();

                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Information Message");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Successfully Updated!");
                successAlert.showAndWait();
                this.questionShowListData();
                this.questionReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void questionDelete() {
        // Check if the question ID field is not empty
        if (!this.tf_question_id.getText().trim().isEmpty()) {
            String sql = "DELETE FROM questions WHERE question_id = ?";
            try {
                this.connect = utils.MyDatabase.connectDb();
                PreparedStatement ps = this.connect.prepareStatement(sql);
                // Set the question ID from the text field
                ps.setInt(1, Integer.parseInt(this.tf_question_id.getText().trim()));

                // Confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation Dialog");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Are you sure you want to delete Question ID: " + this.tf_question_id.getText() + "?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Execute update
                    ps.executeUpdate();

                    // Inform the user of success
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Information Dialog");
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("Successfully Deleted!");
                    infoAlert.showAndWait();

                    // Reset form and refresh list
                    this.questionReset();
                    this.questionShowListData();
                }
            } catch (NumberFormatException nfe) {
                // Handle invalid integer input for question ID
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Invalid Question ID. Please enter a numeric value.");
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
            // If question ID is empty, inform the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Question ID cannot be empty.");
            alert.showAndWait();
        }
    }

    public void questionDifficultyLevelList() {
        List<String> listP = new ArrayList();
        String[] var2 = this.difficultyList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listP.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listP);
        this.comboBox_question_difficulty.setItems(listData);
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

    public void questionReset() {
        this.tf_question_id.setText("");
        this.tf_question_text.setText("");
        this.comboBox_question_difficulty.getSelectionModel().clearSelection();
        this.tf_question_option1.setText("");
        this.tf_question_option2.setText("");
        this.tf_question_option3.setText("");
        this.tf_question_correct_answer.setText("");
    }


    public void quizAdd() {
        String sql = "INSERT INTO quizzes (quiz_id, quiz_title, quiz_duration, quiz_subject, quiz_description) VALUES(?,?,?,?,?)";
        this.connect = utils.MyDatabase.connectDb();

        try {
            Alert alert;
            String quizId = this.tf_quiz_id.getText();
            String quizTitle = this.tf_quiz_title.getText();
            String quizDuration = this.tf_quiz_duration.getText();

            // Validate quiz id format (only numbers, maximum 4 characters)
            if (!quizId.matches("\\d{1,4}")) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Quiz ID must be numeric and have maximum 4 characters");
                alert.showAndWait();
                return;
            }

            // Validate quiz title format (only letters and numbers, start with a letter)
            if (!quizTitle.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Quiz Title must only include letters and numbers, and start with a letter");
                alert.showAndWait();
                return;
            }

            // Validate quiz duration format (only numbers between 10 and 120)
            int duration;
            try {
                duration = Integer.parseInt(quizDuration);
                if (duration < 10 || duration > 120) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Quiz Duration must be a number between 10 and 120");
                alert.showAndWait();
                return;
            }

            // Validate other fields
            if (this.comboBox_quiz_subject.getSelectionModel().getSelectedItem() == null || this.tf_quiz_description.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }

            // Check if quiz ID already exists
            String check = "SELECT quiz_id FROM quizzes WHERE quiz_id = ?";
            this.prepare = this.connect.prepareStatement(check);
            this.prepare.setString(1, quizId);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Quiz ID: " + quizId + " already exists!");
                alert.showAndWait();
                return;
            }

            // If all validations pass, proceed with inserting the quiz
            this.prepare = this.connect.prepareStatement(sql);
            this.prepare.setString(1, quizId);
            this.prepare.setString(2, quizTitle);
            this.prepare.setString(3, quizDuration);
            this.prepare.setString(4, (String) this.comboBox_quiz_subject.getSelectionModel().getSelectedItem());
            this.prepare.setString(5, this.tf_quiz_description.getText());
            this.prepare.executeUpdate();

            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();
            this.quizShowListData();
            this.quizReset();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void questionAdd() {
        String questionId = this.tf_question_id.getText();
        String questionText = this.tf_question_text.getText();
        String correctAnswer = this.tf_question_correct_answer.getText();
        String option1 = this.tf_question_option1.getText();
        String option2 = this.tf_question_option2.getText();
        String option3 = this.tf_question_option3.getText();

        // Validate input fields
        if (questionId.isEmpty() || questionText.isEmpty() || this.comboBox_question_difficulty.getValue() == null ||
                option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || correctAnswer.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }

        // Validate question ID format (only numbers, maximum 4 digits)
        if (!questionId.matches("\\d{1,4}")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Question ID must be a number with maximum 4 digits");
            alert.showAndWait();
            return;
        }

        // Validate question text format (only letters, numbers, ",", and ends with "?" or ":")
        if (!questionText.matches("^[a-zA-Z0-9,]+[?|:]$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Question text must only include letters, numbers, ',' and end with '?' or ':'");
            alert.showAndWait();
            return;
        }

        // Validate correct answer (must match one of the options)
        if (!correctAnswer.equals(option1) && !correctAnswer.equals(option2) && !correctAnswer.equals(option3)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Correct answer must be equal to option 1, option 2, or option 3");
            alert.showAndWait();
            return;
        }

        String sql = "INSERT INTO questions (question_id, question_quiz_id, question_text, question_difficulty_level, question_option1, question_option2, question_option3, question_correct_answer) VALUES(?,?,?,?,?,?,?,?)";
        this.connect = utils.MyDatabase.connectDb();

        try {
            String check = "SELECT question_id FROM questions WHERE question_id = ?";
            this.prepare = this.connect.prepareStatement(check);
            this.prepare.setString(1, questionId);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Question ID: " + questionId + " already exists!");
                alert.showAndWait();
                return;
            }

            this.prepare = this.connect.prepareStatement(sql);
            this.prepare.setString(1, questionId);
            this.prepare.setString(2, this.comboBox_question_quiz_id.getValue().toString());
            this.prepare.setString(3, questionText);
            this.prepare.setString(4, this.comboBox_question_difficulty.getValue());
            this.prepare.setString(5, option1);
            this.prepare.setString(6, option2);
            this.prepare.setString(7, option3);
            this.prepare.setString(8, correctAnswer);
            this.prepare.executeUpdate();

            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Information Message");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Successfully Added!");
            successAlert.showAndWait();
            this.questionShowListData();
            this.questionReset();

        } catch (Exception e) {
            e.printStackTrace();
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

     public void questionSelect() {
         Question selectedQuestion = (Question) this.question_tableView.getSelectionModel().getSelectedItem();
         if (selectedQuestion != null) {
             this.tf_question_id.setText(String.valueOf(selectedQuestion.getQuestion_id()));
             this.comboBox_question_quiz_id.setValue(selectedQuestion.getQuestion_quiz_id());
             this.tf_question_text.setText(selectedQuestion.getQuestion_text());
             this.tf_question_option1.setText(selectedQuestion.getQuestion_option1());
             this.tf_question_option2.setText(selectedQuestion.getQuestion_option2());
             this.tf_question_option3.setText(selectedQuestion.getQuestion_option3());
             this.tf_question_correct_answer.setText(selectedQuestion.getQuestion_correct_answer());
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

     public void questionShowListData() {
         this.questionList = this.questionListData();
         this.question_col_id.setCellValueFactory(new PropertyValueFactory<>("question_id"));
         this.question_col_quiz_id.setCellValueFactory(new PropertyValueFactory<>("question_quiz_id"));
         this.question_col_text.setCellValueFactory(new PropertyValueFactory<>("question_text"));
         this.question_col_difficulty.setCellValueFactory(new PropertyValueFactory<>("question_difficulty_level"));
         this.question_col_option1.setCellValueFactory(new PropertyValueFactory<>("question_option1"));
         this.question_col_option2.setCellValueFactory(new PropertyValueFactory<>("question_option2"));
         this.question_col_option3.setCellValueFactory(new PropertyValueFactory<>("question_option3"));
         this.question_col_correct_answer.setCellValueFactory(new PropertyValueFactory<>("question_correct_answer"));
         this.question_tableView.setItems(this.questionList);
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

     public ObservableList<Question> questionListData() {
         ObservableList<Question> listData = FXCollections.observableArrayList();
         String sql = "SELECT * FROM questions";
         this.connect = utils.MyDatabase.connectDb();

         try {
             this.prepare = this.connect.prepareStatement(sql);
             this.result = this.prepare.executeQuery();

             while(this.result.next()) {
                 Question question = new Question(
                         this.result.getInt("question_id"),
                         this.result.getInt("question_quiz_id"), // Assuming the column name is quiz_id in the questions table
                         this.result.getString("question_text"),
                         this.result.getString("question_difficulty_level"),
                         this.result.getString("question_option1"),
                         this.result.getString("question_option2"),
                         this.result.getString("question_option3"),
                         this.result.getString("question_correct_answer")
                 );
                 listData.add(question);
             }
         } catch (Exception var4) {
             var4.printStackTrace();
         }

         return listData;
     }


    public void questionQuizIdList() {
        List<Integer> quizIds = new ArrayList<>();

        try {
            // Establish connection and create a PreparedStatement
            Connection connection = utils.MyDatabase.connectDb();
            String query = "SELECT quiz_id FROM quizzes";
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query and retrieve the results
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quiz_id");
                quizIds.add(quizId);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            // Convert the list of quiz IDs to ObservableList and set it to the combo box
            ObservableList<Integer> observableQuizIds = FXCollections.observableArrayList(quizIds);
            comboBox_question_quiz_id.setItems(observableQuizIds);
        });
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
            this.questionSearch();
            this.questionQuizIdList();
            this.questionDifficultyLevelList();
        }


    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize other UI elements and data
        // ...
        quizSubjectList();
        quizListData();
        quizShowListData();
        questionListData();
        questionShowListData();
        quizSearch();
        questionSearch();
        questionQuizIdList();
        questionDifficultyLevelList();






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


     @FXML
     void questionAdd(ActionEvent event) {
questionAdd();
     }

     @FXML
     void questionDelete(ActionEvent event) {
questionDelete();
     }

     @FXML
     void questionDifficultyList(ActionEvent event) {

questionDifficultyLevelList();
     }

     @FXML
     void questionQuizIdList(ActionEvent event) {
questionQuizIdList();
     }

     @FXML
     void questionReset(ActionEvent event) {
questionReset();
     }

     @FXML
     void questionSelect(MouseEvent event) {
questionSelect();
     }

     @FXML
     void questionUpdate(ActionEvent event) {
questionUpdate();
     }

    @FXML
    void questionSearch(KeyEvent event) {
        questionSearch();

    }

 }




 // question



