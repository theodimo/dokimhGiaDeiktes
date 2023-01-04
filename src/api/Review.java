package api;

import api.User;

public class Review {
    //properties
    private User author; //the user that wrote this particular review
    private String text; //the review
    private float rating; //the rating of the review

    private String date; //the creation date of the review

    //constructor
    public Review(User author, String text, float rating, String date) {
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

    public float getRating() {
        return this.rating;
    }

    public String getDate() {
        return this.date;
    }
}
