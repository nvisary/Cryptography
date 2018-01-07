/*
package com.sgfromrus.encoder;

import com.sgfromrus.decoder.Decoder;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Encoder {
    private char massLeftUp[] = new char[169];
    private char massLeftDown[] = new char[169];
    private char massRightUp[] = new char[169];
    private char massRightDown[] = new char[169];

    private Random rnd;
    public Encoder() {
        int count = 32; //english labels
        char k = '\u0410';
        char k2 = '\u01f0';
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (count <= 127) {
                    massLeftUp[(i)*13 + j] = (char) count;
                    massRightDown[(i)*13 + j] = (char) count;
                    massRightUp[(i)*13 + j] = (char) count;
                    massLeftDown[(i)*13 + j] = (char) count;
                } else {
                    if (k <= '\u044f') {
                        massLeftUp[(i)*13+ j] = k;   //russian labels
                        massRightDown[(i)*13+ j] = k;
                        massRightUp[(i)*13 + j] = k;
                        massLeftDown[(i)*13 + j] = k;
                        k++;
                    } else {
                        massLeftUp[(i)*13+ j] = k2;
                        massRightDown[(i)*13+ j] = k2;
                        massRightUp[(i)*13 + j] = k2;
                        massLeftDown[(i)*13 + j] = k2;
                        k2++;
                    }
                }
                count++;
            }
        }
        //generateRandomMass();

        count = 0;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(count + " " + massLeftDown[(i)*13+ j]);
                //System.out.print(count + " " + massRightUp[(i)*13+ j]);
                count++;
            }
            System.out.println();
        }

    }
    private void generateRandomMass() {
        rnd = new Random();
        for (int i = 168; i >= 1; i--) {
            int j1 = rnd.nextInt(169);
            int j2 = rnd.nextInt(169);

            char temp1 = massLeftDown[j1];
            massLeftDown[j1] = massLeftDown[i];
            massLeftDown[i] = temp1;

            char temp2 = massRightUp[j2];
            massRightUp[j2] = massRightUp[i];
            massRightUp[i] = temp2;

        }
    }

    public void createKeys() {
        generateRandomMass();
    }

    public String encode(String plainText) {
        if (plainText.length() % 2 != 0) {
            plainText += " ";
        }
        StringBuilder secretText = new StringBuilder();
        char ch1;
        char ch2;
        char sch1;
        char sch2;
        int row1 = 0;
        int row2 = 0;
        int column1 = 0;
        int column2 = 0;
        for (int i = 0; i < plainText.length(); i += 2) {
            ch1 = plainText.charAt(i);
            ch2 = plainText.charAt(i + 1);
            for (int k = 0; k < 13; k++) {
                for (int m = 0; m < 13; m++) {
                   if (massLeftUp[k*13 + m] == ch1) {
                     row1 = k;
                     column1 = m;
                   }
                   if (massRightDown[k*13 + m] == ch2) {
                       row2 = k;
                       column2 = m;
                   }
                }
            }
            sch1 = massRightUp[row1*13 + column2];
            sch2 = massLeftDown[row2*13 + column1];
            secretText.append(sch1);
            secretText.append(sch2);
        }

        return secretText.toString();
    }
    public void saveKeyToFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    writer.write(massRightUp[i * 13 + j]);
                }
                writer.write("\n");
            }
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    writer.write(massLeftDown[i * 13 + j]);
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



FileWriter fileWriter ;
        FileWriter fileWriter1 ;
        try {
            fileWriter = new FileWriter("key1.txt");
            fileWriter1 = new FileWriter("key2.txt");

            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    fileWriter.write(massRightUp[i * 13 + j] + " ");
                    fileWriter1.write(massLeftDown[i * 13 + j] + " ");
                }
                fileWriter.write("\n");
                fileWriter1.write("\n");
            }

            fileWriter.close();
            fileWriter1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public char[] getKey1() {
        return massRightUp;
    }

    public char[] getKey2() {
        return massLeftDown;
    }
    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        encoder.createKeys();
        encoder.saveKeyToFile(new File("key1.txt"));

        decoder.loadKeyFromFile(new File("key.txt"));

        String plainText = "!F";
        String secretText = encoder.encode(plainText);
        System.out.println(secretText);
        String plainText2 = decoder.decode(secretText);
        System.out.println(plainText2 + "\n" + plainText.equals(plainText2));
    }
}
*/
