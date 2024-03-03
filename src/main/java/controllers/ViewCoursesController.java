package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Subject;
import services.PaymentService;
import services.SubjectService;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ViewCoursesController implements Initializable {

    @FXML
    private VBox coursesVBox;

    @FXML
    private RadioButton sortByCheersRadioButton;

    @FXML
    private RadioButton sortByPriceRadioButton;

    @FXML
    private RadioButton sortByIndexRadioButton;

    private Menu chooseSubjectMenu;

    @FXML
    private MenuItem sortByCheersMenuItem;
    private final SubjectService subjectService = new SubjectService();
    private final PaymentService paymentService = new PaymentService();

    private final Map<Integer, SimpleIntegerProperty> cheerCounts = new HashMap<>();
    private final Set<Integer> cheeredSubjects = new HashSet<>();
    private ToggleGroup toggleGroup = new ToggleGroup();
    private static final String USER_ACTIONS_FILE = "user_actions.txt";
    private List<Subject> subjectsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createCheerCountsFile();
        createUserActionsFile();
        loadUserActions();
        loadCheerCounts();
        loadCourseCards();
        ScrollPane scrollPane = new ScrollPane(coursesVBox);
        VBox.setVgrow(coursesVBox, Priority.ALWAYS);

        sortByCheersRadioButton.setToggleGroup(toggleGroup);
        sortByPriceRadioButton.setToggleGroup(toggleGroup);
        sortByIndexRadioButton.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;

                if (selectedRadioButton == sortByCheersRadioButton) {
                    sortSubjectsByCheers(subjectsList);
                } else if (selectedRadioButton == sortByPriceRadioButton) {
                    sortSubjectsByPrice(subjectsList);
                } else if (selectedRadioButton == sortByIndexRadioButton) {
                    sortSubjectsByIndex(subjectsList);
                }
                updateUI();
            }
        });

        sortByCheersRadioButton.setSelected(true);
        sortSubjectsByCheers(subjectsList);
    }

    private void createUserActionsFile() {
        File file = new File(USER_ACTIONS_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCheerCountsFile() {
        File file = new File("cheer_counts.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUserActions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_ACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    int subjectId = Integer.parseInt(parts[0]);
                    String action = parts[1];
                    if (action.equals("cheer")) {
                        cheeredSubjects.add(subjectId);
                    }
                } else if (parts.length == 3 && parts[2].equals("cheer")) {
                    int subjectId = Integer.parseInt(parts[0]);
                    int count = Integer.parseInt(parts[1]);
                    cheerCounts.put(subjectId, new SimpleIntegerProperty(count));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCourseCards() {
        try {
            List<Subject> subjectList;

            Toggle selectedToggle = toggleGroup.getSelectedToggle();
            if (selectedToggle == sortByCheersRadioButton) {
                subjectList = sortSubjectsByCheers(subjectService.read());
            } else if (selectedToggle == sortByPriceRadioButton) {
                subjectList = sortSubjectsByPrice(subjectService.read());
                System.out.println("Sorted by Price: " + subjectList);
            } else if (selectedToggle == sortByIndexRadioButton) {
                subjectList = sortSubjectsByIndex(subjectService.read());
            } else {
                subjectList = sortSubjectsByCheers(subjectService.read());
            }

            coursesVBox.getChildren().clear();

            for (Subject subject : subjectList) {
                VBox card = createCourseCard(subject);
                card.setOnMouseClicked(event -> handleCardClick(subject.getSubjectId()));
                coursesVBox.getChildren().add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<Subject> sortSubjectsByCheers(List<Subject> subjects) {
        subjects.sort((subject1, subject2) -> {
            int cheers1 = getCheerCount(subject1.getSubjectId());
            int cheers2 = getCheerCount(subject2.getSubjectId());
            return Integer.compare(cheers2, cheers1);
        });
        return subjects;
    }

    private List<Subject> sortSubjectsByPrice(List<Subject> subjects) {
        subjects.sort((subject1, subject2) -> {
            int price1 = getPriceAsInteger(subject1);
            int price2 = getPriceAsInteger(subject2);
            return Integer.compare(price1, price2);
        });
        return subjects;
    }

    private int getPriceAsInteger(Subject subject) {
        String priceString = subject.getSubjectPrice();
        try {
            return Integer.parseInt(priceString);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing price for subject: " + subject.getSubjectName());
            return 0;
        }
    }




    private List<Subject> sortSubjectsByIndex(List<Subject> subjects) {
        subjects.sort((subject1, subject2) -> {
            int cheers1 = getCheerCount(subject1.getSubjectId());
            int cheers2 = getCheerCount(subject2.getSubjectId());
            int price1 = getPriceAsInteger(subject1);
            int price2 = getPriceAsInteger(subject2);
            float index1 = (float) cheers1 /price1;
            float index2 = (float) cheers2 /price2;
            return Float.compare(index2, index1);
        });
        return subjects;
    }


    private VBox createCourseCard(Subject subject) {
        VBox card = new VBox();
        card.getStyleClass().add("course-card");

        VBox contentContainer = new VBox();
        contentContainer.getStyleClass().add("content-container");

        Label nameLabel = new Label("Name: " + subject.getSubjectName());
        nameLabel.getStyleClass().add("name-label");

        HBox titlePriceBox = new HBox();
        titlePriceBox.getStyleClass().add("title-price-box");
        Label titleLabel = new Label("Description: " + subject.getDescription());
        Label priceLabel = new Label("Price: " + subject.getSubjectPrice() + "Dt");
        Label cheerCountLabel = new Label("Cheers: " + getCheerCount(subject.getSubjectId()));

        titlePriceBox.getChildren().addAll(titleLabel, priceLabel,cheerCountLabel);

        Button purchaseButton = new Button("Purchase Course");
        purchaseButton.getStyleClass().add("purchase-button");
        purchaseButton.setOnAction(event -> handlePurchaseButtonClick(subject));

        Button cheerButton = new Button("Cheer");
        cheerButton.getStyleClass().add("cheer-button");
        cheerButton.setOnAction(event -> handleCheerButtonClick(subject));


        HBox buttonContainer = new HBox(purchaseButton, cheerButton);
        buttonContainer.getStyleClass().add("button-container");

        contentContainer.getChildren().addAll(nameLabel, titlePriceBox, buttonContainer);
        card.getChildren().add(contentContainer);

        return card;
    }

    private void handleCheerButtonClick(Subject subject) {
        int subjectId = subject.getSubjectId();
        if (!cheeredSubjects.contains(subjectId)) {
            cheeredSubjects.add(subjectId);
            int currentCount = getCheerCount(subjectId);
            setCount(subjectId, currentCount + 1);
            updateCheerCountLabel(subjectId);
            updateUI();
            saveCheeredSubjects();
        } else {
            showAlert("You can only cheer once!");
        }
    }

    private void saveCheeredSubjects() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_ACTIONS_FILE))) {
            for (int subjectId : cheeredSubjects) {
                writer.write(String.valueOf(subjectId) + ":cheer");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCheerCount(int subjectId) {
        return cheerCounts.computeIfAbsent(subjectId, id -> new SimpleIntegerProperty(0)).get();
    }

    private void setCount(int subjectId, int count) {
        cheerCounts.computeIfPresent(subjectId, (id, currentCount) -> {
            currentCount.set(count);
            return currentCount;
        });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cheer_counts.txt", true))) {
            writer.write(subjectId + ":" + count + ":cheer");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCheerCountLabel(int subjectId) {
        for (Node node : coursesVBox.getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                int id = retrieveSubjectIdFromCard(card);
                if (id == subjectId) {
                    HBox cheerCountBox = retrieveCheerCountHBoxFromCard(card);
                    Label cheerCountLabel = (Label) cheerCountBox.getChildren().get(0);
                    cheerCountLabel.setText("Cheers: " + getCheerCount(subjectId));
                    break;
                }
            }
        }
    }

    private int retrieveSubjectIdFromCard(VBox card) {
        ObservableList<Node> cardChildren = card.getChildren();
        for (Node node : cardChildren) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String text = label.getText();
                if (text.startsWith("Name: ")) {
                    try {
                        return Integer.parseInt(text.substring("Name: ".length()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return 0;
    }

    private HBox retrieveCheerCountHBoxFromCard(VBox card) {
        ObservableList<Node> cardChildren = card.getChildren();
        for (Node node : cardChildren) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                if (hbox.getChildren().size() == 1 && hbox.getChildren().get(0) instanceof Label) {
                    Label label = (Label) hbox.getChildren().get(0);
                    if (label.getText().startsWith("Cheers: ")) {
                        return hbox;
                    }
                }
            }
        }
        return new HBox();
    }

    private void handleCardClick(int subjectId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewsessions.fxml"));
            Parent root = loader.load();

            ViewSessionsController sessionsController = loader.getController();
            sessionsController.setSubjectId(subjectId);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCheerCounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cheer_counts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[2].equals("cheer")) {
                    int subjectId = Integer.parseInt(parts[0]);
                    int count = Integer.parseInt(parts[1]);
                    cheerCounts.put(subjectId, new SimpleIntegerProperty(count));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePurchaseButtonClick(Subject subject) {
        try {
            if (paymentService.isSubjectPurchased(subject.getSubjectName())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Subject already purchased");
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentInterface.fxml"));
                Parent root = loader.load();

                PaymentInterfaceController paymentController = loader.getController();
                paymentController.setSubjectInfo(subject.getSubjectId(), subject.getSubjectName(), Integer.parseInt(subject.getSubjectPrice()));

                Stage stage = (Stage) sortByCheersRadioButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (SQLException | NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateUI() {
        coursesVBox.getChildren().clear();
        loadCourseCards();
    }
}