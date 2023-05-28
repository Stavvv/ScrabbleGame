package test;


import java.util.HashSet;

public class CacheManager {
    private int capacity;
    private CacheReplacementPolicy crp;
    HashSet<String> wordSet;

    public CacheManager(int capacity, CacheReplacementPolicy crp) {
        this.capacity = capacity;
        this.crp = crp;
        this.wordSet = new HashSet<>();

    }

    public boolean query(String word) {
        return wordSet.contains(word);
    }

    public void add(String word) {
        if (!wordSet.contains(word)) {
            if (wordSet.size() >= capacity) {
                wordSet.remove(crp.remove());
            }
        }
        crp.add(word);
        wordSet.add(word);
    }


}
