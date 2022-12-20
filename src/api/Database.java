package api;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import api.DataStructures.AVL;
import api.DataStructures.AVLnode;

//a class that holds the data of our app while its running
public class Database {
    private ArrayList<User> users;
    private ArrayList<Lodge> lodges;

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

        AVL myAVL = new AVL();
        AVLnode n1 = new AVLnode("H", 5);
        AVLnode n2 = new AVLnode("D", 6);
        AVLnode n3 = new AVLnode("A", 3);
        AVLnode n4 = new AVLnode("A", 1);
        AVLnode n5 = new AVLnode("A", 2);
        AVLnode n6 = new AVLnode("K", 1);
        AVLnode n7 = new AVLnode("K", 5);
        AVLnode n8 = new AVLnode("J", 6);
        AVLnode n9 = new AVLnode("I", 8);

        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n1));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n2));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n3));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n4));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n5));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n6));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n7));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n8));
        myAVL.setRoot(myAVL.insertNode(myAVL.getRoot(), n9));

        myAVL.printTree(myAVL.getRoot());
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
    private int validateUsername(String username) {
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

    public void createLodge(String name, String type, String address, String city, int zipCode, String description, HashMap<String,String[]> Accommodations) {
        Lodge newLodge = new Lodge(name, type, address, city, zipCode, description, Accommodations);
        this.lodges.add(newLodge);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/database/Lodges.dat"));
            out.writeObject(this.lodges);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Database db = new Database();
    }
}

