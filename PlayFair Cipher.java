import java.util.*;

public class Main
{
    static char[][] table = new char[5][5];
    static HashSet<Character> keySet = new HashSet<>(); //stores the keyword, helps to remove ignore duplicates while filling the table
    static HashMap<Character, int[]> keyMap = new HashMap<>(); //stores each character as key and its location in the table as value


    public static void fillTable(String keyword)
    {
        char e;
        int j, skipped = 0;

        for (j = 0; j < keyword.length(); j++) //fills the table with the keyword left to right, top to bottom without duplicates
        {
            e = keyword.charAt(j);

            if (!keySet.contains(e))
            {
                keySet.add(e);
                table[(j-skipped) / 5][(j-skipped) % 5] = e;
            }
            else
            {
                skipped++;
            }
        }

        j = j - skipped;

        for (char i = 'a'; i <= 'z'; i++)
        {
            if (keySet.contains((i)) || (i == 'j'))
                continue;
            table[j / 5][j % 5] = i;
            j++;
        }

        for (int m = 0; m < 5; m++) //filling the HashMap will characters as key and their location in the table as value
        {
            for (int n = 0; n < 5; n++)
            {
                keyMap.put(table[m][n], new int[]{m, n});
            }
        }
    }




    public static String splitText(String text)
    {
        String newText = "";
        int count = 0;
        char e;

        for (int i = 0; i < text.length(); i++)
        {
            e = text.charAt(i);

            if (e == ' ')
                continue;
            else if (count == 2)
            {
                newText += " " + e;
                count = 1;
            }
            else if ((count == 1) && (e == text.charAt(i-1)))
            {
                newText += "x " + e;
                count = 1;
            }
            else
            {
                newText += e;
                count++;
            }
        }

        return count % 2 == 0 ? newText : newText + "x"; //adds an 'x' in the end last pair if it isn't already one
    }



    public static String Cipher(String pairedText)
    {
        String cipherText = "";
        int[] first, second;
        char a, b;

        for (int i = 0; i < pairedText.length(); i++)
        {

            a = pairedText.charAt(i);
            if (pairedText.charAt(i) == ' ')
                continue;
            b = pairedText.charAt(i+1);

            if (a == 'j')
                a = 'i';
            if (b == 'j')
                b = 'i';

            first = keyMap.get(a); //contains position of first character
            second = keyMap.get(b); //contains position of the second character


            if (first[0] == second[0])
            {
                cipherText += table[first[0]][(first[1]+1)%5];
                cipherText += table[second[0]][(second[1]+1)%5];
            }

            else if (first[1] == second[1])
            {
                cipherText += table[(first[0]+1)%5][first[1]];
                cipherText += table[(second[0]+1)%5][second[1]];
            }

            else
            {
                cipherText += table[first[0]][second[1]];
                cipherText += table[second[0]][first[1]];
            }

            i++;
        }

        return cipherText;
    }



    public static void main(String[] args)
    {

        String keyword = "monarchy";
        String plainText = "we will plan tomorrow to attack yesterday";


        fillTable(keyword); //fills the 5x5 table with the keyword and the rest of the characters

        System.out.println("Table-");
        for (char[] row: table) //prints the table
            System.out.println(Arrays.toString(row));


        System.out.println("\nKEYWORD:\n" + keyword); //prints out the keyword
        System.out.println("ORIGINAL MESSAGE:\n" + plainText); //prints out the original message
        System.out.println("CIPHER TEXT:\n" + Cipher(splitText(plainText))); //prints out the encoded message
    }
}
