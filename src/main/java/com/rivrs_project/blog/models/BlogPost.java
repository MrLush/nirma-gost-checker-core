package com.rivrs_project.blog.models;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Entity
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String title;
    private String anons;
    @Column(columnDefinition = "TEXT")
    private String body;
    private int views = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void newView(){
        this.views += 1;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public BlogPost() {
    }

    public BlogPost(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public BlogPost(String title, String anons, String body) {
        this.title = title;
        this.anons = anons;
        this.body = body;
    }
/*
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

 */
}
