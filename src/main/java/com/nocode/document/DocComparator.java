package com.nocode.document;

public class DocComparator implements Comparable<Object>{

    public TextFileVector textFileVector;

    public double score;

    public DocComparator(TextFileVector textFileVector, double score) {
        this.textFileVector = textFileVector;
        this.score = score;
    }

    @Override
    public int compareTo(Object obj) {
        DocComparator docComparator = (DocComparator) obj;
        return Double.compare(docComparator.score, score);
    }
}
