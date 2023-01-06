package api;

import api.User;

import java.io.Serializable;

public class Review implements java.io.Serializable {
    //properties
    private User author; //the user that wrote this particular review
    private String text; //the review text
    private int rating; //the rating of the review (out of 5)

    private String date; //the creation date of the review

    //constructor
    public Review(User author, String text, int rating, String date) {
        //initialization
        this.author = author;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    //getters
    public User getAuthor() {
        return this.author;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return this.rating;
    }

    public String getDate() {
        return this.date;
    }
}
