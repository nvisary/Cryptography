package com.suai;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.util.Random;

import static com.suai.DesTables.*;

public class DES {

    private byte[] create56bitKey(String userKey) {
        byte[] result = new byte[7];

        for (int i = 0, j = 0; i < 7; i++, j++) {
            j = j % userKey.length();
            result[i] ^= userKey.charAt(j);
            result[i] ^= (i + 1) * 9999;
        }

        return result;
    }

    private long[] createSubKeys(long key) {
        long subKeys[] = new long[16];

        key = PC1(key);

        int c = (int) (key >> 28);
        int d = (int) (key & 0x0FFFFFFF);


        for (int i = 0; i < 16; i++) {

            if (rotations[i] == 1) {
                c = ((c << 1) & 0x0FFFFFFF) | (c >> 27);
                d = ((d << 1) & 0x0FFFFFFF) | (d >> 27);
            } else {
                c = ((c << 2) & 0x0FFFFFFF) | (c >> 26);
                d = ((d << 2) & 0x0FFFFFFF) | (d >> 26);
            }

            long cd = (c & 0xFFFFFFFFL) << 28 | (d & 0xFFFFFFFFL);

            subKeys[i] = PC2(cd);
        }

        return subKeys; /* 48-bit values */
    }

    private static int P(int src) {
        return (int) permute(P, 32, src & 0xFFFFFFFFL);
    }

    private static byte S(int boxNumber, byte src) {
        src = (byte) (src & 0x20 | ((src & 0x01) << 4) | ((src & 0x1E) >> 1));
        return S[boxNumber - 1][src];
    }

    private static long IP(long src) {
        return permute(IP, 64, src);
    }

    private long E(long src) {
        return permute(E, 32, src & 0xFFFFFFFFL);
    }

    private static long FP(long src) {
        return permute(FP, 64, src);
    }

    private static long PC2(long src) {
        return permute(PC2, 56, src);
    }

    private long PC1(long src) {
        return permute(PC1, 64, src);
    }

    private static long getLongFromBytes(byte[] b, int position) {
        long l = 0;
        for (int i = 0; i < 8; i++) {
            byte value;
            if ((position + i) < b.length) {
                value = b[position + i];
            } else {
                value = 0;
            }
            l = l << 8 | (value & 0xFFL);
         }
        return l;
    }

    private static void getBytesFromLong(byte[] b, int pos, long l) {
        for (int i = 7; i > -1; i--) {
            if ((pos + i) < b.length) {
                b[pos + i] = (byte) (l & 0xFF);
                l = l >> 8;
            } else {
                break;
            }
        }
    }

    private static long permute(byte[] table, int srcWidth, long src) {
        long dst = 0;
        for (int i = 0; i < table.length; i++) {
            int srcPos = srcWidth - table[i];
            dst = (dst << 1) | (src >> srcPos & 0x01);
        }
        return dst;
    }

    private int f(long key, long r) {
        long e = E(r);
        long x = e ^ key;
        int dst = 0;

        for (int i = 0; i < 8; i++) {
            dst >>>= 4;
            int s = S(8 - i, (byte) (x & 0x3F));
            dst |= s << 28;
            x >>= 6;
        }
        return P(dst);
    }

    public byte[] encode(byte[] text, String userKey) {
        byte[] key = create56bitKey(userKey);
        int textLen = text.length;
        int blocks = (textLen % 8 != 0) ? (textLen / 8 + 1) : (textLen / 8);
        int p = blocks * 8 - textLen;

        byte[] result = new byte[textLen + p + 1];
        int pos = 0;
        for (int i = 0; i < blocks; i++, pos += 8) {
            long c = encodeBlock(text, pos, key);
            getBytesFromLong(result, pos, c);
        }

        result[result.length - 1] = (byte) p; //сколько нулей добавили в конце последнего байта
        return result;
    }

    private long encodeBlock(byte[] text, int pos, byte[] key) {
        long m = getLongFromBytes(text, pos);
        long k = getLongFromBytes(key, 0);
        long subKeys[] = createSubKeys(k);
        long ip = IP(m);
        int l = (int) (ip >> 32);
        int r = (int) (ip & 0xFFFFFFFFL);

        for (int i = 0; i < 16; i++) {
            int previous_l = l;
            l = r;
            r = previous_l ^ f(r, subKeys[i]);

        }
        long rl = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);

        long fp = FP(rl);
        return fp;
    }

    public byte[] decode(byte[] text, String userKey) {
        byte[] key = create56bitKey(userKey);
        byte[] result = new byte[text.length];

        for (int i = 0; i < text.length; i += 8) {
            long c = decodeBlock(text, i, key);
            getBytesFromLong(result, i, c);
        }
        return result;
    }

    private long decodeBlock(byte[] text, int pos, byte[] key) {
        long c = getLongFromBytes(text, pos);
        long k = getLongFromBytes(key, 0);
        long[] subKeys = createSubKeys(k);
        long ip = IP(c);

        int l = (int) (ip >> 32);
        int r = (int) (ip & 0xFFFFFFFFL);

        for (int i = 15; i > -1; i--) {
            int previous_l = l;
            l = r;
            r = previous_l ^ f(r, subKeys[i]);
        }
        long rl = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);
        long fp = FP(rl);

        return fp;
    }

    public void saveKeyToFile(String userKey, String fileName) throws IOException {
        saveToFile(userKey.getBytes(), fileName);
    }

    public String loadKeyFromFile(String fileName) throws IOException {
        return new String(loadFromFile(fileName));
    }
    public byte[] loadFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return bytes;
    }
    public void saveToFile(byte[] arr, String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(arr);
        fileOutputStream.close();
    }

    public String generateKey(int keyLen) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < keyLen; i++) {
            sb.append(rnd.nextInt(65536));
        }

        return sb.toString();
    }

}
