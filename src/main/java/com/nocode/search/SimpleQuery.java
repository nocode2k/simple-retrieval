package com.nocode.search;

import com.nocode.document.DocComparator;
import com.nocode.document.TokenMapVector;
import com.nocode.document.TextStringVector;
import com.nocode.index.IndexReader;

public class SimpleQuery {

    public void processQueries(String query, IndexReader indexReader) {
        TokenMapVector searchVector = (new TextStringVector()).getTokenMapVector(query);
        DocComparator[] docComparators = indexReader.docComparator(searchVector);
        indexReader.printResultDocument(docComparators);
    }
}
