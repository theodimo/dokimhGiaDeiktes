package gui.screens;

import api.Database;
import api.User;
import gui.components.Button;
import gui.components.TitledTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    TitledTextField nameField;
    TitledTextField surnameField;
    TitledTextField usernameField;
    TitledTextField passwordField;
    TitledTextField validatePasswordField;
    JComboBox<String> userType;
    Button signUpButton;
    Button backButton;

        public SignUpScreen(Database api) {
            super("Sign Up", 650, 600 + 48);

            nameField = new TitledTextField("Name","Name","",false);
            surnameField = new TitledTextField("Surname","Surname","",false);
            usernameField = new TitledTextField("Username","Username","This username already exists",false);
            passwordField = new TitledTextField("Password","Password","",true);
            validatePasswordField = new TitledTextField("Validate Password","Validate Password","Not valid password",true);

            String[] choices = {"simple", "provider"};
            userType = new JComboBox<>(choices);
            userType.setPreferredSize(new Dimension(fieldsWidth,fieldsHeight));
            userType.setBackground(secondaryColor);
            userType.setForeground(accentColor2);
            userType.setFont(mainFont);
            userType.setSelectedIndex(0);
            userType.setBorder(new EmptyBorder(0, 10, 0, 0));

            //styling
            nameField.style(primaryColor, secondaryColor, accentColor2, accentColor1);
            surnameField.style(primaryColor, secondaryColor, accentColor2, accentColor1);
            usernameField.style(primaryColor, secondaryColor, accentColor2, accentColor1);
            passwordField.style(primaryColor, secondaryColor, accentColor2, accentColor1);
            validatePasswordField.style(primaryColor, secondaryColor, accentColor2, accentColor1);


            signUpButton = new Button("Sign Up",buttonsWidth,buttonsHeight,secondaryColor,accentColor1);
            backButton = new Button("Back",buttonsWidth,buttonsHeight,secondaryColor,accentColor1);

            //add components
            this.fieldsPanel.add(nameField);
            this.fieldsPanel.add(surnameField);
            this.fieldsPanel.add(usernameField);
            this.fieldsPanel.add(userType);
            this.fieldsPanel.add(passwordField);
            this.fieldsPanel.add(validatePasswordField);


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
                String password = String.valueOf(passwordField.getText());
                String validatePassword = String.valueOf(validatePasswordField.getText());
                String selectedUserType = (String) userType.getSelectedItem();

                //checks weather the credentials are valid and proceeds to the Search Screen
                if(api.validateUsername(username) == -1){
                    usernameField.showErrorMessage(false);

                    if(api.validateSignUpCredentials(username,password,validatePassword)){
                        api.createUser(name,surname,username,password,selectedUserType);

                        User user = api.getUser(api.validateSignInCredentials(username,password));
                        api.setCurrentUser(user);
                        new SearchScreen(api);
                        dispose();
                    }
                    else {
                        validatePasswordField.showErrorMessage(true);
                    }

                }
                else {
                    usernameField.showErrorMessage(true);
                }


            });
            this.buttonsPanel.add(signUpButton);

            this.getRootPane().setDefaultButton(signUpButton); //this will automatically listen to the "Enter" key
                                                               //and trigger the action listener of the given button

            this.setVisible(true);
        }
}
