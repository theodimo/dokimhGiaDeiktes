package api;

import javax.xml.crypto.Data;
import java.util.ArrayList;

import static java.lang.Math.round;

public class User implements java.io.Serializable {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String type;

    private ArrayList<Lodge> lodges; //a list containing all the lodges that the user owns


    private ArrayList<Review> reviews; //a list containing all the reviews that the user submitted



    public User(String name, String surname, String username, String password, String type) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.type = type;

        this.lodges = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public User(String name, String surname, String username, String password, String type, ArrayList<Lodge> lodges, ArrayList<Review> reviews) {
        this(name, surname, username, password, type);
        this.lodges = lodges;
        this.reviews = reviews;
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getType() {
        return this.type;
    }

    public int getReviewsMade(){
        return this.reviews.size();
    }
    public int getReviewsReceived(Database db){
        int sum = 0;
        for (Lodge lodge: this.lodges) {
            sum += lodge.getReviews().size();
        }

        return sum;
    }
    public float getOverallRatingReceived(Database db){
        float ratingSum = 0;
        int count = 0;

        for (Lodge lodge : this.lodges) {
            ratingSum += lodge.getTotalRating();
            count++;
        }

        if(count != 0)
            return (float) (round((ratingSum / count) * 10.0) / 10.0);
        else
            return 0;
    }

    public float getOverallRatingMade(Database db){
        float ratingSum = 0;
        int count = 0;

        for (Review review: this.reviews) {
            ratingSum += review.getRating();
            count++;
        }

        if(count != 0)
            return (float) (round( (ratingSum / count) * 10.0 ) / 10.0);
        else
            return 0;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    //Functions
    public void printUserData() {
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
    }

    /**
     * The user created a new lodge, so we want to add it to the list of lodges
     */
    public void addLodge(Lodge lodge) {
        this.lodges.add(lodge);
    }

    public void removeLodge(Lodge lodge) {
        this.lodges.remove(lodge);
    }

    /**
     * The user submitted a review, so we want to add it to the reviews
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }


    /**
     * Remove the review from reviews
     * @param review the review to be removed
     */
    public void removeReviewIndex(Review review) {
        this.reviews.remove(review);
    }

    public ArrayList<Lodge> getLodges() {
        return this.lodges;
    }

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }


    /*
    /**
     * Creates and returns a copy of the user. With "copy" we mean a new lodge with the identical properties & values
     * @return

    @Override
    public User getCopy() {
        User newUser = new User(this.name, this.surname, this.username, this.password, this.type, this.lodgeIndexes, this.reviewsIndexes);
        return newUser;
    }
    */
}
