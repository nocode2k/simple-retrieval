package com.nocode.document;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TokenMapVector {
    public Map<String, Weight> tokenMap;

    public TokenMapVector() {
        tokenMap = new HashMap<>();
    }

    private String stringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str = str.replaceAll(match, "");
        return str;
    }

    public Set<Entry<String, Weight>> entrySet() {
        return tokenMap.entrySet();
    }

    public void increment(String token, double amount) {
        token = stringReplace(token);
        Weight weight = tokenMap.get(token);
        if (weight == null) {
            weight = new Weight();
            tokenMap.put(token, weight);
        }
        weight.increment(amount);
    }

    public void increment(String token) {
        increment(token, 1.0);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Entry<String, Weight> entry : entrySet()) {
            ret.append(entry.getKey()).append(": ").append(entry.getValue().getValue()).append(" ");
        }
        return ret.toString();
    }
}
