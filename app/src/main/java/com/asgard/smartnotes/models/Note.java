package com.asgard.smartnotes.models;

import com.asgard.smartnotes.Importance;

public class Note {

    private String header;
    private String body;
    private int importance;
    private String date;
    private int id;

    public Note(String header, String body, @Importance int importance, String date, int id) {
        this.header = header;
        this.body = body;
        this.importance = importance;
        this.date = date;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(@Importance int importance) {
        this.importance = importance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
