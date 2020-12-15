package com.example.communication_training_application.model;

import com.google.gson.annotations.SerializedName;

public class UiseongUitaeData {

    @SerializedName("id")
    private int id; //단어 id

    @SerializedName("link")
    private String link; //단어 연관 이미지

    @SerializedName("answer")
    private String answer; //단어

    @SerializedName("desc")
    private String desc; //설명

    @Override
    public String toString() {
        return "UiseongUitaeData{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", answer='" + answer + '\'' +
                ", desc='" + desc + '\'' +
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
}
