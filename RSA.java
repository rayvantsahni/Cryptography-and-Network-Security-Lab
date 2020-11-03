import java.lang.Math.*;
import java.util.*;

public class Main
{
    static double cipherText;  // will store encoded message
    static double originalText;  // will store decoded message


    public static double encrypt(double plainText, double e, double n) {
        cipherText = Math.pow(plainText, e);
        return cipherText % n;
    }

    public static double decrypt(double cipherText, double d, double n) {
        originalText = Math.pow(cipherText, d) % n;
        return originalText;
    }

    public static double gcd(double a, double b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }


    public static void main(String[] args) {
        final double p = 73;  // first prime number
        final double q = 233;  // second prime number
        final double n = p * q;
        final double phi = (p - 1) * (q - 1);
        System.out.println("'phi' is " + phi);

        double e = 2;  // finding 'e' for encryption
        while ((e < phi) && (gcd(e, phi) != 1))
            e++;
        System.out.println("'e' is " + e);

        double d = (1.0 / e) % phi;  // finding 'd' for decryption
        System.out.println("'d' is " + d);


        double message = 5267;
        System.out.printf("Message: %.1f\n", message);

        System.out.printf("Encoded as: %.1f\n", encrypt(message, e, n));

        originalText = decrypt(cipherText, d, n);
        System.out.printf("Decoded as: %.1f\n", originalText);
    }
}
