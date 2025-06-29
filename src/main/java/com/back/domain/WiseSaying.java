package com.back.domain;


public class WiseSaying {
    private long id;
    private String author;
    private String content;

    public WiseSaying(long id,String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public long getId() { return id;}

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
