package test;


import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class LRU implements test.CacheReplacementPolicy {
    private Deque<String> deque;

    private HashSet<String> wordsSet;

    public LRU() {
        wordsSet = new HashSet<>();
        deque = new LinkedList<>();
    }

    @Override
    public void add(String word) {
        if (wordsSet.contains(word))
        {
            deque.remove(word);
            deque.addLast(word);
        } else {
            wordsSet.add(word);
            deque.addLast(word);
        }
    }

    @Override
    public String remove() {
        return deque.removeFirst();
    }
}
