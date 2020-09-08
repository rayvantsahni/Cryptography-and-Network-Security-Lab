import java.util.*;

public class Main
{

    static int[][] key = {{6, 24, 1},
                         {13, 16, 10},
                         {20, 17, 15}};

    static ArrayList<Integer> characterPositions;



    public static ArrayList<Integer> getCharacterPositions(String text, int m)
    {
        characterPositions = new ArrayList<Integer>(text.length() + m - 1);

        for (int i = 0; i < text.length(); i++)
            characterPositions.add((int) text.charAt(i) - 97);

        while (characterPositions.size() % m != 0)
            characterPositions.add(23);

        return characterPositions;
    }



    public static String cipher(int m)
    {
        String cipherText = "";
        int i;
        int c;

        for (i = 0; i < characterPositions.size(); i += m)
        {
            for (int j = 0; j < m; j++)
            {
                c = ((key[j][0] * characterPositions.get(i)) +
                        (key[j][1] * characterPositions.get(i+1)) +
                            (key[j][2] * characterPositions.get(i+2)));

                cipherText += (char) ((c % 26) + 97);

            }
        }

        return cipherText;
    }



    public static void main(String[] args)
    {
        String plainText = "cat";
        int m = 3;

        getCharacterPositions(plainText, m);
        System.out.println("Original Message: " + plainText);
        System.out.println("Cipher: " + cipher(m));
    }
}
