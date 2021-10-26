package com.nocode.index;

import com.nocode.document.*;

import java.io.File;
import java.util.*;

public class IndexWriter {
    private final List<TextFileVector> textDocumentList;
    private final Map<String, TokenInfo> tokenHash;

    public IndexWriter() {
        this.textDocumentList = new ArrayList<>();
        this.tokenHash = new HashMap<>();
    }

    private void indexToken(String token, int count, TextFileVector textDocument) {
        TokenInfo tokenInfo = tokenHash.get(token);
        if (tokenInfo == null) {
            tokenInfo = new TokenInfo();
            tokenHash.put(token, tokenInfo);
        }
        tokenInfo.tokenGenerationList.add(new TokenGeneration(textDocument, count));
    }

    public void indexDocument(File inputFile) {
        TextFileVector textDocument = new TextFileVector(inputFile);
        textDocumentList.add(textDocument);

        TokenMapVector tokenMapVector = textDocument.getTokenMapVector();
        for (Map.Entry<String, TokenCount> entry : tokenMapVector.entrySet()) {
            String token = entry.getKey();
            int count = (int) entry.getValue().getValue();
            //System.out.println("token > " + token + ", count > " + count + ", file > " + textDocument.toString());
            indexToken(token, count, textDocument);
        }
    }

    public void computeIDFDocument() {
        double documentSize = textDocumentList.size();
        Iterator<Map.Entry<String, TokenInfo>> mapEntries = tokenHash.entrySet().iterator();
        while (mapEntries.hasNext()) {
            Map.Entry<String, TokenInfo> entry = mapEntries.next();
            TokenInfo tokenInfo = entry.getValue();
            double numDocRefs = tokenInfo.tokenGenerationList.size();
            // idf(t,D) = 1 + log (N/( n))
            // N은 데이터 세트의 문서 수
            // n은 데이터 세트 중 용어 t를 포함하는 문서의 수
            double idf = 1 + Math.log(documentSize / numDocRefs);
            if (idf == 0.0) {
                mapEntries.remove();
            } else {
                tokenInfo.idf = idf;
            }
        }
    }
    public Map<String, TokenInfo> getTokenHash() {
        return tokenHash;
    }
}
