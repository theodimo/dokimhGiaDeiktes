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



    public User(String name, String surname, String username, String password, String type) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.type = type;

        this.lodgeEntries = new ArrayList<>();
        this.lodgeIndexes = new ArrayList<>();
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

    public void addLodgeIndex(int index) {
        this.lodgeIndexes.add(index);
    }

    public ArrayList<Integer> getLodgeIndexes() {
        return this.lodgeIndexes;
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
