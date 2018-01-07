package com.sgfromrus;

import com.sgfromrus.decoder.DecoderView;
import com.sgfromrus.encoder.EncoderView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JButton btnStartEncoder;
    private JButton btnStartDecoder;
    public Main(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setResizable(false);
        setLocation(800, 400);

        Font btnFont = new Font("Times New Roman", Font.BOLD, 35);
        btnStartDecoder = new JButton("Decoder");
        btnStartEncoder = new JButton("Encoder");
        btnStartEncoder.setFont(btnFont);
        btnStartDecoder.setFont(btnFont);

        getContentPane().add(btnStartEncoder);
        getContentPane().add(btnStartDecoder);
        btnStartEncoder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EncoderView("Encoder",500, 450);
            }
        });
        btnStartDecoder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DecoderView("Decoder", 500, 450);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main("Лаба 1" ,250, 300);
    }
}
