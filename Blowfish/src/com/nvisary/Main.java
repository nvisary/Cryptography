package com.nvisary;


import com.nvisary.blowfish.Blowfish;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
            Blowfish encoder = new Blowfish();
            Blowfish decoder = new Blowfish();
            String key = encoder.generateKey();
            encoder.saveKeyToFile("key.txt", key);
            encoder.encodeFile("cat.jpg", "out.binar", key);

            String key2 = decoder.loadKeyFromFile("key.txt");
            decoder.decodeFile("out.binar", "out.jpg", key2);

    }
}
