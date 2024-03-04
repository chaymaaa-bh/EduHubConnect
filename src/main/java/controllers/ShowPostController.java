package controllers;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import models.Post;
import services.PostService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowPostController {

    @FXML
    public TableView<Post> tableView;

    @FXML
    private TableColumn<Post, String> contentColumn;
    @FXML
    private ComboBox<String> sortPostsComboBox;

    @FXML
    private AnchorPane post_form;

    @FXML
    private Button filterByCommentsBtn; // Bouton pour filtrer par commentaires

    private final PostService postService = new PostService();
    // Set to track liked posts
    private Set<Integer> likedPosts = new HashSet<>();

    // Set to track disliked posts
    private Set<Integer> dislikedPosts = new HashSet<>();


    public void initialize() {
        // Initialize contentColumn and loadData()...
        contentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Post, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Post, String> cellData) {
                return cellData.getValue().contentProperty();
            }
        });
        loadData();

        // Populate the sorting options in the ComboBox
        ObservableList<String> sortingOptions = FXCollections.observableArrayList(
                "Alphabetical Order", "Content Length"
        );
        sortPostsComboBox.setItems(sortingOptions);
        sortPostsComboBox.setValue("Alphabetical Order");

        // Add listener to the ComboBox selection
        sortPostsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Content Length".equals(newValue)) {
                sortPostsByContentLength(); // Call sortPostsByContentLength() when "Content Length" is selected
            } else {
                sortPostsAlphabetically(); // Call sortPostsAlphabetically() for other options
            }
        });
    }


    public void loadData() {
        try {
            List<Post> posts = postService.getAllPosts();
            ObservableList<Post> postList = FXCollections.observableArrayList(posts);

            tableView.setItems(postList);

        } catch (SQLException e) {
            showAlert("Error fetching posts from the database: " + e.getMessage());
        }
    }

    @FXML
    void DeletePost(ActionEvent event) {
        // Get the selected post from the table
        Post selectedPost = tableView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            try {
                // Delete the post from the database
                postService.delete(selectedPost.getIdP());

                // Remove the post from the TableView
                tableView.getItems().remove(selectedPost);

                showAlert("Post deleted successfully!");
            } catch (SQLException e) {
                showAlert("Failed to delete post: " + e.getMessage());
            }
        } else {
            showAlert("Please select a post to delete.");
        }
    }


    @FXML
    void UpdateBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatepost.fxml"));
            Parent root = loader.load();
            UpdatePostController controller = loader.getController();
            // Get the selected post from the table
            Post selectedPost = tableView.getSelectionModel().getSelectedItem();
            if (selectedPost != null) {
                controller.setSelectedPost(selectedPost);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Post");
                stage.show();
            } else {
                showAlert("Please select a post to update.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void filterByComments(ActionEvent event) {
        ObservableList<Post> postList = null;
        try {
            List<Post> sortedPosts = postService.getPostsSortedByCommentCount(true); // true pour trier par ordre croissant
            postList = FXCollections.observableArrayList(sortedPosts);
            tableView.setItems(postList);
        } catch (SQLException e) {
            showAlert("Error filtering posts: " + e.getMessage());
        }
        tableView.setItems(postList);
    }

    @FXML
    void handleLikeButton(ActionEvent event) {
        Post selectedPost = tableView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            int postId = selectedPost.getIdP();
            if (!likedPosts.contains(postId)) {
                selectedPost.setLikes(selectedPost.getLikes() + 1);
                try {
                    postService.update(selectedPost);
                    showAlert("Liked!");
                    likedPosts.add(postId); // Add post ID to the set of liked posts
                } catch (SQLException e) {
                    showAlert("Failed to update like count: " + e.getMessage());
                }
            } else {
                showAlert("You have already liked this post.");
            }
        } else {
            showAlert("Please select a post to like.");
        }
    }

    @FXML
    void handleDislikeButton(ActionEvent event) {
        Post selectedPost = tableView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            int postId = selectedPost.getIdP();
            if (!dislikedPosts.contains(postId)) {
                selectedPost.setDislikes(selectedPost.getDislikes() + 1);
                try {
                    postService.update(selectedPost);
                    showAlert("Disliked!");
                    dislikedPosts.add(postId); // Add post ID to the set of disliked posts
                } catch (SQLException e) {
                    showAlert("Failed to update dislike count: " + e.getMessage());
                }
            } else {
                showAlert("You have already disliked this post.");
            }
        } else {
            showAlert("Please select a post to dislike.");
        }
    }

    private void sortPostsAlphabetically() {
        try {
            List<Post> sortedPosts = postService.getPostsSortedAlphabetically();
            updateTableView(sortedPosts);
        } catch (SQLException e) {
            showAlert("Error sorting posts: " + e.getMessage());
        }
    }



    private void sortPostsByContentLength() {
        try {
            List<Post> sortedPosts = postService.getPostsSortedByContentLength();
            updateTableView(sortedPosts);
        } catch (SQLException e) {
            showAlert("Error sorting posts: " + e.getMessage());
        }
    }



    private void updateTableView(List<Post> posts) {
        ObservableList<Post> postList = FXCollections.observableArrayList(posts);
        tableView.setItems(postList);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void openPostAdd(ActionEvent actionEvent) {

        switchScene(actionEvent, "/addpost.fxml");
    }

    @FXML
    void close(ActionEvent event) {
        switchScene(event, "/student.fxml");
    }

    @FXML
    void minimize(ActionEvent event) {
        minimize();
    }



    public void minimize() {
        Stage stage = (Stage)this.post_form.getScene().getWindow();
        stage.setIconified(true);
    }
}
