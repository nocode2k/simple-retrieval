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
        IndexWriter indexWriter = new IndexWriter();
        List<Path> textDataFiles = FileUtil.getPathsFromResource("data");
        if(textDataFiles.isEmpty()) {
            return;
        }
        for(Path inputFile : textDataFiles) {
            indexWriter.indexDocument(inputFile.toFile());
        }
        indexWriter.computeIDFDocument();
        IndexReader indexReader = new IndexReader(indexWriter.getTokenHash());
        SimpleQuery simpleQuery = new SimpleQuery();
        simpleQuery.processQueries("ranking queries", indexReader);
    }

}
