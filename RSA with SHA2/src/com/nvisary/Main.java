package com.nvisary;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // write your code here

        /*long t1 = System.currentTimeMillis();
        RSA rsa = new RSA();
        rsa.generateKeys(2048, "password");
        PublicKey publicKey = rsa.getPublicKey();

        String text = "hello";
        byte[] arr = text.getBytes();
        RSA rsa2 = new RSA();
        rsa2.setPublicKey(publicKey);
        byte[] res = rsa2.encode(arr);
        System.out.println(new String(res));

        byte[] res2 = rsa.decode(res);
        System.out.println("res:" + new String(res2));
        long  t2 = System.currentTimeMillis();
        System.out.println("time: " + (t2 - t1));*/


        //RSA rsa3 = new RSA();
        //rsa3.generateKeys(128);

        String message = "I am Alice";

        RSA alice = new RSA();
        RSA bob = new RSA();

        alice.generateKeys(1024);
        byte[] sign = alice.mac(message);
        PublicKey publicKey = alice.getPublicKey();

        bob.setPublicKey(publicKey);
        boolean isAlice = bob.checkMac(message, sign);
        System.out.println(isAlice);

    }
}
