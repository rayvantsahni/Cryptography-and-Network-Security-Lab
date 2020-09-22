import java.util.*;

public class Main
{
    public static int gcd(int a, int b)
    {
        return (b == 0) ? a : gcd(b, a % b);
    }

    public static void main(String []args)
    {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter first number:");
        int x = scan.nextInt();
        System.out.println("Enter second number:");
        int y = scan.nextInt();

        System.out.println(gcd(x, y));
    }
}
