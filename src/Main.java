import api.User;

import java.io.*;
import java.util.*;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    //contains the paths for each data file
    private static HashMap<String, String> databasePaths = new HashMap<>();

    public static void main(String[] args) {
        User newUser = new User("Dimos", "Theocharis", "thanatopios7", "1234", "user");
        //constructor funtionality
        databasePaths.put("users", "../database/users.dat");


        //User newUser = signUp();
        users.add(newUser);
        newUser = signIn();

    }

    public static User signUp() {
        Scanner scanner = new Scanner(System.in);

        String name, surname, username, type;
        String password;
        int confirmationPassword;
        char submit;

        System.out.println("---- SIGN UP PAGE ----\n");

        //give credentials
        System.out.println("Give name: ");
        name = scanner.nextLine();

        System.out.println("Give surname: ");
        surname = scanner.nextLine();

        System.out.println("Give username: ");
        username = scanner.nextLine();

        System.out.println("Give password: ");
        password = scanner.nextLine();

        System.out.println("Give type: ");
        type = scanner.nextLine();
        scanner.next();

        System.out.println("Want to submit? (Y/N)");
        submit = scanner.next().charAt(0);


        //validate the username of the user
        boolean found = false;
        int i = 0;
        while (!found && i < users.size()) {
            found = users.get(i).getName().equals(name);
            i += 1;
        }

        //the procedure implemented if username already exists
        while (found) {
            System.out.println("This username already exists!");
            System.out.println("Give username: ");
            username = scanner.nextLine();
            found = false;
            i = 0;
            while (!found && i < users.size()) {
                found = users.get(i).getName().equals(name);
                i += 1;
            }
        }

        User newUser = new User(name, surname, username, password, type);
        return newUser;
    }

    public static void saveUser(User user) {
        users.add(user);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(databasePaths.get("users")));
            out.writeObject(users);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }



    }

    public static User signIn(){
        Scanner scanner = new Scanner(System.in);
        String username;
        int password;
        boolean credentials_true = false;
        User validUser = null;


        System.out.println("---- SIGN IN PAGE ----\n");


        while(!credentials_true) {

            System.out.println("Give username: ");
            username = scanner.next();
            System.out.println(username);

            System.out.println("Give password: ");
            password = scanner.nextInt();
            System.out.println(password);

            for (User temp : users) {

                if (Objects.equals(temp.getUsername(), username)) {
                    if(Objects.equals(temp.getPassword() , password)){
                        validUser = temp;
                        credentials_true = true;
                    }
                    else
                        System.out.println("Wrong password, try again:");
                }
                else
                    System.out.println("No such username, try again:");
            }
        }
        System.out.println("Welcome to the app!");
        return validUser;
    }
}
