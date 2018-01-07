package com.nvisary.ui;

import com.nvisary.blowfish.Blowfish;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by sgfromrus on 11.2017
 */
public class DecoderFrame extends Application {
    @FXML
    public TextField textFieldKey;
    @FXML
    public Button btnOpenInFile;
    @FXML
    public Button btnOpenOutFile;
    @FXML
    public Button btnOpenKeyFile;
    @FXML
    public Button btnDecode;
    @FXML
    public Label lbl1;
    @FXML
    public Label lbl2;
    @FXML
    public Label lbl3;

    private Stage primaryStage;
    private FileChooser fileChooser = new FileChooser();
    private Blowfish decoder = new Blowfish();
    private String keyFileName;
    private String inFileName;
    private String outFileName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Decoder");
        this.primaryStage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EncoderFrame.class.getResource("DecoderFrame.fxml"));
            Pane pane = (Pane) loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void btnDecodeClick(ActionEvent actionEvent) throws IOException {
        String key;
        if (keyFileName == null) {
            key = textFieldKey.getText();
        } else {
            key = decoder.loadKeyFromFile(keyFileName);
        }

        if (inFileName != null && outFileName != null) {
            decoder.decodeFile(inFileName, outFileName, key);
        }
    }

    public void btnOpenInFileClick(ActionEvent actionEvent) {
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            inFileName = file.getPath();
            lbl1.setText(inFileName);
        }
    }

    public void btnSaveOutClick(ActionEvent actionEvent) {
        fileChooser.setTitle("Save result..");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            outFileName = file.getPath();
            lbl2.setText(outFileName);
        }
    }

    public void btnOpenKeyClick(ActionEvent actionEvent) {
        fileChooser.setTitle("Open key");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            keyFileName = file.getPath();
            lbl3.setText(keyFileName);
        }
    }
}
