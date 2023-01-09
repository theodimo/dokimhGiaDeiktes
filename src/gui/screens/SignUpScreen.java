package gui.screens;

import api.Database;
import api.User;
import gui.components.Button;
import gui.components.Button2;
import gui.components.TitledTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
    Button2 signUpButton;
    Button2 backButton;

        public SignUpScreen(Database db) {
            super("Sign Up", 650, 600 + 48);

            nameField = new TitledTextField("Name","Name","",false);
            surnameField = new TitledTextField("Surname","Surname","",false);
            usernameField = new TitledTextField("Username","Username","This username already exists",false);
            passwordField = new TitledTextField("Password","Password","",true);
            validatePasswordField = new TitledTextField("Validate Password","Validate Password","Not valid password",true);
            signUpButton = new Button2("Sign Up",buttonsWidth,buttonsHeight);
            backButton = new Button2("Back",buttonsWidth,buttonsHeight);

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
            signUpButton.style(accentColor2, primaryColor, accentColor1, mainFont);
            signUpButton.setBorder(new LineBorder(accentColor1, 2));
            signUpButton.setFocusable(false);
            backButton.style(accentColor2, primaryColor, accentColor1, mainFont);
            backButton.setBorder(new LineBorder(accentColor1, 2));
            backButton.setFocusable(false);


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
                new LogInScreen(db);
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
                if(db.validateUsername(username) == -1){
                    usernameField.showErrorMessage(false);

                    if(db.validateSignUpCredentials(username,password,validatePassword)){
                        db.createUser(name,surname,username,password,selectedUserType);

                        new SearchScreen(db);
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
