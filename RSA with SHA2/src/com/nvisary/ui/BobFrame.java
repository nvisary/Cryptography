package com.nvisary.ui;

import com.nvisary.PublicKey;
import com.nvisary.RSA;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;

public class BobFrame extends Application {
    public TextField tf_public_e;
    public TextField tf_public_n;
    public TextArea ta_plain_text;
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Bob");
        this.primaryStage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AliceFrame.class.getResource("BobFrame.fxml"));
            Pane pane = (Pane) loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void btnEncodeClick() {
        PublicKey publicKey = new PublicKey(new BigInteger(tf_public_e.getText()), new BigInteger(tf_public_n.getText()));
        RSA rsa = new RSA();
        rsa.setPublicKey(publicKey);
        BigInteger res = rsa.encode(new BigInteger(ta_plain_text.getText().getBytes()));
        ta_plain_text.setText(res.toString());
    }
}
