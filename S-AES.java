import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    /**
     * Will be used for multiplying in round 1
     */
    public static final int[] mix = {0, 4, 8, 12, 3, 7, 11, 15, 6, 2, 14, 10, 5, 1, 13, 9};

    /**
     * Will be used for substitution of nibbles
     */
    public static final Map<String, String> sBox;
    static {
            sBox = new HashMap<String, String>();
            sBox.put("0000", "1001");
            sBox.put("0001", "0100");
            sBox.put("0010", "1010");
            sBox.put("0011", "1011");
            sBox.put("0100", "1101");
            sBox.put("0101", "0001");
            sBox.put("0110", "1000");
            sBox.put("0111", "0101");
            sBox.put("1000", "0110");
            sBox.put("1001", "0010");
            sBox.put("1010", "0000");
            sBox.put("1011", "0011");
            sBox.put("1100", "1100");
            sBox.put("1101", "1110");
            sBox.put("1110", "1111");
            sBox.put("1111", "0111");
        }



    /**
     * Gives the odd numbered words(3 and 5)
     * @param word1
     * @param word2
     * @return xor of the two words
     */
    public static String[] getOddWord(String[] word1, String[] word2) {
        return xor(word1, word2);
    }

    /**
     * Gives the even numberes words(2 and 4)
     * @param word1
     * @param word2
     * @param wordNumber is required to decide which array will the first word 'xor'ed with
     * @return
     */
    public static String[] getEvenWord(String[] word1, String[] word2, int wordNumber) {
        String[] temp = {"1000", "0000"};  // default taking wordNumber = 2
        if (wordNumber == 4)
            temp[0] = "0011";  // making changes if wordNumber == 4

        return xor(xor(word1, temp), subNib(rotNib(word2)));
    }

    /**
     * Combines two given words to form a key
     * @param word1
     * @param word2
     * @return returns a key
     */
    public static String[] combineWords(String[] word1, String[] word2) {
        return new String[]{word1[0], word1[1], word2[0], word2[1]};
    }



    /**
     * Rotates the nibbles, example (a, b) -> (b, a)
     * @param word
     * @return rotated nibbles
     */
    private static String[] rotNib(String[] word) {
        return new String[]{word[1], word[0]};
    }

    /**
     * Looks for the substitute values of the nibbles of the given word in the sBox
     * @param word
     * @return new array with the substitute values of the given word
     */
    private static String[] subNib(String[] word) {
        String[] subWord = new String[word.length];

        for (int i = 0; i < word.length; i++)
            subWord[i] = sBox.get(word[i]);

        return subWord;
    }

    /**
     * Swaps the 2nd and the 4th nibble of the given word
     * @param word
     */
    private static void shiftRow(String[] word) {
        String temp = word[1];
        word[1] = word[3];
        word[3] = temp;
    }

    /**
     * In this function, we first initialize an empty String array to store the results
     * of the xor of the two words.
     * Then, for each corresponding index of both the words, the binary string
     * is converted to decimal, and then 'xor'ed.
     * Then, this result is converted to a binary string, ready to be stored in the
     * string array.
     * Before storing, it is prepended with '0's  for uniformity
     * if the binary string is less than 4 digits long.
     * @param word1
     * @param word2
     * @return xor of the given two words
     */
    private static String[] xor(String[] word1, String[] word2) {
        int n = word1.length;
        String[] xorWord = new String[n];
        int xorInDecimal;
        String xorInBinary;

        for (int i = 0; i < n; i++) {
            xorInDecimal = Integer.parseInt(word1[i], 2) ^ Integer.parseInt(word2[i], 2);
            xorInBinary = Integer.toBinaryString(xorInDecimal);
            xorWord[i] = "0".repeat(4 - xorInBinary.length()) + xorInBinary;
        }

        return xorWord;

    }

    /**
     * Takes in the 2D array and return the its product with {{1,4}, {4,1}}
     * For the multiplication part, we look for the product of 2 values from the 'mix' array
     * defined at the start of the program.
     * For the addition [art, we simply take the xor of the 2 values.
     * @param S
     * @return
     */
    private static String[] multiply(int[][] S) {
        String[] sDash = new String[4];
        String binaryString;

        // does the mix columns operation, i.e multiplication according to the mix table and xor in place for the addition and then converts it into a string of binary
        binaryString = Integer.toBinaryString(S[0][0] ^ (mix[S[1][0]]));
        //  prepending the binary string with 0's if they are of length less than 4
        sDash[0] = "0".repeat(4 - binaryString.length()) + binaryString;

        binaryString = Integer.toBinaryString((mix[S[0][0]]) ^ S[1][0]);
        sDash[1] = "0".repeat(4 - binaryString.length()) + binaryString;

        binaryString = Integer.toBinaryString(S[0][1] ^ (mix[S[1][1]]));
        sDash[2] = "0".repeat(4 - binaryString.length()) + binaryString;

        binaryString = Integer.toBinaryString((mix[S[0][1]]) ^ S[1][1]);
        sDash[3] = "0".repeat(4 - binaryString.length()) + binaryString;

        return sDash;
    }

    /**
     * For this round, we perform all 4 operations -
     * 1. SubBytes
     * 2. ShiftRows
     * 3. MixColumns
     * 4. AddRoundKey
     *
     * @param word
     * @param key
     * @return
     */
    private static String[] applyRound1(String[] word, String[] key) {

        String[] subWord = subNib(word);

        shiftRow(subWord);

        int[][] S = {{Integer.parseInt(subWord[0], 2), Integer.parseInt(subWord[1], 2)},
                     {Integer.parseInt(subWord[2], 2), Integer.parseInt(subWord[3], 2)}};

        String[] sDash = multiply(S);

        return xor(sDash, key);

    }

    /**
     * For this round, we perform 3 operation -
     * 1. SubBytes
     * 2. ShiftRows
     * 3. AddRoundKey
     *
     * @param word
     * @param key
     * @return returns the final cipher for the original message
     */
    private static String[] applyFinalRound(String[] word, String[] key) {

        String[] subWord = subNib(word);

        shiftRow(subWord);

        return xor(subWord, key);
    }



    /**
     * Takes in the original message and the keys and returns the ciphertext
     * @param plainText
     * @param key0
     * @param key1
     * @param key2
     * @return
     */
    public static String[] encrypt(String[] plainText, String[] key0, String[] key1, String[] key2) {
        String[] round0 = xor(plainText, key0);
        String[] round1 = applyRound1(round0, key1);
        String[] roundFinal = applyFinalRound(round1, key2);

        return roundFinal;
    }



    public static void main(String[] args) {

        String[] plainText = {"1101","0111","0010","1000"};
        String[] key = {"0100","1010","1111","0101"};

	    String[] w0 = {key[0], key[1]};
	    String[] w1 = {key[2], key[3]};
	    String[] w2 = getEvenWord(w0, w1, 2);
	    String[] w3 = getOddWord(w2, w1);
	    String[] w4 = getEvenWord(w2, w3, 4);
        String[] w5 = getOddWord(w4, w3);

        String[] key0 = combineWords(w0, w1);
        String[] key1 = combineWords(w2, w3);
        String[] key2 = combineWords(w4, w5);

        String[] encodedMessage = encrypt(plainText, key0, key1, key2);


        System.out.println(MessageFormat.format("Message: {0}", Arrays.toString(plainText)));
        System.out.println(MessageFormat.format("Key: {0}", Arrays.toString(key)));
        System.out.println(MessageFormat.format("Cipher: {0}", Arrays.toString(encodedMessage)));

    }


}
