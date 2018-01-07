package com.nvisary;


import com.nvisary.blowfish.Blowfish;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
            Blowfish encoder = new Blowfish();
            Blowfish decoder = new Blowfish();
            encoder.encodeFile("in.txt", "out.binar", "mykey");
            decoder.decodeFile("out.binar", "new.txt", "mykey");
    }
}
