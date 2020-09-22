import java.util.*;

public class Main
{

    static char[][] textTable; //matrix that holds the message

    public static void fillTable(String plainText, String key) //fills the matrix with the characters of the message
    {
        int i;
        int keyLength = key.length(); //length of the key
        int textLength = plainText.length(); //length of the original message
        int numberOfRows = textLength % keyLength == 0 ? textLength / keyLength : (textLength / keyLength) + 1; //finding the number of rows required in the matrix according to the length of the message

        textTable = new char[numberOfRows][keyLength]; //initializing the matrix with the required size

        for (i = 0; i < textLength; i++) //filling the matrix
            textTable[i / keyLength][i % keyLength] = plainText.charAt(i);

        if (textLength % keyLength != 0) //handles the scenario where the last row is left with some infilled spaces
        {
            int spacesToFill = keyLength - (i % keyLength); //finding the number of unfilled spaces

            while (spacesToFill != 0) //filling the unfilled spaces with ...w,x,y,z until all spaces are filled
            {
                textTable[numberOfRows - 1][keyLength - spacesToFill] = (char) (123 - spacesToFill); //filling the unfilled spaces
                spacesToFill--;
            }
        }

        for (char[] arr: textTable) //printing out the matrix
            System.out.println(Arrays.toString(arr));
    }



    public static int findMaxInKey(String key) //finds the maximum digit in the key
    {
        int max = Character.getNumericValue(key.charAt(0)); //casts the character digits into integers

        for (int i = 1; i < key.length(); i++) //looping over each element
        {
            if (Character.getNumericValue(key.charAt(i)) > max) //comparing each element to max
                max = Character.getNumericValue(key.charAt(i));
        }

        return max; //returning the max digit in the key
    }



    public static String prepareText(String plainText) //removes spaces from the message and prepares it for encryption
    {
        StringBuilder newText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++)
        {
            if (plainText.charAt(i) == ' ') //ignoring all the spaces
                continue;

            newText.append(plainText.charAt(i)); //appending non-space characters into a new string
        }

        return newText.toString(); //returning the prepared string
    }



    public static String Cipher(String key) //encrypts the message
    {
        StringBuilder cipherText = new StringBuilder();
        int maxInKey = findMaxInKey(key); //stores the maximum digit in the key

        for (int i = 1; i <= maxInKey; i++) //gives the column of a particular position in the matrix
            for (int j = 0; j < textTable.length; j++) //gives the row of a particular position in the matrix
                cipherText.append(textTable[j][key.indexOf(String.valueOf(i))]); //column is position of each digit in the original key

        return cipherText.toString();
    }



    public static void main(String[] args)
    {
        String message = "attack postponed until yesterday"; //original message
        String key = "4312567"; //key

        String text = prepareText(message); //new message, ready to be encrypted
        System.out.println("\nOriginal Message:\n" + message); //printing the original message

        System.out.println("\nKey: ");
        for (int i = 0; i < key.length(); i++) //printing the key
            System.out.print(key.charAt(i) + " ");

        System.out.println("\n\nMessage Table: ");
        fillTable(text, key); //fills and prints the matrix

        System.out.println("\nCipher Text: ");
        System.out.println(Cipher(key)); //encrypts the original message and prints the cipher text

    }
}
