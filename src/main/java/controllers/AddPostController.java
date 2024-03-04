package controllers;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import models.Post;
import services.BadWordsFilter;
import services.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPostController {

    @FXML
    private Button addPostBtn;

    @FXML
    private TextField contentp;

    @FXML
    private AnchorPane add_post_form;

    private final PostService postService = new PostService();
    private static final Logger logger = Logger.getLogger(AddPostController.class.getName());

    @FXML
    void postAdd(ActionEvent event) {
        String content = contentp.getText().trim();

        // Check if the content contains bad words
        if (BadWordsFilter.containsBadWord(content)) {
            showAlert("Content contains inappropriate language!");
            return;
        }

        // Validate the content
        if (content.isEmpty()) {
            showAlert("Content cannot be empty!");
            return;
        }

        // Create a new Post object
        Post newPost = new Post(content, 0, 0);

        // Use PostService to add the new post to the database
        try {
            postService.create(newPost);
            showAlert("Post added successfully!");

            // Load the showpost.fxml interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showpost.fxml"));
            Parent root = loader.load();
            ShowPostController controller = loader.getController();
            // Pass any necessary data to the ShowPostController if needed

            // Switch scenes
            Stage stage = (Stage) addPostBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            showAlert("Failed to add post: " + e.getMessage());
            logger.log(Level.SEVERE, "Failed to add post", e);
        }
    }

    // Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void close(ActionEvent event) {
        switchScene(event, "/showpost.fxml");
    }

    @FXML
    void minimize(ActionEvent event) {
minimize();
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


    public void minimize() {
        Stage stage = (Stage)this.add_post_form.getScene().getWindow();
        stage.setIconified(true);
    }
}
