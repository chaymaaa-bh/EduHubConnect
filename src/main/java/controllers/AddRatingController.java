package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.controlsfx.control.Rating;
import services.RatingService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AddRatingController {

    @FXML
    private Rating Ratingvalue;

    @FXML
    private Button cancelbtn;

    private RatingService ratingService;

    public AddRatingController() {
        ratingService = new RatingService();
    }

    @FXML
    private void initialize() {
        // You can initialize anything here if needed
    }

    @FXML
    private void submitrating() {
        // This method will be called when the "Submit" button is clicked
        try {
            int ratingValue = (int) Ratingvalue.getRating(); // Cast to int since ratingValue is an int
            // Assuming ratedCourse and ratingUser are obtained from somewhere
            int ratedCourse = 1; // Example ratedCourse
            int ratingUser = 1; // Example ratingUser
            Timestamp ratingDate = Timestamp.valueOf(LocalDateTime.now());

            // Create a new Rating object
            models.Rating rating = new models.Rating(ratingValue, ratedCourse, ratingUser, ratingDate);

            // Call the RatingService to create the rating
            ratingService.create(rating);

            // Optionally, you can add further actions after rating submission, such as closing the window
            // For example: ((Stage) Ratingvalue.getScene().getWindow()).close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately, such as showing an error message
        }
    }


    @FXML
    private void cancelAction() {
        // This method will be called when the "Cancel" button is clicked
        // Add your logic here to handle cancellation, if needed
    }
}
