package com.sgfromrus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by sgfromrus on 09.2017
 */
public class Program {
    private int N = 13;
    private Random rnd;
    private char massLeftUp[] = new char[N*N];
    private char massLeftDown[] = new char[N*N];
    private char massRightUp[] = new char[N*N];
    private char massRightDown[] = new char[N*N];
    private String lastSecretKey = "";
    public Program() {
        int count = 32; //english labels
        char k = '\u0410';
        char k2 = '\u01f0';
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (count <= 127) {
                    massLeftUp[(i)*N + j] = (char) count;
                    massRightDown[(i)*N + j] = (char) count;
                    massRightUp[(i)*N + j] = (char) count;
                    massLeftDown[(i)*N + j] = (char) count;
                } else {
                    if (k <= '\u044f') {
                        massLeftUp[(i)*N + j] = k;   //russian labels
                        massRightDown[(i)*N + j] = k;
                        massRightUp[(i)*N + j] = k;
                        massLeftDown[(i)*N + j] = k;
                        k++;
                    } else {
                        massLeftUp[(i)*N + j] = k2;
                        massRightDown[(i)*N + j] = k2;
                        massRightUp[(i)*N + j] = k2;
                        massLeftDown[(i)*N + j] = k2;
                        k2++;
                    }
                }
                count++;
            }
        }
    }
    public void saveKeyToFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    writer.write(massRightUp[i * N + j]);
                }
                writer.write("\n");
            }
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    writer.write(massLeftDown[i * N + j]);
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    public void generateKeys(String secretKey) {
        if (!secretKey.equals(lastSecretKey)) {
            int seed = 0;
            for (int i = 0; i < secretKey.length(); i++) {
                seed += secretKey.charAt(i);
            }
            rnd = new Random(seed);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    massLeftDown[i * N + j] = massLeftUp[i * N + j];
                    massRightUp[i * N + j] = massLeftUp[i * N + j];
                }
            }
            for (int i = N * N - 1; i >= 1; i--) {
                int j1 = rnd.nextInt(N * N);
                int j2 = rnd.nextInt(N * N);

                char temp1 = massLeftDown[j1];
                massLeftDown[j1] = massLeftDown[i];
                massLeftDown[i] = temp1;

                char temp2 = massRightUp[j2];
                massRightUp[j2] = massRightUp[i];
                massRightUp[i] = temp2;

            }
            lastSecretKey = secretKey;
        }
    }
    public char[] getKey1() {
        return massRightUp;
    }
    public char[] getKey2() {
        return massLeftDown;
    }
    public void loadKeyFromFile(File file) {
        try {
            Scanner in = new Scanner(file);
            int i = 0;
            boolean first = true;
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.length() == N) {
                    for (int j = 0; j < N; j++) {
                        if (first) {
                            massRightUp[i * N + j] = line.charAt(j);
                        } else {
                            massLeftDown[i * N + j] = line.charAt(j);
                        }
                    }
                    i++;
                    if (i == N) {
                        i = 0;
                        first = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private String code(String text, boolean encode) {
        if (text.length() % 2 != 0) {
            text += " ";
        }

        StringBuilder newText = new StringBuilder();
        char ch1;
        char ch2;
        int row1 = 0;
        int row2 = 0;
        int column1 = 0;
        int column2 = 0;
        for (int i = 0; i < text.length(); i += 2) {
            ch1 = text.charAt(i);
            ch2 = text.charAt(i + 1);
            for (int k = 0; k < N; k++) {
                for (int m = 0; m < N; m++) {
                    if (massLeftUp[k * N + m] == ch1 && encode) {
                        row1 = k;
                        column1 = m;
                    }
                    if (massRightDown[k * N + m] == ch2 && encode) {
                        row2 = k;
                        column2 = m;
                    }
                    if (massRightUp[k * N + m] == ch1 && !encode) {
                        row1 = k;
                        column1 = m;
                    }
                    if (massLeftDown[k * N + m] == ch2 && !encode) {
                        row2 = k;
                        column2 = m;
                    }
                }
            }
            if (encode){
                newText.append(massRightUp[row1 * N + column2]);
                newText.append(massLeftDown[row2 * N + column1]);
            } else {
                newText.append(massLeftUp[row1 * N + column2]);
                newText.append(massRightDown[row2 * N + column1]);
            }

        }

        return newText.toString();
    }
    public String encode(String plainText) {
        return code(plainText, true);
    }


    public String decode(String text) {
        return code(text, false);
    }
}
