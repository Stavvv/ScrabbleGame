package test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Dictionary {
    CacheManager existedWords;
    CacheManager unExistedWords;
    BloomFilter bloomFilter;
    String file1;
    String file2;

    public Dictionary(String f1, String f2) {
        CacheReplacementPolicy lru = new LRU();
        CacheReplacementPolicy lfu = new LFU();
        existedWords = new CacheManager(400, lru);
        unExistedWords = new CacheManager(100, lfu);
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        file1 = f1;
        file2 = f2;
        File file = new File(f1);
        try {


            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String w = input.next();
                bloomFilter.add(w);
            }

            File file22 = new File(f2);
            Scanner input2 = new Scanner(file22);
            while (input2.hasNext()) {
                String w = input2.next();
                bloomFilter.add(w);
            }
        } catch (FileNotFoundException e) {
        }
    }


    public boolean query(String word) {
        if (existedWords.query(word)) {
            return true;
        } else if (unExistedWords.query(word)) {
            return false;

        }
        boolean result = bloomFilter.contains(word);
        if (result) {
            existedWords.add(word);
        } else {
            unExistedWords.add(word);
        }
        return result;
    }

    public boolean challenge(String word) {
        boolean result;
        try {
            result = IOSearcher.search(word, file1, file2);
        } catch (Exception e) {
            return false;
        }
        if (result) {
            existedWords.add(word);
        } else {
            unExistedWords.add(word);
        }
        return result;
    }
}
