package com.nvisary;

import com.nvisary.blowfish.Blowfish;
import com.nvisary.ui.MainFrame;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Run {
    public static void main(String[] args) {
        try {
            if (args.length > 4) {
                throw new IOException("Ошибка в количестве аргументов");
            } else if (args.length == 0 || args[0].equals("-ui")) {
                Application.launch(MainFrame.class, args);
            } else if (args[0].equals("-e")) {
                encoderRun(args);
            } else if (args[0].equals("-d")) {
                decodeRun(args);
            }
        } catch (IOException ex) {
            String message = ex.getMessage();
            if (!message.equals("Ошибка в количестве аргументов")) {
                message += " не найден!";
            }
            System.out.println("Ошибка в переданных аргументах!\n" + message );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encoderRun(String[] args) throws IOException {
        String inFile = args[1];
        String outFile = args[2];
        String key;
        Blowfish encoder = new Blowfish();
        if (args.length == 4) {
            key = args[3];
        } else
            key = encoder.generateKey();
        encoder.saveKeyToFile("key", key);
        encoder.encodeFile(inFile, outFile, key);
    }

    private static void decodeRun(String[] args) throws IOException {
        String inFile = args[1];
        String outFile = args[2];
        String key;
        Blowfish decoder = new Blowfish();
        if (args.length == 3) {
            key = decoder.loadKeyFromFile("key");
        } else
            key = args[3];

        decoder.decodeFile(inFile, outFile, key);
    }
}
