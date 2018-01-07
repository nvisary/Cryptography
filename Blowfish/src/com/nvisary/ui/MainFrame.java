package com.nvisary.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by sgfromrus on 11.2017
 */
public class MainFrame extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Encoder");
        this.primaryStage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFrame.class.getResource("MainFrame.fxml"));
            Pane pane = (Pane) loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void btnEncoderClick(ActionEvent actionEvent) throws Exception {
        EncoderFrame encoderFrame = new EncoderFrame();
        encoderFrame.start(new Stage());
    }

    public void btnDecoderClick(ActionEvent actionEvent) throws Exception {
        DecoderFrame decoderFrame = new DecoderFrame();
        decoderFrame.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
