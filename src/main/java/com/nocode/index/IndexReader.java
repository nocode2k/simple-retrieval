package com.nocode.index;

import com.nocode.document.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IndexReader {

    private final Map<String, TokenInfo> tokenHash;

    public IndexReader(Map<String, TokenInfo> tokenHash) {
        this.tokenHash = tokenHash;
    }

    private DocComparator getDocComparator(TextFileVector textFileVector, double score) {
        return new DocComparator(textFileVector, score);
    }

    private void calculateScoreToken(String token, Map<TextFileVector, Double> resultHashMap) {
        TokenInfo tokenInfo = this.tokenHash.get(token);
        double score;
        double weight = 1.0; //match 가중치 1.0
        for (TokenGeneration tokenGeneration : tokenInfo.tokenGenerationList) {
            Double value = resultHashMap.get(tokenGeneration.textDocument);
            if (value == null) {
                value = 0.0;
            }
            score = weight + value + (tokenInfo.idf * tokenGeneration.tf);
            score = Math.round(score*10000) / 10000.0; //소숫점 4자리까지 표현
            resultHashMap.put(tokenGeneration.textDocument, score);
        }
    }

    public DocComparator[] docComparator(TokenMapVector searchVector) {
        Map<TextFileVector, Double> resultHashMap = new HashMap<>();
        for (Map.Entry<String, TokenCount> entry : searchVector.entrySet()) {
            String token = entry.getKey();
            calculateScoreToken(token, resultHashMap);
        }

        DocComparator[] docComparators = new DocComparator[resultHashMap.size()];
        int searchCount = 0;
        for (Map.Entry<TextFileVector, Double> entry : resultHashMap.entrySet()) {
            TextFileVector textFileVector = entry.getKey();
            double score = entry.getValue();
            docComparators[searchCount++] = getDocComparator(textFileVector, score);
        }
        Arrays.sort(docComparators);
        return docComparators;
    }

    public void printResultDocument(DocComparator[] docComparators) {
        System.out.println("> Results: ");
        if (docComparators.length == 0) {
            System.out.println("\nNo matching documents found.");
        } else {
            int no = 1;
            for (DocComparator docComparator : docComparators) {
                System.out.println("Rank [" + no +"] "
                        + docComparator.textFileVector.toString() + " > score: " + docComparator.score);
                no++;
            }
        }
    }
}
