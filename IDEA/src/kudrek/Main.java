package kudrek;

import javafx.application.Application;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /*if (args.length < 3) {
            if (args[0].equals("-e")) {
                String inName = args[1];
                String outName = args[2];
                IDEA encoder = new IDEA();
                String key;
                if (args.length == 4) {
                    key = args[3];
                } else {
                    key = encoder.generateKey(100);
                }
                byte[] in = encoder.loadByteArrayFromFile(inName);
                byte[] res = encoder.encode(in, key);
                encoder.saveByteArrayToFile(res, outName);
            } else if (args[0].equals("-d")) {

            } else {
                System.out.println("Ошибка в аргументах");
            }
        } else {
            System.out.println("Не то количесвто аргументов");
        }*/

        try {
            if (args.length > 4) {
                throw new IOException("Ошибка в количестве аргументов");
            } else if (args.length == 0 || args[0].equals("-ui")) {

            } else if (args[0].equals("-e")) {
                System.out.println("good");
            } else if (args[0].equals("-d")) {

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






        IDEA encoder = new IDEA();
        IDEA decoder = new IDEA();


        /*byte[] arr = encoder.loadByteArrayFromFile("new.txt");
        byte[] res = encoder.encode(arr, "mykey");
        encoder.saveByteArrayToFile(res, "out.dat");

        byte[] arr2 = decoder.loadByteArrayFromFile("out.dat");
        byte[] res2 = decoder.decode(arr2, "mykey");
        decoder.saveByteArrayToFile(res2, "result.txt");*/
    }
}


