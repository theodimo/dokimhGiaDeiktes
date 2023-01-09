package api;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.round;

public class Lodge extends StringEditor implements java.io.Serializable {
    // fields of Lodge class
    private String name;
    private String type;
    private String address;
    private String city;
    private int zipCode;
    private String description;
    private User owner;
    private HashMap<String, String[]> Accommodations;
    private ArrayList<Review> reviews; //a list with all reviews that have been submited for this lodge
    private float totalRating;
    private int index; //the position of this lodge inside the "lodges" property of database object

    // constructors of Lodge
    public Lodge(User owner, String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations, int index){
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.description = description;

        this.Accommodations = Accommodations;
        this.index = index;

        this.reviews = new ArrayList<>();
        this.totalRating = 0;
    }
    public Lodge(User owner,String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations, ArrayList<Review> review, int index, float totalRating){
        this(owner,name, type, address, city, zipCode, description, Accommodations, index);

        this.reviews = reviews;
        this.totalRating = totalRating;
    }

    // setters & getters of every field
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String[]> getAccommodations() {
        return Accommodations;
    }

    public void setAccommodations(HashMap<String, String[]> accommodations) {
        this.Accommodations = accommodations;
    }

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }

    public Review getReview(int index) {
        return this.reviews.get(index);
    }

    public void addReview(Review review, Database db) {
        this.reviews.add(review);
        this.totalRating = (float) (round(calculateTotalRating(db, this.reviews) * 10.0) / 10.0);
    }

    public float getTotalRating(){
        return totalRating;
    }
    public void setTotalRating(int rating) {
        this.totalRating = rating;
    }
    public void updateTotalRating(Database db){
        this.totalRating = (float) (round(calculateTotalRating(db, this.reviews) * 10.0) / 10.0);
    }

    public float calculateTotalRating(Database db, ArrayList<Review> reviews){
        int sum = 0;
        int count = 0;

        if(reviews.size() == 0)
            return 0;

        for (Review rev: reviews) {
            sum += rev.getRating();
            count++;
        }

        this.totalRating = (float) sum / (float) count;

        return this.totalRating;
    }

    public int getIndex() {
        return this.index;
    }

    //functions
    /**
     * This function gathers all words that exist in a lodge inside an ArrayList. Specifically, these words can be found
     * at name, address, city, accommodations and all other lodge fields.
     * @return then ArrayList
     */
    public ArrayList<String> getAllWords() {
        //initialization
        ArrayList<String> finalWords = new ArrayList<>();
        ArrayList<String> words;

        //name decomposition
        words = this.decompose(this.name.toLowerCase());
        finalWords = this.concatenate(finalWords, words);

        //address decomposition
        words = this.decompose(this.address.toLowerCase());
        finalWords = this.concatenate(finalWords, words);

        //description decomposition
        words = this.decompose(this.description.toLowerCase());
        finalWords = this.concatenate(finalWords, words);

        //accommodations decomposition
        for (String accommodationCategory: this.Accommodations.keySet()) {
            for (String accommodationName: this.Accommodations.get(accommodationCategory)) {
                words = this.decompose(accommodationName.toLowerCase());
                finalWords = this.concatenate(finalWords, words);
            }
        }

        //add one-word fields
        finalWords.add(this.type.toLowerCase());
        finalWords.add(this.city.toLowerCase());
        finalWords.add( (this.zipCode + "").toLowerCase() );

        return finalWords;
    }


    /*
    /**
     * Creates and returns a copy of the lodge. With "copy" we mean a new lodge with the identical properties & values
     * @return

    @Override
    public Lodge getCopy() {
        User newUser = this.owner.getCopy();
        Lodge newLodge = new Lodge(newUser, this.name, this.type, this.address, this.city, this.zipCode, this.description, this.Accommodations, this.reviewsIndexes, this.index, this.totalRating);
        return newLodge;
    }

    */

    public int getTotalReviews() {
        return this.reviews.size();
    }

}