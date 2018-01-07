package kudrek;

/**
 * Created by sgfromrus on 11.2017
 */
public class Test {
    public static void main(String[] args) {
        /*
        * Пример показывающий действие данного алгоритма:
        * int b1; int b2;
        * b1 = (b1 & 0xFF) << 8;
        * b2 = b2 & 0xFF;
        * int c = b1 | b2;
        *
        * В одно число int (32 бита) мы записываем первые 16 бит первого числа и первые 16 бит второго числа
        * */

        int a = - 45;
        System.out.println("a: " + Integer.toBinaryString(a));
        System.out.println("0xff: " + Integer.toBinaryString(0xff));
        a = (a & 0xff);
        System.out.println("a & 0xff: " + Integer.toBinaryString(a));
        a = a << 8;
        System.out.println("a << 8: " + Integer.toBinaryString(a));
        int b = 1234545;
        System.out.println("b: " + Integer.toBinaryString(b));
        b = b & 0xff;
        System.out.println("b & 0xff: " + Integer.toBinaryString(b));
        int c = a | b;
        System.out.println("a | b: " + Integer.toBinaryString(c));
    }
}
