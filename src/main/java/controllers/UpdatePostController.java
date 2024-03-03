package controllers;

import models.Post;
import services.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UpdatePostController {

    @FXML
    private TextArea postContentTextArea;

    private Post selectedPost;

    public void setSelectedPost(Post post) {
        this.selectedPost = post;
        postContentTextArea.setText(post.getContent());
    }

    @FXML
    void updatePost(ActionEvent event) {
        String updatedContent = postContentTextArea.getText();
        selectedPost.setContent(updatedContent);

        PostService postService = new PostService();
        try {
            postService.update(selectedPost);
            showAlert("Post updated successfully!");
            closeWindow();
        } catch (SQLException e) {
            showAlert("Failed to update post: " + e.getMessage());
        }
    }

    @FXML
    void handleLikeButton(ActionEvent event) {
        // Logic to handle the "Like" action
        selectedPost.setLikes(selectedPost.getLikes() + 1);
    }

    @FXML
    void handleDislikeButton(ActionEvent event) {
        // Logic to handle the "Dislike" action
        selectedPost.setDislikes(selectedPost.getDislikes() + 1);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) postContentTextArea.getScene().getWindow();
        stage.close();
    }

    public void openUpdatePostWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updatepost.fxml"));
            Parent root = loader.load();
            UpdatePostController controller = loader.getController();
            controller.setSelectedPost(selectedPost);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Post");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
