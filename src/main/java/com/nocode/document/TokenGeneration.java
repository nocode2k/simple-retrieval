package com.nocode.document;

/**
 * Posting information class
 */
public class TokenGeneration {
    public TextFileVector textDocument;
    public int count;
    //tf(t,d) = n/N
    //n은 문서 d에 용어 t가 나타나는 횟수
    //N은 문서 d의 총 용어 수
    public double tf;

    public TokenGeneration(TextFileVector textDocument, int count) {
        this.textDocument = textDocument;
        this.count = count;
        this.tf = count/textDocument.length;
    }

    @Override
    public String toString() {
        return "TokenGeneration in " + textDocument + ", count: " + count;
    }
}
