import api.Database;
import api.User;
import gui.screens.LogInScreen;


public class Main {

    public static void main(String[] args) {
        Database db = new Database();
        new LogInScreen(db);


    }

}
