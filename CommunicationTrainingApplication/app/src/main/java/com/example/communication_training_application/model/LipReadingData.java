package com.example.communication_training_application.model;

import com.google.gson.annotations.SerializedName;

public class LipReadingData {

    @SerializedName("id")
    private int id;

    @SerializedName("link")
    private String link;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("answer")
    private String answer;

    @SerializedName("type")
    private String type;

    @Override
    public String toString() {
        return "LipReadingData{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", answer='" + answer + '\'' +
                ", type='" + type + '\'' +
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
