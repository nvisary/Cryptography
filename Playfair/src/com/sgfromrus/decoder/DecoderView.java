package com.sgfromrus.decoder;

import com.sgfromrus.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by sgfromrus on 09.2017
 */
public class DecoderView extends JFrame {
    private JTextArea textAreaSecret;
    private JLabel lblSecretText;
    private Font fontLbl;
    private JLabel lblPlainText;
    private JTextArea textAreaPlain;
    private Font fontTextArea;
    private JButton btnOpenKeys;
    private JButton btnDecode;
    private JTextField secretWordField;
    private JLabel lblSecretWord;
    private Program program = new Program();

    public DecoderView(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocation(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(null);

        lblSecretText = new JLabel("Secret text");
        lblPlainText = new JLabel("Plain text");
        textAreaPlain = new JTextArea();
        textAreaSecret = new JTextArea();
        fontLbl = new Font("Arial Black", Font.PLAIN, 25);
        fontTextArea = new Font("Times New Roman", Font.PLAIN, 20);
        btnDecode = new JButton("Decode");
        btnOpenKeys = new JButton("Open keys");
        secretWordField = new JTextField();
        lblSecretWord = new JLabel("Secret word");

        lblSecretText.setBounds(170, 45, 200, 35);
        lblSecretText.setFont(fontLbl);

        secretWordField.setBounds(300, 10, 160, 30);
        secretWordField.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblSecretWord.setBounds(180, 13, 150, 20);
        lblSecretWord.setFont(new Font("Arial Black", Font.PLAIN, 16));

        lblPlainText.setBounds(165, 190, 225, 35);
        lblPlainText.setFont(fontLbl);

        btnOpenKeys.setBounds(15, 330, 225, 50);
        btnDecode.setBounds(240, 330, 225, 50);

        textAreaSecret.setBounds(15, 80, 450, 100);
        textAreaSecret.setBorder(BorderFactory.createLineBorder(Color.black));
        textAreaSecret.setFont(fontTextArea);

        textAreaPlain.setBounds(15, 230, 450, 100);
        textAreaPlain.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textAreaPlain.setFont(fontTextArea);

        btnClicks();
        getContentPane().add(lblSecretText);
        getContentPane().add(textAreaSecret);
        getContentPane().add(lblPlainText);
        getContentPane().add(textAreaPlain);
        getContentPane().add(btnDecode);
        getContentPane().add(btnOpenKeys);
        getContentPane().add(lblSecretWord);
        getContentPane().add(secretWordField);

    }

    private void btnClicks() {
        btnDecode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = secretWordField.getText();
                program.generateKeys(key);
                String decodeStr = program.decode(textAreaSecret.getText());
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textAreaPlain.setText(decodeStr);
                    }
                });
            }
        });

        btnOpenKeys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileLoad = new JFileChooser();
                int ret = fileLoad.showDialog(null, "Load keys");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File fileKeys = fileLoad.getSelectedFile();
                    //decoder.loadKeyFromFile(fileKeys);
                    program.loadKeyFromFile(fileKeys);
                }
            }
        });
    }
}
