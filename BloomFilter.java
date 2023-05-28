package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    int size;
    private BitSet bitSet;
    private MessageDigest md1;
    private MessageDigest md2;

    public BloomFilter(int size, String alg1, String alg2) {
        this.size = size;
        try {


            md1 = MessageDigest.getInstance(alg1);
            md2 = MessageDigest.getInstance(alg2);
        } catch (NoSuchAlgorithmException e) {
        }
        bitSet = new BitSet(size);

    }

    public void add(String word) {
        byte[] bts1 = md1.digest(word.getBytes());
        BigInteger b = new BigInteger(bts1);
        int a = b.intValue();
        if (a < 0) {
            a = -a;
        }
        bitSet.set(a % size);
        byte[] bts2 = md2.digest(word.getBytes());
        BigInteger b2 = new BigInteger(bts2);
        int a2 = b2.intValue();
        if (a2 < 0) {
            a2 = -a2;
        }
        bitSet.set(a2 % size);
    }

    public boolean contains(String word) {

        byte[] bts1 = md1.digest(word.getBytes());
        BigInteger b = new BigInteger(bts1);
        int a = b.intValue();
        if (a < 0) {
            a = -a;
        }
        if (!bitSet.get(a % size)) {
            return false;
        }
        byte[] bts2 = md2.digest(word.getBytes());
        BigInteger b2 = new BigInteger(bts2);
        int a2 = b2.intValue();
        if (a2 < 0) {
            a2 = -a2;
        }
        if (!bitSet.get(a2 % size)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (bitSet.get(i)) {
                st.append("1");
            } else if (bitSet.nextSetBit(i + 1) == -1) {
                break;
            } else {
                st.append("0");
            }
        }
        return st.toString();
    }


}
