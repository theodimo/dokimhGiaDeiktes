package gui.screens;

import api.Database;
import api.User;
import gui.components.ScrollableDialog;
import gui.components.ScrollableDialogInterface;
import gui.components.UserWithLodges;

import java.awt.*;

//this dialog is responsible for displaying the accommodations of every user
public class LodgesScreen extends ScrollableDialog implements ScrollableDialogInterface<UserWithLodges> {
    //properties
    private static Color primaryColor = new Color(21, 47, 80);
    private static Color secondaryColor = new Color(5, 68, 94);
    private static Color accentColor = new Color(212, 241, 244);

    public static Color dark = new Color(20, 20, 20);

    //constructor
    public LodgesScreen(Database db) {
        super(db, "Lodges Screen", "All Lodges", 880);

        for (User user: db.getUsers()) {
            //create the container
            UserWithLodges container = new UserWithLodges(user, db, 800, primaryColor, secondaryColor, accentColor, dark);
            //add the container
            this.addElement(container);
        }

        //add the back button at the north panel. These properties are given from the super class
        this.northPanel.add(this.backButton);

        //resize the main panel
        this.resizeMainPanel(db.getUsersCount());

        this.revalidate();
        this.repaint();
    }

    @Override
    public void resizeMainPanel(int componentCount) {
        int height = (componentCount + 1) * this.verticalGap;
        for (Component comp: this.mainPanel.getComponents()) {
            UserWithLodges container = (UserWithLodges) comp;
            height += container.computeHeight();
        }
        int oldWidth = (int) this.mainPanel.getPreferredSize().getWidth();
        this.mainPanel.setPreferredSize(new Dimension(oldWidth, height));
    }

    @Override
    public void addElement(UserWithLodges component) {
        this.mainPanel.add(component);
    }
}
