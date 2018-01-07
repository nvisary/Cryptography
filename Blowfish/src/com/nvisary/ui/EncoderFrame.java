package com.nvisary.ui;

import com.nvisary.blowfish.Blowfish;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;

/**
 * Created by sgfromrus on 11.2017
 */
public class EncoderFrame extends Application {
    @FXML
    public TextField textFieldKey;
    @FXML
    public Button btnLoadInFile;
    @FXML
    public Button btnEncode;
    @FXML
    public Button btnLoadOutFile;
    @FXML
    public CheckBox chBoxGenerate;
    @FXML
    public Label lblInFileName;
    @FXML
    public Label lblOutFileName;

    private Blowfish encoder = new Blowfish();
    private FileChooser fileChooser = new FileChooser();
    private Stage primaryStage;
    private String inFileName;
    private String outFileName;

    public void btnClick(ActionEvent actionEvent) throws IOException {
        String key = textFieldKey.getText();
        if (chBoxGenerate.isSelected()) {
            key = encoder.generateKey();
        }
        if (inFileName != null && outFileName != null) {
            fileChooser.setTitle("Save key to..");
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                encoder.saveKeyToFile(file.getPath(), key);
            }
            encoder.encodeFile(inFileName, outFileName, key);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Encoder");
        this.primaryStage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EncoderFrame.class.getResource("EncoderFrame.fxml"));
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

    public void btnInFileClick(ActionEvent actionEvent) {
        fileChooser.setTitle("File for encode");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            inFileName = file.getPath();
            lblInFileName.setText(inFileName);
        }
    }

    public void btnOutFileClick(ActionEvent actionEvent) {
        fileChooser.setTitle("Name result file");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            outFileName = file.getPath();
            lblOutFileName.setText(outFileName);
        }
    }
}
