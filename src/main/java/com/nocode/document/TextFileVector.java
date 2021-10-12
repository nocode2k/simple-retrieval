package com.nocode.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFileVector {

    private final File textFile;

    public double length = 0.0;

    public TextFileVector(File textFile){
        this.textFile = textFile;
    }

    public TokenMapVector getTokenMapVector() {
        TokenMapVector vector = new TokenMapVector();
        Scanner scanner;
        try {
            scanner = new Scanner(this.textFile);
            while (scanner.hasNext()) {
                String token = scanner.next();
                vector.increment(token.toLowerCase());
            }
            this.length = vector.tokenMap.size(); //token count
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return vector;
    }

    @Override
    public String toString() {
        return textFile.getName();
    }

}
