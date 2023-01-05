package api;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import api.DataStructures.AVL;
import api.DataStructures.AVLnode;

//a class that holds the data of our app while its running
public class Database extends StringEditor{
    private ArrayList<User> users;
    private ArrayList<Lodge> lodges;
    private AVL myAVL;

    public Database() {
        //initialization of the objects
        this.users = new ArrayList<>();
        this.lodges = new ArrayList<>();

        //fetch users
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/database/Users.dat"));
            this.users = (ArrayList<User>) in.readObject();
            in.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        //fetch lodges
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/database/Lodges.dat"));
            this.lodges = (ArrayList<Lodge>) in.readObject();
            in.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        //create AVL. It will be used for the searching
        myAVL = new AVL();
        int i = 0;
        ArrayList<String> words;
        for (Lodge lodge: this.lodges) {
            words = lodge.getAllWords();
            System.out.println(words);
            for (String word: words) {
                AVLnode newNode = new AVLnode(word, i);
                myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), newNode));
            }
            i += 1;
        }

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


    public void createUser(String name, String surname, String username, String password, String type) {
        User newUser = new User(name, surname, username, password, type);
        this.users.add(newUser);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/database/Users.dat"));
            out.writeObject(this.users);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Lodge createLodge(User owner,String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations) {
        Lodge newLodge = new Lodge(owner, name, type, address, city, zipCode, description, Accommodations);
        this.lodges.add(newLodge);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/database/Lodges.dat"));
            out.writeObject(this.lodges);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return newLodge;
    }

    public void deleteLodge(Lodge lodgeForDeleting){
        System.out.println(lodgeForDeleting.getAddress());

        System.out.println(this.lodges.remove(lodgeForDeleting));

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/database/Lodges.dat"));
            out.writeObject(this.lodges);
            out.close();

        } catch (Exception e) {
            System.out.println(e);

        }
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

            resultIds.addAll(results);
        }
        System.out.println(resultIds);

        return resultIds;
    }

    public static void main(String[] args) {
        Database db = new Database();
    }
}

