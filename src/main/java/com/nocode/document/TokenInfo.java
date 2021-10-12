package com.nocode.document;

import java.util.ArrayList;
import java.util.List;

public class TokenInfo {
    public double idf;

    public List<TokenGeneration> tokenGenerationList;

    public TokenInfo() {
        this.tokenGenerationList = new ArrayList<>();
        this.idf = 0.0;
    }
}
