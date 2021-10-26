package com.nocode.document;

/**
 * query string vector class
 */
public class TextStringVector {

    public TokenMapVector getTokenMapVector(String keyword) {
        TokenMapVector vector = new TokenMapVector();
        String[] keywords = keyword.split(" ");
        for(String word : keywords) {
            vector.increment(word.toLowerCase());
        }
        return vector;
    }
}
