package io.molenaar.module.wordFrequencyAnalyzer.domain;

import io.molenaar.assignment.WordFrequency;

public class WordFrequencyImp implements WordFrequency {

    private String word;
    private int frequency;

    public String getWord() {
        return this.word;
    }

    public WordFrequencyImp setWord(String word){
        this.word = word;
        return this;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public WordFrequencyImp setFrequency(int frequency){
        this.frequency = frequency;
        return this;
    }
}
