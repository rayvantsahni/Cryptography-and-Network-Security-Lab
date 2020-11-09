import java.text.*;

class DiffieHellman
{
    static int P;
    static int G;

    public static int keyGeneration(int privateKey) {
        return (int) Math.pow(G, privateKey) % P;
    }


    public static int getSecretKey(int key, int privateKey) {
        return (int) Math.pow(key, privateKey) % P;
    }


    public static void main(String[] args) {
        P = 23;
        G = 5;

        System.out.println(MessageFormat.format("Shared value P: {0}", P));
        System.out.println(MessageFormat.format("Shared value G: {0}", G));
        System.out.println();

        int a = 4;  // client private key
        int b = 3;  // server private key

        System.out.println(MessageFormat.format("Client Private Key: {0}", a));
        System.out.println(MessageFormat.format("Server Private Key: {0}", b));
        System.out.println();

        int x = keyGeneration(a);  // client public key
        int y = keyGeneration(b);  // server public key

        System.out.println(MessageFormat.format("Client Public Key i.e {0}, is given to the Server.", x));
        System.out.println(MessageFormat.format("Server Public Key i.e {0}, is given to the Client.", y));
        System.out.println();

        int kA = getSecretKey(y, a);  // client secret key
        int kB = getSecretKey(x, b);  // server secret key

        System.out.println(MessageFormat.format("Client Secret Key: {0}", kA));
        System.out.println(MessageFormat.format("Server Secret Key: {0}", kB));
        System.out.println();
    }
}