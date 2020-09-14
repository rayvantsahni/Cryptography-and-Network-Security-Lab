import java.util.*;

public class Main
{

    static char[][] textTable;

    public static void fillTable(String plainText, String key)
    {
        int i;
        int keyLength = key.length();
        int textLength = plainText.length();
        int numberOfRows = textLength % keyLength == 0 ? textLength / keyLength : (textLength / keyLength) + 1;

        textTable = new char[numberOfRows][keyLength];

        for (i = 0; i < textLength; i++)
        {
            textTable[i / keyLength][i % keyLength] = plainText.charAt(i);
        }

//        System.out.println("left " + ((i % keyLength) + 1));
        int spacesToFill = keyLength - (i % keyLength);
        int row = (int) i / keyLength;
//        System.out.println("row is " + row);
        int positionFromEnd = ;

        while (spacesToFill != 0)
        {
            textTable[row][positionFromEnd] =(char) (122 - positionFromEnd);
//            positionFromEnd++;
            spacesToFill--;
        }


        for (char[] arr: textTable)
            System.out.println(Arrays.toString(arr));
    }



    public static int findMaxInKey(String key)
    {
        int max = Character.getNumericValue(key.charAt(0));

        for (int i = 1; i < key.length(); i++)
        {
            if (Character.getNumericValue(key.charAt(i)) > max)
                max = Character.getNumericValue(key.charAt(i));
        }

        return max;
    }



    public static String prepareText(String plainText)
    {
        StringBuilder newText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++)
        {
            if (plainText.charAt(i) == ' ')
                continue;

            newText.append(plainText.charAt(i));
        }

        return newText.toString();
    }



    public static String Cipher(String key)
    {
        StringBuilder cipherText = new StringBuilder();
        int maxInKey = findMaxInKey(key);

        for (int i = 1; i <= maxInKey; i++) // ONE CHANGE HERE PLEASE!!
            for (int j = 0; j < textTable.length; j++)
                cipherText.append(textTable[j][key.indexOf(String.valueOf(i))]);

        return cipherText.toString();
    }



    public static void main(String[] args)
    {
        String message = "attack postponed until eternityyyy";
        String key = "4312567";

        String text = prepareText(message);

//        System.out.println("\nOriginal Message:\n" + message);

//        System.out.println("\nKey: ");
//        for (int i = 0; i < key.length(); i++)
//            System.out.print(key.charAt(i) + " ");

        System.out.println("\n\nMessage Table: ");
        fillTable(text, key);

//        System.out.println("\nCipher Text: ");
//        System.out.println(Cipher(key));


    }
}
