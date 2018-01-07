package com.nvisary.blowfish;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class Blowfish {
    private int[] P;
    private int[][] S;

    public Blowfish() {
        Constants constants = new Constants();
        P = constants.getP();
        S = constants.getS();
    }

    private int F(int x) {
        int s4 = x & 0x00FF;
        x = x >>> 8;
        int s3 = x & 0x00FF;
        x = x >>> 8;
        int s2 = x & 0x00FF;
        x = x >>> 8;
        int s1 = x & 0x00FF;
        int y = ((S[0][s1] + S[1][s2]) ^ S[2][s3]) + S[3][s4];
        return y;
    }

    private int[] encrypt(int l, int r) {
        int temp;
        for (byte i = 0; i < 16; i++) {
            l ^= P[i];
            r ^= F(l);

            temp = l;
            l = r;
            r = temp;
        }

        temp = l;
        l = r;
        r = temp;

        r = r ^ P[16];
        l = l ^ P[17];

        int[] y = {l, r};

        return y;
    }


    private int[] decrypt(int l, int r) {
        int temp;
        for (byte i = 17; i > 1; i--) {
            l = l ^ P[i];
            r = r ^ F(l);

            temp = l;
            l = r;
            r = temp;
        }

        temp = l;
        l = r;
        r = temp;

        r = r ^ P[1];
        l = l ^ P[0];

        int[] y = {l, r};
        return y;
    }

    private byte[] loadByteArrayFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] result = Files.readAllBytes(file.toPath());
        return result;
    }

    private void saveByteArrayToFile(byte[] arr, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(arr);
        fos.close();
    }

    private void blowfishInit(byte[] key) {
        int longKey = 0;
        for (int i = 0; i < 18; i++) {
            for (int j = 0, k = 0; j < 4; j++, k++) {
                longKey = (longKey << 8) | key[k % key.length];
            }
            P[i] = P[i] ^ longKey;
        }

        int[] y = {0, 0};
        for (int i = 0; i < 18; i += 2) {
            y = encrypt(y[0], y[1]);
            P[i] = y[0];
            P[i + 1] = y[1];
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 256; j++) {
                y = encrypt(y[0], y[1]);
                S[i][j] = y[0];
                S[i][++j] = y[1];
            }
        }

    }

    private byte[] getFourByte(byte[] text, int ind) {
        byte[] result = new byte[4];
        if (ind + 4 < text.length) {
            for (int i = 0; i < 4; i++) {
                result[i] = text[ind + i];
            }
        } else {
            int p = text.length - ind;
            for (int i = 0; i < p; i++) {
                result[i] = text[ind + i];
            }
        }
        return result;
    }

    private void setFourByte(byte[] text, int ind, byte[] fourByte) {
        for (int i = 0; i < 4; i++) {
            if (ind + i >= text.length) {
                break;
            }
            text[ind + i] = fourByte[i];
        }
    }

    private byte[] getByteArray(int a) {
        byte[] result = new byte[4];
        if (a == 0) {
            return result;
        }
        result[0] = (byte) (a >> 24);
        result[1] = (byte) ((a << 8) >> 24);
        result[2] = (byte) ((a << 8) >> 16);
        result[3] = (byte) (a & 0xFF);
        return result;
    }

    private int getInteger(byte[] arr) {
        int result = ((arr[0] & 0xFF) << 24) + ((arr[1] & 0xFF) << 16) + ((arr[2] & 0xFF) << 8) + (arr[3] & 0xFF);
        return result;
    }

    public byte[] encode(String key, byte[] text) {
        blowfishInit(Integer.toString(key.hashCode()).getBytes());
        int textLen = text.length;
        int blockCount = (textLen % 8 != 0) ? (textLen / 8 + 1) : (textLen / 8);
        int p = blockCount * 8 - textLen;

        byte[] result = new byte[textLen + p + 1];
        int count = 0;
        for (int i = 0, j = 0; i < blockCount; i++) {
            byte[] l = getFourByte(text, j);
            j += 4;
            byte[] r = getFourByte(text, j);
            j += 4;

            int intL = getInteger(l);
            int intR = getInteger(r);

            int[] arr = encrypt(intL, intR);

            l = getByteArray(arr[0]);
            r = getByteArray(arr[1]);

            setFourByte(result, count, l);
            count += 4;
            setFourByte(result, count, r);
            count += 4;
        }
        result[textLen + p] = (byte) p;
        return result;
    }

    public byte[] decode(String key, byte[] text) {
        blowfishInit(Integer.toString(key.hashCode()).getBytes());
        int textLen = text.length;
        int p = text[textLen - 1];
        textLen -= (p + 1);
        byte[] result = new byte[textLen];
        int blockCount = (textLen % 8 != 0) ? (textLen / 8 + 1) : (textLen / 8);
        int count = 0;
        for (int i = 0, j = 0; i < blockCount; i++) {
            byte[] l = getFourByte(text, j);
            j += 4;

            byte[] r = getFourByte(text, j);
            j += 4;

            int lInt = getInteger(l);
            int rInt = getInteger(r);

            int[] arr = decrypt(lInt, rInt);
            l = getByteArray(arr[0]);
            r = getByteArray(arr[1]);
            setFourByte(result, count, l);
            count += 4;
            setFourByte(result, count, r);
            count += 4;
        }
        return result;
    }

    public void encodeFile(String fileInName, String fileOutName, String key) throws IOException {
        byte[] inFileBytes = loadByteArrayFromFile(fileInName);
        byte[] encodeFileBytes = encode(key, inFileBytes);
        saveByteArrayToFile(encodeFileBytes, fileOutName);
    }

    public void decodeFile(String fileInName, String fileOutName, String key) throws IOException {
        byte[] inFileBytes = loadByteArrayFromFile(fileInName);
        byte[] resultArray = decode(key, inFileBytes);
        saveByteArrayToFile(resultArray, fileOutName);
    }

    public String generateKey() {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            sb.append(rnd.nextInt(65536));
        }
        return sb.toString();
    }

    public void saveKeyToFile(String fileName, String key) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(key.getBytes());
        fos.close();
    }

    public String loadKeyFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] result = Files.readAllBytes(file.toPath());
        return new String(result);
    }

}