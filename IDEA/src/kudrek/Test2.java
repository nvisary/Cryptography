package kudrek;

/**
 * Created by sgfromrus on 11.2017
 */
public class Test2 {
    public static void main(String[] args) {
        int a = 0b111111;
        System.out.println(Integer.toBinaryString(a));
        a = a << 8;
        System.out.println(Integer.toBinaryString(a));
    }
}
