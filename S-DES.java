import java.util.*;

public class Main
{

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter Key");
        String key = scanner.nextLine();
        //String key = "1111001100";

        System.out.println("Enter message");
        String message = scanner.nextLine();
        //String message = "hello world";

        System.out.println();
        System.out.println("Key: " + key);
        System.out.println("Message: " + message);

        SDES sdes = new SDES(key);

        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++)
            encryptedMessage.append(sdes.encrypt(message.charAt(i)));
        System.out.println("Encoded Message: " + encryptedMessage.toString());


        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++)
            decryptedMessage.append(sdes.decrypt(encryptedMessage.charAt(i)));
        System.out.println("Decoded Message: " + decryptedMessage);
    }
}

class SDES 
{
    private BitSet masterKey;

    private static boolean [][][] S0 = new boolean[4][4][2];
    private static boolean [][][] S1 = new boolean[4][4][2];
    static {
        S0[0][0] = new boolean[]{false,true};
        S0[0][1] = new boolean[]{false,false};
        S0[0][2] = new boolean[]{true,true};
        S0[0][3] = new boolean[]{true,false};
        S0[1][0] = new boolean[]{true,true};
        S0[1][1] = new boolean[]{true,false};
        S0[1][2] = new boolean[]{false,true};
        S0[1][3] = new boolean[]{false,false};
        S0[2][0] = new boolean[]{false,false};
        S0[2][1] = new boolean[]{true,false};
        S0[2][2] = new boolean[]{false,true};
        S0[2][3] = new boolean[]{true,true};
        S0[3][0] = new boolean[]{true,true};
        S0[3][1] = new boolean[]{false,true};
        S0[3][2] = new boolean[]{true,true};
        S0[3][3] = new boolean[]{true,false};

        S1[0][0] = new boolean[]{false,false};
        S1[0][1] = new boolean[]{false,true};
        S1[0][2] = new boolean[]{true,false};
        S1[0][3] = new boolean[]{true,true};
        S1[1][0] = new boolean[]{true,false};
        S1[1][1] = new boolean[]{false,false};
        S1[1][2] = new boolean[]{false,true};
        S1[1][3] = new boolean[]{true,true};
        S1[2][0] = new boolean[]{true,true};
        S1[2][1] = new boolean[]{false,false};
        S1[2][2] = new boolean[]{false,true};
        S1[2][3] = new boolean[]{false,false};
        S1[3][0] = new boolean[]{true,false};
        S1[3][1] = new boolean[]{false,true};
        S1[3][2] = new boolean[]{false,false};
        S1[3][3] = new boolean[]{true,true};
    }


    public SDES(String userKey) {
        BitSet key = new BitSet(10);

        for(int i = 0; i < 10; i++)
            if(userKey.charAt(i) == '1') 
                key.set(i);

        masterKey = key;
    }


    public BitSet getKey() {
        return (BitSet) this.masterKey.clone();
    }


    private BitSet p10(final BitSet key) {
        BitSet permutedKey = new BitSet(10);

        changeBit(permutedKey, 0, key.get(2));
        changeBit(permutedKey, 1, key.get(4));
        changeBit(permutedKey, 2, key.get(1));
        changeBit(permutedKey, 3, key.get(6));
        changeBit(permutedKey, 4, key.get(3));
        changeBit(permutedKey, 5, key.get(9));
        changeBit(permutedKey, 6, key.get(0));
        changeBit(permutedKey, 7, key.get(8));
        changeBit(permutedKey, 8, key.get(7));
        changeBit(permutedKey, 9, key.get(5));

        return permutedKey;
    }


    private BitSet circularLeftShift(final BitSet key, int shift) {
        BitSet s = new BitSet(10);

        for (int i = 0; i < 5; i++) {
            boolean value = key.get(i);
            int shiftedIndex = i - shift;
            
            if (shiftedIndex < 0) {
                if(value)
                    s.set(5 + shiftedIndex);
                else {
                    s.clear(5 + shiftedIndex);
                }
            }
            
            else {
                if(value)
                    s.set(shiftedIndex);
                else {
                    s.clear(shiftedIndex);
                }
            }
        }

        for (int i = 5; i < 10; i++) {
            boolean value = key.get(i);
            int shiftedIndex = i - shift;

            if (shiftedIndex < 5) {
                if(value)
                    s.set(5+shiftedIndex);
                else {
                    s.clear(5+shiftedIndex);
                }
            }

            else {
                if(value)
                    s.set(shiftedIndex);
                else {
                    s.clear(shiftedIndex);
                }
            }
        }
        return s;
    }


    private BitSet p8(final BitSet key) {
        BitSet p8 = new BitSet(8);

        changeBit(p8, 0, key.get(5));
        changeBit(p8, 1, key.get(2));
        changeBit(p8, 2, key.get(6));
        changeBit(p8, 3, key.get(3));
        changeBit(p8, 4, key.get(7));
        changeBit(p8, 5, key.get(4));
        changeBit(p8, 6, key.get(9));
        changeBit(p8, 7, key.get(8));

        return p8;
    }


    private List<BitSet> generateKeys() {
        BitSet p10 = p10(this.getKey());

        BitSet c1 = circularLeftShift(p10, 1);

        BitSet k1 = p8(c1);

        BitSet c2 = circularLeftShift(c1,2);

        BitSet k2 = p8(c2);

        List<BitSet> keys = new ArrayList<>();
        keys.add(k1);
        keys.add(k2);

        return keys;
    }


