package com.nocode.document;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TokenMapVector {
    public Map<String, TokenCount> tokenMap;

    public TokenMapVector() {
        tokenMap = new HashMap<>();
    }

    private String stringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str = str.replaceAll(match, "");
        return str;
    }

    public Set<Entry<String, TokenCount>> entrySet() {
        return tokenMap.entrySet();
    }

    public void increment(String token, double amount) {
        token = stringReplace(token);
        TokenCount tokenCount = tokenMap.get(token);
        if (tokenCount == null) {
            tokenCount = new TokenCount();
            tokenMap.put(token, tokenCount);
        }
        tokenCount.increment(amount);
    }

    public void increment(String token) {
        increment(token, 1.0);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Entry<String, TokenCount> entry : entrySet()) {
            ret.append(entry.getKey()).append(": ").append(entry.getValue().getValue()).append(" ");
        }
        return ret.toString();
    }
}
