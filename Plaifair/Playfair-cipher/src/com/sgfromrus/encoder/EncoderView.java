package com.sgfromrus.encoder;

import com.sgfromrus.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by sgfromrus on 09.2017
 */
public class EncoderView extends JFrame {
    private JTextArea textArea;
    private JLabel lblPlainText;
    private Font fontLbl;
    private JLabel lblSecureText;
    private JTextArea textAreaSecret;
    private Font fontTextArea;
    private JButton btnEncode;
    private JButton saveKeys;
    private JButton showKeys;
    private JButton generateKey;
    private File fileKeys;
    private JTextField secretWordField;
    private JLabel lblSecretWord;
    private Program program = new Program();

    public EncoderView(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocation(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(null);

        generateKey = new JButton("Generate key");
        textArea = new JTextArea();
        textAreaSecret = new JTextArea();
        btnEncode = new JButton("Encode");
        saveKeys = new JButton("Save key");
        showKeys = new JButton("Show key");
        lblPlainText = new JLabel("Plain text");
        lblSecureText = new JLabel("Secret text");
        fontLbl = new Font("Arial Black", Font.PLAIN, 25);
        fontTextArea = new Font("Times New Roman", Font.PLAIN, 20);
        secretWordField = new JTextField("test secret word");
        lblSecretWord = new JLabel("Secret word");

        lblSecureText.setBounds(165, 190, 225, 35);
        lblSecureText.setFont(fontLbl);

        lblPlainText.setBounds(170, 45, 200, 35);
        lblPlainText.setFont(fontLbl);


        secretWordField.setBounds(300, 10, 160, 30);
        secretWordField.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblSecretWord.setBounds(180, 13, 150, 20);
        lblSecretWord.setFont(new Font("Arial Black", Font.PLAIN, 16));

        textAreaSecret.setFont(fontTextArea);
        textAreaSecret.setBorder(BorderFactory.createLineBorder(Color.black));
        textAreaSecret.setBounds(15, 230, 450, 100);

        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        textArea.setFont(fontTextArea);
        textArea.setBounds(15, 80, 450, 100);

        generateKey.setBounds(15, 330, 112, 50);
        saveKeys.setBounds(127, 330, 112, 50);
        showKeys.setBounds(239, 330, 112, 50);
        btnEncode.setBounds(351, 330, 114, 50);

        btnClicks();

        getContentPane().add(showKeys);
        getContentPane().add(saveKeys);
        getContentPane().add(btnEncode);
        getContentPane().add(lblSecureText);
        getContentPane().add(textArea);
        getContentPane().add(lblPlainText);
        getContentPane().add(textAreaSecret);
        getContentPane().add(generateKey);
        getContentPane().add(secretWordField);
        getContentPane().add(lblSecretWord);
    }

    private void btnClicks() {
        generateKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                program.generateKeys(secretWordField.getText());
                //encoder.createKeys();
            }
        });

        btnEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plainText = textArea.getText();

                if (plainText.length() != 0) {

                    //String secretText = encoder.encode(plainText);
                    String secretText = program.encode(plainText);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            textAreaSecret.setText("");
                            textAreaSecret.append(secretText);
                        }
                    });
                }
            }
        });

        saveKeys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSave = new JFileChooser();
                int ret =   fileSave.showDialog(null, "Save keys");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    fileKeys = fileSave.getSelectedFile();
                    program.saveKeyToFile(fileKeys);
                    //encoder.saveKeyToFile(fileKeys);
                }

            }
        });

        showKeys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileKeys != null) {
                    new ShowKeyView( fileKeys);
                }

            }
        });
    }
}
