package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // load the fxml file
<<<<<<< HEAD
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home_quiz.fxml"));
=======
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
>>>>>>> origin/chayma
        // load fxml code in a sceen
        Parent root= loader.load();
        // put the fxml file in a sceene
        Scene scene = new Scene(root);
        // set a scene in stage
        stage.setScene(scene);
        stage.setTitle("add user form");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
<<<<<<< HEAD
}
=======
}

>>>>>>> origin/chayma
