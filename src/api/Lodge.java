package api;

import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<String> Reviews;

    // constructors of Lodge
    public Lodge(String name, String type, String address, String city, int zipCode, String description) {
        //the minimal constructor
        this.name = name;
        this.type = type;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.description = description;
        this.Accommodations = new HashMap<>();
        this.Reviews = new ArrayList<>();
    }
    public Lodge(User owner,String name, String type, String address, String city, int zipCode, String description) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.description = description;
        this.Accommodations = new HashMap<>();
        this.Reviews = new ArrayList<>();
    }

    public Lodge(User owner,String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations){
        this(owner,name, type, address, city, zipCode, description);
        this.Accommodations = Accommodations;
    }
    public Lodge(User owner,String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations, ArrayList<String> Reviews){
        this(owner,name, type, address, city, zipCode, description, Accommodations);
        this.Reviews = Reviews;
    }

    public Lodge(User owner,String name, String type, String address, String city, int zipCode, String description, User user, HashMap<String,String[]> Accommodations, ArrayList<String> Reviews){
        this(owner,name, type, address, city, zipCode, description, Accommodations, Reviews);
        this.owner = user;
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

    public void setAccommodations(String accommodation,String[] accommodations) {
        Accommodations.put(accommodation,accommodations);
    }

    public ArrayList<String> getReviews() {
        return Reviews;
    }

    public void setReviews(String reviews) {
        Reviews.add(reviews);
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
        words = this.decompose(this.name);
        finalWords = this.concatenate(finalWords, words);

        //address decomposition
        words = this.decompose(this.address);
        finalWords = this.concatenate(finalWords, words);

        //description decomposition
        words = this.decompose(this.description);
        finalWords = this.concatenate(finalWords, words);

        //accommodations decomposition
        for (String accommodationCategory: this.Accommodations.keySet()) {
            for (String accommodationName: this.Accommodations.get(accommodationCategory)) {
                words = this.decompose(accommodationName);
                finalWords = this.concatenate(finalWords, words);
            }
        }

        //add one-word fields
        finalWords.add(this.type);
        finalWords.add(this.city);
        finalWords.add(this.zipCode + "");

        return finalWords;
    }
}
