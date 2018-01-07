/*
package com.sgfromrus.decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

*/
/**
 * Created by sgfromrus on 09.2017
 *//*

public class Decoder {
    private char massLeftUp[] = new char[169];
    private char massLeftDown[] = new char[169];
    private char massRightUp[] = new char[169];
    private char massRightDown[] = new char[169];

    public Decoder() {
        int count = 32; //english labels
        char k = '\u0410';
        char k2 = '\u2460';
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (count <= 127) {
                    massLeftUp[(i) * 13 + j] = (char) count;
                    massRightDown[(i) * 13 + j] = (char) count;
                    massRightUp[(i) * 13 + j] = (char) count;
                    massLeftDown[(i) * 13 + j] = (char) count;
                } else {
                    if (k <= '\u044f') {
                        massLeftUp[(i) * 13 + j] = k;   //russian labels
                        massRightDown[(i) * 13 + j] = k;
                        massRightUp[(i) * 13 + j] = k;
                        massLeftDown[(i) * 13 + j] = k;
                        k++;
                    } else {
                        massLeftUp[(i) * 13 + j] = k2;   //другие символы
                        massRightDown[(i) * 13 + j] = k2;
                        massRightUp[(i) * 13 + j] = k2;
                        massLeftDown[(i) * 13 + j] = k2;
                        k2++;
                    }
                }
                count++;
            }
        }

    }

    public void loadKeyFromFile(File file) {
        try {
            Scanner in = new Scanner(file);
            int i = 0;
            boolean first = true;
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.length() == 13) {
                    for (int j = 0; j < 13; j++) {
                        if (first) {
                            massRightUp[i * 13 + j] = line.charAt(j);
                        } else {
                            massLeftDown[i * 13 + j] = line.charAt(j);
                        }
                    }
                    i++;
                    if (i == 13) {
                        i = 0;
                        first = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String decode(String secretText) {
        boolean isEven = false;
        if (secretText.length() % 2 != 0) {
            isEven = true;
            secretText += " ";
        }

        StringBuilder plainText = new StringBuilder();

        char ch1;
        char ch2;
        int row1 = 0;
        int column1 = 0;
        int row2 = 0;
        int column2 = 0;
        for (int i = 0; i < secretText.length(); i += 2) {
            ch1 = secretText.charAt(i);
            ch2 = secretText.charAt(i + 1);
            for (int k = 0; k < 13; k++) {
                for (int m = 0; m < 13; m++) {
                    if (massRightUp[k*13 + m] == ch1) {
                        row1 = k;
                        column1 = m;
                    }
                    if (massLeftDown[k*13 + m] == ch2) {
                        row2 = k;
                        column2 = m;
                    }
                }
            }
            plainText.append(massLeftUp[row1*13 + column2]);
            plainText.append(massRightDown[row2*13 + column1]);
        }


        return plainText.toString();
    }
}
*/
