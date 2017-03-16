package com.example.bht.bookreader.Model;

/**
 * Created by bht on 3/16/17.
 */

public class Chapter {
    private int id;
    private int novelId;
    private int number;
    private String description;
    private String content;

    public Chapter() {
    }

    public Chapter(int id, int novelId, int number, String description, String content) {
        this.id = id;
        this.novelId = novelId;
        this.number = number;
        this.description = description;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
