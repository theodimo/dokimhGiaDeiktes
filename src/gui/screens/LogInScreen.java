package gui.screens;

import api.Database;
import api.User;
import gui.components.Button;
import gui.components.PasswordField;
import gui.components.TextField;
import gui.components.TitledTextField;

import static gui.bootstrap.Colors.*;

/**This is the screen in which a user can Log In
 * the application. The class checks if the given
 * credentials exist in the database and if they are
 * correct the user proceeds in the next screen of the
 * program. The user also has the ability to go in the
 * Sign-Up screen if he doesn't have an account.
 *
 * @author tzikaman
 */
public class LogInScreen extends SignTemplate {
    private final TitledTextField usernameField;
    private final TitledTextField passwordField;
    private Button logInButton;
    private Button backButton;
    public LogInScreen() {
        super("Log In",5);
        Database api = new Database();

        //initializing the components
        usernameField = new TitledTextField("Username","username","This username doesn't exist",false);
        passwordField = new TitledTextField("Password","password","Wrong password",true);

        logInButton = new Button("Log In",buttonsWidth,buttonsHeight,characterColor,secondaryColor);
        backButton = new Button("Back",buttonsWidth,buttonsHeight,characterColor,secondaryColor);


        // add text fields to the screen
        this.fieldsPanel.add(usernameField);
        this.fieldsPanel.add(passwordField);

        // add action listeners in buttons
        // and add buttons to the screen
        backButton.addActionListener(e -> {
            new SignUpScreen(api);
            dispose();
        });
        this.buttonsPanel.add(backButton);


        logInButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();

            if(api.validateUsername(username) == -1){
                usernameField.showErrorMessage(true);
                passwordField.showErrorMessage(false);
            }
            else{
                try{
                    User user = api.getUser(api.validateSignInCredentials(username,password));

                    new SearchScreen(api, user);
                    dispose();
                }catch (Exception ex) {
                    usernameField.showErrorMessage(false);
                    passwordField.showErrorMessage(true);
                }
            }

        });
        this.buttonsPanel.add(logInButton);

        this.getRootPane().setDefaultButton(logInButton); //this will automatically listen to the "Enter" key
                                                          //and trigger the action listener of the given button

        this.setVisible(true);
    }

    public static void main(String[] args) {

        LogInScreen p = new LogInScreen();
    }

}
