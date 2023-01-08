package api;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import api.DataStructures.AVL;
import api.DataStructures.AVLnode;

//a class that holds the data of our app while its running
public class Database extends StringEditor {
    private ArrayList<User> users;
    private ArrayList<Lodge> lodges;
    private ArrayList<Review> reviews;
    private AVL myAVL;

    private User currentUser;

    public Database() {
        //initialization of the objects
        this.users = new ArrayList<>();
        this.lodges = new ArrayList<>();
        this.reviews = new ArrayList<>();

        //fetch users
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/database/Users.dat"));
            ArrayList<User> oldUsers = (ArrayList<User>) in.readObject();
            for (User user: oldUsers) {
                this.users.add(user.getCopy());
            }
            in.close();
        } catch(Exception e) {
            System.out.println("error fortwnontas ta users");
            System.out.println(e);
        }

        //fetch lodges
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/database/Lodges.dat"));
            ArrayList<Lodge> oldLodges = (ArrayList<Lodge>) in.readObject();
            for (Lodge lodge: oldLodges) {
                this.lodges.add(lodge.getCopy());
            }
            in.close();
        } catch(Exception e) {
            System.out.println("error fortwnontas ta lodges");
            System.out.println(e);
        }

        //fetch reviews
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/database/Reviews.dat"));
            ArrayList<Review> oldReviews = (ArrayList<Review>) in.readObject();
            for (Review review: oldReviews) {
                this.reviews.add(review.getCopy());
            }
            in.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        //create AVL. It will be used for the searching
        this.createAVL();


        this.printData();

        System.out.println("neo programma");
        this.printReviewData();
    }

    //getters
    /**
     * Returns the user at the position: index
     * @param index the position of the user in the database
     * @return the user
     */
    public User getUser(int index) {
        return this.users.get(index);
    }

    /**
     * Returns the lodge at the position: index
     * @param index the position of the lodge in the database
     * @return the lodge
     */
    public Lodge getLodge(int index) {
        return this.lodges.get(index);
    }

    /**
     * Returns the review at the position: index
     * @param index the position of the review in the database
     * @return the review
     */
    public Review getReview(int index) {
        return this.reviews.get(index);
    }

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }


    public User getCurrentUser() {
        return this.currentUser;
    }

    //setters
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }



    //functions
     /**
     * Checks if a user with the given username exists in the database
     * @param username: The name of the user we want to check his existence
     * @return An integer that represents the position of the user in the database. If user does not exist, return -1
     */
    public int validateUsername(String username) {
        boolean found = false;
        int foundPosition = -1, i = 0;
        String currentUsername;
        while (!found && i < this.users.size()) {
            currentUsername = this.users.get(i).getUsername();
            found = username.equals(currentUsername);
            i += 1;
        }
        if (found) {
            foundPosition = i - 1;
        }
        return foundPosition;
    }

     /**
     * Checks if there is a user registered with the given credentials in the database.
     * @param username: The name of the user we want to check about his existence
     * @param password: The password of the user we want to check about his existence
     * @return an integer that represents the position of the potential user in the database. If user does not exist
     * return -1
     */
    public int validateSignInCredentials(String username, String password) {
        //check if there is any user with the given username
        int foundPosition = -1;
        int pos = this.validateUsername(username);
        if (pos != -1) {
            //if the user exists, validate his password
            if (Objects.equals(this.users.get(pos).getPassword() , password)) {
                foundPosition = pos;
            }
        }
        return foundPosition;
    }


     /**
     * Checks if a new account with the given credentials can be created. An account can be created, if there isn't already
     * a user that has the same username with the given username, and if the user typed correctly the password he wants to have.
     * @param username: The username of the possibly new user.
     * @param password: The password of the possibly new user.
     * @param confirmPassword: The confirmation of the password of the possibly new user.
     * @return True if the validation is successfull, false otherwise.
     */
    public boolean validateSignUpCredentials(String username, String password, String confirmPassword) {
        boolean successfullValidation = false;
        //check if there is already a user with the same username
        int pos = this.validateUsername(username);
        if (pos == -1) {
            //if an account with the given username can be created, check if password matches confirmPassword
            successfullValidation = Objects.equals(password, confirmPassword);
        }
        return successfullValidation;
    }

    /**
     * Checks if the title of the lodge that is going to be created, is validated.
     * A title is validated if it consists from 6 to 20 characters
     * @param title
     * @return
     */
    public boolean validateLodgeTitle(String title) {
        return title.length() >= 6 && title.length() <= 20;
    }

    /**
     * Checks if the address of the lodge that is going to be created, is validated.
     * An address is validated if it consists from 10 to 20 characters
     * @param address
     * @return
     */
    public boolean validateLodgeAddress(String address) {
        return address.length() >= 10 && address.length() <= 20;
    }

    /**
     * Checks if the city of the lodge that is going to be created, is validated
     * A city is validated if it consists from 4 to 16 characters
     * @param city
     * @return
     */
    public boolean validateLodgeCity(String city) {
        return city.length() >= 4 && city.length() <= 16;
    }

    /**
     * Checks if the zipCode of the lodge that is going to be created, is validated
     * A zipCode is validated if it consists of 5 numeric digits
     * @param zipCode
     * @return 0 if zipCode is validated,
     *        -1 if zipCode's length is different from 5,
     *         1 if it doesn't consist of numeric digits
     */
    public int validateLodgeZipCode(String zipCode) {
        if (zipCode.length() == 5 && zipCode.matches("[0-9]+")) {
            return 0;
        } else if (zipCode.length() != 5) {
            return -1;
        } {
            return 1;
        }
    }

    /**
     * Checks if the description of the lodge that is going to be created, is validated
     * A description is validated if it is at least 50 characters long
     * @param text
     * @return
     */
    public boolean validateLodgeDescription(String text) {
        return text.length() >= 50;
    }


    public void createUser(String name, String surname, String username, String password, String type) {
        User newUser = new User(name, surname, username, password, type);
        this.users.add(newUser);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/database/Users.dat"));
            out.writeObject(this.users);
            out.close();
        } catch (Exception e) {
            System.out.println("error ftiaxnontas ton neo user");
            System.out.println(e);
        }
    }

    public Lodge createLodge(User owner,String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations) {
        Lodge newLodge = new Lodge(owner, name, type, address, city, zipCode, description, Accommodations, this.lodges.size());
        this.lodges.add(newLodge);

        this.saveLodges();

        //put the words of the new lodge to the avl tree
        ArrayList<String> words = newLodge.getAllWords();
        for (String word: words) {
            AVLnode newNode = new AVLnode(word, this.lodges.size() - 1);
            myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), newNode));
        }

        return newLodge;
    }

    public Review createReview(User author,String reviewText, int rating, String date) {
        Review newReview = new Review(author, reviewText, rating, date, this.reviews.size());
        this.reviews.add(newReview);

        this.saveReviews();

        System.out.println("I have created a review");
        this.printReviewData();

        return newReview;
    }



    /**
     * Deletes the lodge from the local object database and from the files
     * @param lodgeForDeleting
     * @param owner
     */
    public void deleteLodge(Lodge lodgeForDeleting, User owner){
        //firstly, we want the lodges to have an ascending index with step 1. By removing a lodge, we should decrease the
        //index of all lodges with bigger index, in order to maintain the order

        System.out.println("before deleting:");
        for (Lodge lodge: this.lodges) {
            System.out.println(lodge.getIndex() + " -> " + lodge.getName());
        }
        int deletedIndex = lodgeForDeleting.getIndex();
        for (int i = deletedIndex + 1; i < this.lodges.size(); i++) {
            this.lodges.get(i).decreaseIndex();
        }

        //then, delete the lodge from the lodges property
        this.lodges.remove(lodgeForDeleting);

        System.out.println("after deleting:");
        for (Lodge lodge: this.lodges) {
            System.out.println(lodge.getIndex() + " -> " + lodge.getName());
        }

        //remove the index of the lodge from owner's lodgeIndexes
        owner.removeLodgeIndex(deletedIndex);

        //furthermore, decrease the indexes of all owner's lodges that have index higher that deletedIndex, for every user
        //to understand it better lets say that User1 and User2 have lodges at indexes:
        //User1 -> 0, 1
        //User2 -> 2
        //if User1 deletes his first lodge, we want the new data to be:
        //User1 -> 0
        //User2 -> 1
        //cause the lodges had indexes 0 1 2 and after the deletion they have 0 1
        for (User user: this.users) {
            user.decreaseLodgeIndexes(deletedIndex);
        }


        //save the changes to database files
        this.saveLodges();
        this.saveUsers();
    }

    public void deleteReview(Review reviewForDeleting, User author, Lodge reviewedLodge){
        //firstly, we want the lodges to have an ascending index with step 1. By removing a lodge, we should decrease the
        //index of all lodges with bigger index, in order to maintain the order

        System.out.println("prin thn diagrafh");
        this.printReviewData();

        System.out.println("before deleting:");
        for (Review review: this.reviews) {
            System.out.println(review.getIndex() + " -> " + review.getText());
        }
        int deletedIndex = reviewForDeleting.getIndex();
        for (int i = deletedIndex + 1; i < this.reviews.size(); i++) {
            this.reviews.get(i).decreaseIndex();
        }

        //then, delete the lodge from the lodges property
        this.reviews.remove(reviewForDeleting);

        System.out.println("after deleting:");
        for (Review review: this.reviews) {
            System.out.println(review.getIndex() + " -> " + review.getText());
        }

        System.out.println("meta th diagrafh");
        this.printReviewData();

        //remove the index of the review from owner's and lodge's reviewIndexes
        author.removeReviewIndex(deletedIndex);
        reviewedLodge.removeReviewIndex(deletedIndex);
        reviewedLodge.updateTotalRating(this);



        //furthermore, decrease the indexes of all owner's lodges that have index higher that deletedIndex, for every user
        //to understand it better lets say that User1 and User2 have lodges at indexes:
        //User1 -> 0, 1
        //User2 -> 2
        //if User1 deletes his first lodge, we want the new data to be:
        //User1 -> 0
        //User2 -> 1
        //cause the lodges had indexes 0 1 2 and after the deletion they have 0 1
        for (User user: this.users) {
            user.decreaseReviewsIndexes(deletedIndex);
        }

        //auto den kserw an leitourgei
        for (Lodge lodge : this.lodges) {
            lodge.decreaseReviewsIndexes(deletedIndex);
        }


        //save the changes to database files
        this.saveReviews();
        this.saveUsers();
        this.saveLodges();

        System.out.println("telos diagrafhs");
        this.printReviewData();
    }

    /**
     * Performs searching based on the text that user gives. The searching
     * in the AVL that contains all the words for each registered Lodge
     *
     * @param text: the text that we need to perform the search for
     * @return a HashSet of the ids of the Lodges that match the given text
     */
    public HashSet<Integer> performSearch(String text){
        ArrayList<String> WordsForSearching = this.decompose(text);
        HashSet<Integer> resultIds = new HashSet<>();

        for (String word : WordsForSearching) {
            ArrayList<Integer> results = myAVL.find(myAVL.getRoot(), word);
            if (results != null) {
                resultIds.addAll(results);
            }
        }

        return resultIds;
    }

     /**
     * Saves the object "this.lodges" inside database at the file Lodges.dat
     */
    public void saveLodges() {
        try {
            ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream("src/database/Lodges.dat", false));
            out.writeObject(this.lodges);
            out.close();
        } catch(Exception e) {
            System.out.println("error kanontas save ta lodges");
            System.out.println(e);
        }
    }

    /**
     * Saves the object "this.users" inside database at the file Users.dat
     */
    public void saveUsers() {
        try {
            ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream("src/database/Users.dat", false));
            out.writeObject(this.users);
            out.close();
        } catch(Exception e) {
            System.out.println("error kanontas save ta ussers");
            System.out.println(e);
        }
    }

    /**
     * Saves the object "this.reviews" inside database at the file Reviews.dat
     */
    public void saveReviews() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/database/Reviews.dat", false));
            out.writeObject(this.reviews);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

     /**
     * Creates the avl tree. The nodes of avl will be words that describe a lodge. These words exist in lodge's properties, ie
     * title, address, city, description etc
     */
    public void createAVL() {
        this.myAVL = new AVL();
        int i = 0;
        ArrayList<String> words;
        for (Lodge lodge: this.lodges) {
            words = lodge.getAllWords();
            for (String word: words) {
                AVLnode newNode = new AVLnode(word, i);
                myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), newNode));
            }
            i += 1;
        }
    }

    /**
     * @return the number of lodges registered in database
     */
    public int getLodgesCount() {
        return this.lodges.size();
    }

    /**
     * @return the number of reviews registered in database
     */
    public int getReviewsCount() {
        return this.reviews.size();
    }

    /**
     * @return the number of users registered in database
     */
    public int getUsersCount() {
        return this.users.size();
    }

    public void clearFiles() {
        try {
            ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream("src/database/Lodges.dat", false));
            out.writeObject(null);
            out.close();
        } catch(Exception e) {
            System.out.println("error kanontas save ta lodges");
            System.out.println(e);
        }
    }

    public void printData() {
        System.out.println("----------------");
        System.out.println("Users:");
        for (User user: this.users) {
            System.out.println(user.getName() + " " + user.getSurname());
            System.out.println("me indexes: ");
            System.out.println(user.getLodgeIndexes());
        }
        System.out.println("Lodges");
        System.out.println("Ola ta indexes apo ta lodges:");
        for (Lodge lodge: this.lodges) {
            System.out.println(lodge.getIndex() + "-> " + lodge.getOwner().getName() + " " + lodge.getOwner().getSurname());
        }
        System.out.println("----------------");
    }

    public void printReviewData() {
        System.out.println("**************");

        System.out.println("Users:");
        for (User user: this.users) {
            System.out.println(user.getName() + " " + user.getSurname());
            System.out.println("Me Review indexes: ");
            System.out.println(user.getReviewsIndexes());
        }

        System.out.println("Reviews:");
        System.out.println("Ola ta indexes apo ta reviews:");
        for (Review review : this.reviews) {
            System.out.println(review.getIndex() + "-> " + review.getAuthor().getUsername() + " " + review.getRating() + "/5" + " " + review.getText());
        }

        System.out.println("**************");
    }

    public static void main(String[] args) {
        Database db = new Database();
    }
}

