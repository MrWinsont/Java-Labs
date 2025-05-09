package com.test.bandViewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BandViewer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("BandInfoView.fxml"));
        primaryStage.setTitle("Band Viewer");
        primaryStage.setScene(new Scene(root, 1100, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}