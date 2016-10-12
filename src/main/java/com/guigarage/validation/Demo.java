package com.guigarage.validation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(FormController1.class.getResource("form.fxml"));
        loader.setController(new FormController1());

        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Demo.class.getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