    private BitSet ip(final BitSet plainText) {
        BitSet ip = new BitSet(8);

        changeBit(ip, 0, plainText.get(1));
        changeBit(ip, 1, plainText.get(5));
        changeBit(ip, 2, plainText.get(2));
        changeBit(ip, 3, plainText.get(0));
        changeBit(ip, 4, plainText.get(3));
        changeBit(ip, 5, plainText.get(7));
        changeBit(ip, 6, plainText.get(4));
        changeBit(ip, 7, plainText.get(6));
        
        return ip;
    }


    public BitSet rip(final BitSet permutedText) {
        BitSet rip = new BitSet();

        changeBit(rip, 0, permutedText.get(3));
        changeBit(rip, 1, permutedText.get(0));
        changeBit(rip, 2, permutedText.get(2));
        changeBit(rip, 3, permutedText.get(4));
        changeBit(rip, 4, permutedText.get(6));
        changeBit(rip, 5, permutedText.get(1));
        changeBit(rip, 6, permutedText.get(7));
        changeBit(rip, 7, permutedText.get(5));

        return rip;
    }


    public BitSet ep(final BitSet input) {
        BitSet ep = new BitSet(8);

        if(input.get(0)) {
            ep.set(1);
            ep.set(7);
        }

        if(input.get(1)) {
            ep.set(2);
            ep.set(4);
        }
        
        if(input.get(2)) {
            ep.set(3);
            ep.set(5);
        }

        if(input.get(3)) {
            ep.set(0);
            ep.set(6);
        }

        return ep;
    }


    private BitSet xor(final BitSet b1, final BitSet b2) {
        BitSet x = (BitSet) b1.clone();
        x.xor(b2);

        return x;
    }


    private BitSet sandbox(final BitSet input, final boolean [][][] s) {
        boolean p0, p1, p2, p3;

        p0 = input.get(0);
        p1 = input.get(1);
        p2 = input.get(2);
        p3 = input.get(3);

        int line = 0;
        if (!p0 && !p3) line = 0;
        if (!p0 && p3) line = 1;
        if (p0 && !p3) line = 2;
        if (p0 && p3) line = 3;

        int col = 0;
        if (!p1 && !p2) col = 0;
        if (!p1 && p2) col = 1;
        if (p1 && !p2) col = 2;
        if (p1 && p2) col = 3;

        BitSet res = new BitSet(2);
        boolean[] value = s[line][col];
        if (value[0])
            res.set(0);

        if (value[1])
            res.set(1);

        return res;
    }


    private BitSet p4(final BitSet part1, final BitSet part2) {
        BitSet p4 = new BitSet(4);

        changeBit(p4, 0, part1.get(1));
        changeBit(p4, 1, part2.get(1));
        changeBit(p4, 2, part2.get(0));
        changeBit(p4, 3, part1.get(0));
        
        return p4;
    }


    private BitSet f(BitSet right, BitSet sk) {
        BitSet ep = ep(right);
        BitSet xor = xor(ep,sk);

        BitSet s0 = sandbox(xor.get(0, 4), S0);
        BitSet s1 = sandbox(xor.get(4, 8), S1);
        
        return p4(s0,s1);
    }


    private BitSet fK(BitSet bits, BitSet key) {
        BitSet f = f(bits.get(4, 8), key);
        BitSet x = xor(bits.get(0, 4), f);

        BitSet c = new BitSet(8);

        for(int i = 0; i < 4; i++)
            if(x.get(i))
                c.set(i);
        
        for(int i = 4; i < 8; i++)
            if(bits.get(i))
                c.set(i);

        return c;
    }


    private BitSet sw(BitSet input) {
        BitSet inverse = new BitSet(8);

        for(int i = 0; i < 4; i++)
            if(input.get(i))
                inverse.set(i + 4);

        for(int i = 4; i < 8; i++)
            if(input.get(i))
                inverse.set(i - 4);

        return inverse;
    }


    public char encrypt(char c) {
        List<BitSet> keys = generateKeys();
        BitSet k1 = keys.get(0);
        BitSet k2 = keys.get(1);

        String binary = Integer.toBinaryString((int) c);

        BitSet b = new BitSet(8);

        int index = 7;
        for(int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1')
                b.set(index);

            index--;
        }

        BitSet ip = ip(b);
        BitSet fk1 = fK(ip, k1);
        BitSet inverse = sw(fk1);
        BitSet fk2 = fK(inverse, k2);
        BitSet enc = rip(fk2);

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (enc.get(i))
                res.append("1");
            else {
                res.append("0");
            }
        }

        int i = Integer.parseInt(res.toString(), 2);
        return (char) i;
    }


    public char decrypt(char c) {
        List<BitSet> keys = generateKeys();

        BitSet k1 = keys.get(0);
        BitSet k2 = keys.get(1);

        String binary = Integer.toBinaryString((int) c);
        
        BitSet b = new BitSet(8);

        int index = 7;
        for(int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1')
                b.set(index);
            
            index--;
        }

        BitSet ip = ip(b);
        BitSet fk1 = fK(ip,k2);
        BitSet inverse = sw(fk1);
        BitSet fk2 = fK(inverse, k1);
        BitSet dec = rip(fk2);

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            if (dec.get(i))
                res.append("1");
            else {
                res.append("0");
            }
        }

        int i = Integer.parseInt(res.toString(), 2);
        return (char) i;
    }


    private void changeBit(BitSet bitset, int index, boolean newValue) {
        if (newValue)
            bitset.set(index);
        else {
            bitset.clear(index);
        }
    }


    public void printBitSet(BitSet bitSet, int size) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < size; i++) {
            if (bitSet.get(i))
                builder.append("1");
            else {
                builder.append("0");
            }
        }

        System.out.println(builder.toString());
    }
}    
