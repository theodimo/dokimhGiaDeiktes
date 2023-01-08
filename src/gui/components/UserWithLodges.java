package gui.components;

import api.Database;
import api.Lodge;
import api.User;

import static gui.bootstrap.Fonts.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

//this class is responsible for displaying the full name of a user and the lodges he owns
public class UserWithLodges extends JPanel implements ScrollableElement {
    //properties
    User owner; //the user whose lodges we want to display
    int width;

    private int verticalGap; //the space between each component inside mainPanel. Used at resizing

    int totalLodges = 0;


    //components
    Panel namePanel; //the panel that will wrap the nameLabel
    Panel mainPanel; //the panel that displays the lodges of the user

    Label nameLabel; //the label that displays the name of user

    public UserWithLodges(User owner, Database db, int width, Color primaryColor, Color secondaryColor, Color accentColor1, Color accentColor2) {
        //initialization
        this.owner = owner;
        this.width = width;;
        this.verticalGap = 10;
        this.totalLodges = owner.getLodgeIndexes().size();

        //components initialization
        this.namePanel = new Panel(this.width, 30, primaryColor, "preferredSize");
        this.mainPanel = new Panel(this.width, this.computeMainPanelHeight(this.verticalGap), primaryColor, "preferredSize");

        this.nameLabel = new Label(this.owner.getName() + " " + this.owner.getSurname(), 200, 30, false);


        //layouts
        this.setLayout(new BorderLayout());
        this.namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 300, this.verticalGap));

        //styling
        this.nameLabel.style(primaryColor, accentColor2, inputLabel);
        this.setBorder(new LineBorder(accentColor2, 2));

        //creating the minimizedLodges
        for (Integer id: this.owner.getLodgeIndexes()) {
            Lodge lodge = db.getLodge(id);
            MinimizedLodge minimizedLodge = new MinimizedLodge(db, this.width - 40, 60, lodge, db.getCurrentUser(), secondaryColor, accentColor1, accentColor2, primaryColor);

            this.mainPanel.add(minimizedLodge);
        }

        //components addition
        this.namePanel.add(this.nameLabel);

        this.add(this.namePanel, BorderLayout.NORTH);
        this.add(this.mainPanel, BorderLayout.CENTER);
    }

    private int computeMainPanelHeight(int gap) {
        int totalHeight = this.totalLodges * 60; //because i set the height of all minimizedLodges to be 60px
        totalHeight += (this.totalLodges + 1) * gap;
        return totalHeight;
    }

    @Override
    public int computeHeight() {
        int totalHeight = this.computeMainPanelHeight(10); //cause i set the vertical gap of mainPanel to be 10px
        totalHeight += this.namePanel.getPreferredSize().getHeight();
        return totalHeight;
    }
}
