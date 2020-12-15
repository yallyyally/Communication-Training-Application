package com.example.communication_training_application.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UiseongUitaeData implements Serializable {

    @SerializedName("id")
    private int id; //단어 id

    @SerializedName("link")
    private String link; //단어 연관 이미지

    @SerializedName("answer")
    private String answer; //단어

    @SerializedName("desc")
    private String desc; //설명

    @SerializedName("type")
    private String type; //구분(의성,의태)

    @SerializedName("example")
    private String example; //예문

    @SerializedName("extension")
    private String extention; //파일 확장자


    @Override
    public String toString() {
        return "UiseongUitaeData{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", answer='" + answer + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", example='" + example + '\'' +
                ", extention='" + extention + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }
}
