package gui.screens;

import api.Database;
import api.User;
import gui.components.Button;
import gui.components.PasswordField;
import gui.components.TextField;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

/**This is the screen in which a new user can sign up
 * in the application. The data of the user are used
 * to create an instance of the User class which is saved
 * in the database. If the credentials are valid the user
 * can proceed to the next page of the program. The user
 * also has the ability to go in the Log-In screen if he
 * already has an account.
 *
 * @author tzikaman
 */

public class SignUpScreen extends SignTemplate{

    TextField nameField;
    TextField surnameField;
    TextField usernameField;
    PasswordField passwordField;
    PasswordField validatePasswordField;
    JComboBox<String> userType;
    Button signUpButton;
    Button backButton;

        public SignUpScreen(Database api) {
            super("Sign Up",5);

            nameField = new TextField(fieldsWidth/2,fieldsHeight,primaryColor,secondaryColor,accentColor,secondaryColor,"name");
            surnameField = new TextField(fieldsWidth/2,fieldsHeight,primaryColor,secondaryColor,accentColor,secondaryColor,"surname");
            usernameField = new TextField(fieldsWidth,fieldsHeight,primaryColor,secondaryColor,accentColor,secondaryColor,"username");
            passwordField = new PasswordField(fieldsWidth,fieldsHeight,primaryColor,secondaryColor,accentColor,secondaryColor,"password");
            validatePasswordField = new PasswordField(fieldsWidth,fieldsHeight,primaryColor,secondaryColor,accentColor,secondaryColor,"validate password");

            String[] choices = {"simple", "provider"};
            userType = new JComboBox<>(choices);
            userType.setPreferredSize(new Dimension(fieldsWidth,fieldsHeight));
            userType.setBackground(primaryColor);
            userType.setFont(mainFont);
            userType.setSelectedIndex(0);


            signUpButton = new Button("Sign Up",buttonsWidth,buttonsHeight,characterColor,secondaryColor);
            backButton = new Button("Back",buttonsWidth,buttonsHeight,characterColor,secondaryColor);

            //add components
            this.fieldsPanel.add(nameField);
            this.fieldsPanel.add(surnameField);
            this.fieldsPanel.add(usernameField);
            this.fieldsPanel.add(passwordField);
            this.fieldsPanel.add(validatePasswordField);

            this.fieldsPanel.add(userType);


            //add buttons in the screen and action listeners to the buttons

            backButton.addActionListener(e -> {
                //goes back to Log In Screen
                new LogInScreen();
                dispose();
            });
            this.buttonsPanel.add(backButton);

            signUpButton.addActionListener(e -> {
                //getting the information from the fields and combobox
                String name = nameField.getText();
                String surname = surnameField.getText();
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String validatePassword = String.valueOf(validatePasswordField.getPassword());
                String selectedUserType = (String) userType.getSelectedItem();

                //checks weather the credentials are valid and proceeds to the Search Screen
                if(api.validateSignUpCredentials(username,password,validatePassword)){
                    api.createUser(name,surname,username,password,selectedUserType);

                    User user = api.getUser(api.validateSignInCredentials(username,password));
                    new SearchScreen(api, user);
                    dispose();
                }


            });
            this.buttonsPanel.add(signUpButton);

            this.getRootPane().setDefaultButton(signUpButton); //this will automatically listen to the "Enter" key
                                                               //and trigger the action listener of the given button

            this.setVisible(true);
        }
}
