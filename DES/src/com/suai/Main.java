package com.suai;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        /*DES encoder = new DES();
        byte[] arr = encoder.encode(new String("Hello world").getBytes(), "key");
        System.out.println(new String(arr));


        DES decoder = new DES();
        byte[] result = decoder.decode(arr, "key");
        System.out.println(new String(result));*/


        DES encoder = new DES();
        byte[] arr = encoder.loadFromFile("in.pdf");
        byte[] res = encoder.encode(arr, "key");
        encoder.saveToFile(res, "out.bin");

        DES decoder = new DES();
        arr = decoder.loadFromFile("out.bin");
        res = decoder.decode(arr, "key");
        decoder.saveToFile(res, "result.pdf");

    }
}
