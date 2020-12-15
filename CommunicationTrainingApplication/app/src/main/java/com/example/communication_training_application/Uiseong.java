package com.example.communication_training_application;

import java.io.Serializable;

public class Uiseong implements Serializable {
    private String word;
    private String meaning;
    private String ex;

    public Uiseong(String word, String meaning, String ex){
        this.word = word;
        this.meaning = meaning;
        this.ex = ex;
    }

    public String getWord(){
        return word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public String getMeaning(){
        return meaning;
    }

    public void setMeaning(String meaning){
        this.meaning = meaning;
    }

    public String getEx(){
        return ex;
    }

    public void setEx(String ex){
        this.ex = ex;
    }
}
