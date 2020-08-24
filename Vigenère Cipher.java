public class Main
{

    public static String Cipher(String message, String key)
    {
        String cipher = "";
        int asciiValue;

        for (int i = 0; i < message.length(); i++)
        {
            if (message.charAt(i) == ' ')
                continue;

            asciiValue = (((message.charAt(i) + ((key.charAt(i % key.length()) - 97) % 26)) - 97) % 26) + 97;
            cipher = cipher + (char)asciiValue;
        }

        return cipher;
    }


    public static void main(String[] args)
    {
        String plainText = "hello world";
        String key = "byee";

        System.out.println("The original message is:\n" + plainText);
        String encryptedMessage = Cipher(plainText, key);
        System.out.println("Encoded message: " + encryptedMessage);

    }
}
