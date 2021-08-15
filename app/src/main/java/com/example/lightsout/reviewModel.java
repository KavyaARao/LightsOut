package com.example.lightsout;

public class reviewModel {

    private String review;
    private int rating;
    private String author;

    // Constructor
    public reviewModel(String review,int rating,String author) {
        this.review = review;
        this.rating=rating;
        this.author=author;
    }

    // Getter and Setter
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review =review;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author =author;
    }

    public void setRating(int rating){this.rating=rating;}
    public int getRating(){return rating;}
}
