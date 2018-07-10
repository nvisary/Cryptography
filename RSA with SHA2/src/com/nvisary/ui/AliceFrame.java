package com.nvisary.ui;

import com.nvisary.PrivateKey;
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

public class AliceFrame extends Application {


    public TextField tf_public_e;
    public TextField tf_public_n;
    public TextField tf_private_d;
    public TextField tf_private_n;
    public TextArea tf_decoded_msg;
    public TextField tf_password;

    private Stage primaryStage;

    private RSA rsa = new RSA();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Alice");
        this.primaryStage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AliceFrame.class.getResource("AliceFrame.fxml"));
            Pane pane = (Pane) loader.load();
            Scene scene = new Scene(pane);

            this.primaryStage.setScene(scene);
            this.primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void btnGenerateKeysClick() {
        String password = tf_password.getText();
        if (password.length() > 0) {
            rsa.generateKeys(128, password);
        } else {
            rsa.generateKeys(128);
        }
        PublicKey publicKey = rsa.getPublicKey();
        PrivateKey privateKey = rsa.getPrivateKey();
        tf_public_e.setText(publicKey.getE().toString());
        tf_public_n.setText(publicKey.getN().toString());

        tf_private_n.setText(privateKey.getN().toString());
        tf_private_d.setText(privateKey.getD().toString());
    }

    public void btnDecodeClick() {
        String str = tf_decoded_msg.getText();
        BigInteger b = rsa.decode(new BigInteger(str));
        byte[] arr = b.toByteArray();
        tf_decoded_msg.setText(new String(arr));
    }
}
