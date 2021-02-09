package com.hrmelo.books.models;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonView;

public class Book {

    public interface Simplified {}
    
    public interface Complete extends Simplified {}

    @JsonView(Simplified.class)
    private long id;

    @JsonView(Simplified.class)
    private String title;

    @JsonView(Complete.class)
    private String author;

    @JsonView(Complete.class)
    private String summary;

    @JsonView(Complete.class)
    private String editorial;

    @JsonView(Complete.class)
    private int year;

    private ConcurrentMap<Long, Comment> comments = new ConcurrentHashMap<>();

    private AtomicLong nextCommentId = new AtomicLong();

    public Book(String title, String author, String summary, String editorial, int year) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.editorial = editorial;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String resume) {
        this.summary = resume;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Collection<Comment> getComments() {
        return this.comments.values();
    }

    public void createComment(Comment comment) {
        long commentId = nextCommentId.getAndIncrement();
        comment.setId(commentId);
        this.comments.put(commentId, comment);
    }

    public void deleteComment(long commentId) {
        this.comments.remove(commentId);
    }
}
