package com.nocode;

import com.nocode.index.IndexReader;
import com.nocode.index.IndexWriter;
import com.nocode.search.SimpleQuery;
import com.nocode.utils.FileUtil;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;

public class RetrievalExample {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String query = "ranking";
        System.out.println("검색어 > " + query);
        long start = System.currentTimeMillis();
        IndexWriter indexWriter = new IndexWriter();
        List<Path> textDataFiles = FileUtil.getPathsFromResource("data");
        if(textDataFiles.isEmpty()) {
            return;
        }
        for(Path inputFile : textDataFiles) {
            indexWriter.indexDocument(inputFile.toFile());
        }
        indexWriter.computeIDFDocument();
        long end = System.currentTimeMillis();
        System.out.println("색인 수행시간: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        IndexReader indexReader = new IndexReader(indexWriter.getTokenHash());
        SimpleQuery simpleQuery = new SimpleQuery();
        simpleQuery.processQueries(query, indexReader);

        end = System.currentTimeMillis();
        System.out.println("검색 수행시간: " + (end - start) + " ms");
    }

}
