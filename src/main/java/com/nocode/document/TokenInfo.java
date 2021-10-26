package com.nocode.document;

import java.util.ArrayList;
import java.util.List;

/**
 * inverted index structure class
 */
public class TokenInfo {
    public double idf;

    //posting list
    public List<TokenGeneration> tokenGenerationList;

    public TokenInfo() {
        this.tokenGenerationList = new ArrayList<>();
        this.idf = 0.0;
    }
}
