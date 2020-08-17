public class Caesor
{
    
    public static String encrypt(String message, int key)
    {
        String cypher = "";
        char newCharacter;
        int asciiValue;
        
        for (int i = 0; i < message.length(); i++)
        {
            //ascii = ((message.charAt(i) + key) % 26) + 97;
            asciiValue = (((message.charAt(i) + key) - 97) % 26) + 97;
            newCharacter = (char)asciiValue;
            cypher = cypher + newCharacter;
        }
        
        return cypher;
    }
    
    
    public static String decrypt(String codedMessage, int key)
    {
        String originalMessage = "";
        char newCharacter;
        int asciiValue;
        
        for (int i = 0; i < codedMessage.length(); i++)
        {
            asciiValue = (((((codedMessage.charAt(i) - key) - 97) % 26) + 26) % 26) + 97;
            newCharacter = (char)asciiValue;
            originalMessage = originalMessage + newCharacter;
        }
        return originalMessage;
    }
    
    
    public static void main(String args[])
    {
        String text = "caesarcipher";
        int k = 3;
        
        System.out.println("Original text is '" + text + "'\n");
        
        String encryptedMessage = encrypt(text, k);
        
        System.out.println("Encoded message: " + encryptedMessage);
        System.out.println("Decoded message: " + decrypt(encryptedMessage, k));

    }
}
