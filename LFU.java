package test;


import java.util.HashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {
    private HashMap<String, Integer> frequencyMap;

    public LFU() {
        frequencyMap = new HashMap<>();
    }

    @Override
    public void add(String word) {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
    }

    @Override
    public String remove() {
        String st = "";
        for (HashMap.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (st.equals("")) {
                st = entry.getKey();
            } else {
                if (frequencyMap.get(st) > entry.getValue()) {
                    st = entry.getKey();
                }
            }
        }
        frequencyMap.remove(st);
        return st;
    }
}
