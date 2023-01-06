package api;

import java.util.ArrayList;

public class User implements java.io.Serializable, Element<User> {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String type;
    private ArrayList<Lodge> lodgeEntries;

    private ArrayList<Integer> lodgeIndexes; //a list that contains the positions of the lodges that the
    //user owns, inside 'lodges' property in database class

    private ArrayList<Integer> reviewsIndexes; //a list that contains the positions of the reviews that the
    //user has submitted, inside 'reviews' property in database class



    public User(String name, String surname, String username, String password, String type) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.type = type;

        this.lodgeEntries = new ArrayList<>();
        this.lodgeIndexes = new ArrayList<>();
        this.reviewsIndexes = new ArrayList<>();
    }

    public User(String name, String surname, String username, String password, String type, ArrayList<Integer> lodgeIndexes) {
        this(name, surname, username, password, type);
        this.lodgeIndexes = lodgeIndexes;
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

    //Functions
    public void addEntry(Lodge lodge){
        this.lodgeEntries.add(lodge);
    }
    public void removeEntry(Lodge lodge){
        this.lodgeEntries.remove(lodge);
    }

    public ArrayList<Lodge> getLodgeEntries(){
        return this.lodgeEntries;
    }

    public void printUserData() {
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
    }

    /**
     * The user created a new lodge, so we want to add its index, ie its position in lodges property of database, inside lodgesIndexes
     */
    public void addLodgeIndex(int index) {
        this.lodgeIndexes.add(index);
    }

    /**
     * The user submitted, so we want to add its index, ie its position in reviews property of database, inside reviewsIndexes
     */
    public void addReviewIndex(int index) {
        this.reviewsIndexes.add(index);
    }

    /**
     * Remove the id of a lodge from lodgesIndexes property
     * @param index the id of the lodge we want to remove
     */
    public void removeLodgeIndex(int index) {
        this.lodgeIndexes.remove(index);
    }

    /**
     * Remove the id of a review from reviewsIndexes property
     * @param index the id of the lodge we want to remove
     */
    public void removeReviewIndex(int index) {
        this.reviewsIndexes.remove(index);
    }

    public ArrayList<Integer> getLodgeIndexes() {
        return this.lodgeIndexes;
    }

    public ArrayList<Integer> getReviewsIndexes() {
        return this.reviewsIndexes;
    }


     /**
     * This function sets every number inside lodgeIndexes to be one less. The result of this function is to set the lodges that
     * owner have, to be at one place left-more
     * @param index
     */
    public void decreaseIndexes(int index) {
        int i = 0;
        for (Integer currentIndex: this.lodgeIndexes) {
            if (currentIndex > index) {
                this.lodgeIndexes.set(i, currentIndex - 1);
            }
            i += 1;
        }
    }

    /**
     * Creates and returns a copy of the user. With "copy" we mean a new lodge with the identical properties & values
     * @return
     */
    @Override
    public User getCopy() {
        User newUser = new User(this.name, this.surname, this.username, this.password, this.type, this.lodgeIndexes);
        return newUser;
    }
}
