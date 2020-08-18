public class Main
{
    
    public static String encrypt(String message, int key)
    {
        String cypher = "";
        char newCharacter;
        int asciiValue;
        
        for (int i = 0; i < message.length(); i++)
        {
            asciiValue = (((message.charAt(i) + key) - 97) % 26) + 97;
            newCharacter = (char)asciiValue;
            cypher = cypher + newCharacter;
        }
        
        return cypher;
    }
    
    
    public static String decrypt(String codedMessage, int key)
    {
        return encrypt(codedMessage, 26 - key);
    }
    
    
    public static void main(String args[])
    {
        String text = "byeworld"; //lowercase text
        int k = 3;
        
        System.out.println("Original text is '" + text + "'\n");
        
        String encryptedMessage = encrypt(text, k);
        
        System.out.println("Encoded message: " + encryptedMessage);
        System.out.println("Decoded message: " + decrypt(encryptedMessage, k));

    }
}
