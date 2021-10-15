package com.nocode.document;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nocode.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class TfIdfTest {


    @Test
    void computedTfIdfValue() {
        try {
            List<Path> filePathList = FileUtil.getPathsFromResource("data");
            List<Map<String, Integer>> documents = new ArrayList<>();
            for(Path inputFile : filePathList) {
                Map<String, Integer> docMap = getToken(inputFile.toFile());
                documents.add(docMap);
            }
            double tfidf = tfIdf(documents.get(2), documents, "ranking");
            assertEquals(0.022512210953961234, tfidf);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * tf(t,d) = n/N
     * n은 문서 d에 용어 t가 나타나는 횟수
     * N은 문서 d의 총 용어 수
     * @param doc  list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    private double tf(Map<String, Integer> doc, String term) {
        double result = 0;
        for(Map.Entry<String, Integer> element : doc.entrySet()) {
            String word = element.getKey();
            Integer count = element.getValue();
            if (term.equalsIgnoreCase(word)) {
                result = count;
            }
        }
        return result / doc.size();
    }

    /**
     * idf(t,D) = log (N/( n))
     * N은 데이터 세트의 문서 수
     * n은 데이터 세트 중 용어 t를 포함하는 문서의 수
     * @param docs list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    private double idf(List<Map<String, Integer>> docs, String term) {
        double n = 0;
        for (Map<String, Integer> doc : docs) {
            for(Map.Entry<String, Integer> element : doc.entrySet()) {
                String word = element.getKey();
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return 1 + Math.log(docs.size() / n);
    }

    /**
     *
     * @param doc  a text document
     * @param docs all documents
     * @param term term
     * @return the TF-IDF of term
     */
    private double tfIdf(Map<String, Integer> doc, List<Map<String, Integer>> docs, String term) {
        double tf = tf(doc, term);
        double idf = idf(docs, term);
        double score = tf  * idf ;
        return score;

    }

    private Map<String, Integer> getToken(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        Map<String, Integer> result = new HashMap<>();
        while (scanner.hasNext()) {
            String token = scanner.next();
            token = stringReplace(token.toLowerCase());
            if(result.containsKey(token)) {
                int count = result.get(token);
                result.put(token, count+1);
            }else{
                result.put(token, 1);
            }

        }
        return result;
    }

    private String stringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str = str.replaceAll(match, "");
        return str;
    }
}
