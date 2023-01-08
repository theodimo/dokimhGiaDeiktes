package api;

import api.User;

import java.io.Serializable;

public class Review implements java.io.Serializable, Element<Review> {
    //properties
    private User author; //the user that wrote this particular review
    private String text; //the review text
    private int rating; //the rating of the review (out of 5)

    private String date; //the creation date of the review

    private int index; //the position of this review in the reviews Arraylist in the Database class

    //constructor
    public Review(User author, String text, int rating, String date, int index) {
        //initialization
        this.author = author;
        this.text = text;
        this.rating = rating;
        this.date = date;
        this.index = index;
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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return this.date;
    }

    public int getIndex() {
        return this.index;
    }

    public void decreaseIndex() {
        this.index -= 1;
    }

    @Override
    public Review getCopy() {
        Review newReview = new Review(this.author, this.text, this.rating, this.date, this.index);
        return newReview;
    }
}
