package com.nocode.document;

public class Weight {

    private double value = 0;

    public void increment(double n) {
        this.value = this.value + n;
    }

    public double getValue() {
        return value;
    }
}
