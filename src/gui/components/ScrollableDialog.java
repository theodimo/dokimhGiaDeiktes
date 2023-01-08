package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

import api.Database;
import gui.screens.SearchScreen;

import static gui.bootstrap.Fonts.*;
import static gui.bootstrap.Colors.*;

//this class displays a jdialog. It creates the basic structure of the window, the colors, fonts etc. However, it does not display
//any data because it should be expanded by other dialogs that will put different data into this "vessel"

public abstract class ScrollableDialog extends JDialog {
    //properties
    private int width;
    private int height = 600;
    protected int verticalGap; //the space between each component inside mainPanel. Used at resizing
    private HashMap<String, String[]> accommodations;

    //components
    protected Panel northPanel; //this panel displays a title and a maybe a button
    protected Panel mainPanel; //this panel displays the accommodation categories and the accommodations themselves

    Label titleLabel; //displays the title What this lodge provides

    protected Button2 backButton; //a button that gets you to the SearchScreen

    public ScrollableDialog(Database db, String title, String titleMessage, int width) {
        //initialization
        this.width = width;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.verticalGap = 30;

        //components initialization
        this.northPanel = new Panel(this.width, 50, primaryColor, "preferredSize");
        this.mainPanel = new Panel(this.width, 400, primaryColor, "preferredSize");
        JScrollPane scrollable = new JScrollPane(this.mainPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        this.titleLabel = new Label(titleMessage, 300, this.verticalGap, false);

        this.backButton = new Button2("<- Back", 60, this.verticalGap);


        //layouts
        this.setLayout(new BorderLayout());
        this.northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, this.verticalGap));

        //styling
        this.getContentPane().setBackground(primaryColor);
        this.titleLabel.style(primaryColor, accentColor3, titleFont);
        scrollable.setBorder(new EmptyBorder(0,0,0,0));
        this.backButton.style(accentColor2, secondaryColor, accentColor1, mainFont);
        this.backButton.setFocusable(false);

        //components addition
        this.northPanel.add(this.titleLabel);

        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(scrollable, BorderLayout.CENTER);

        this.setVisible(true);

        //listeners
        this.backButton.addActionListener(e -> {
            new SearchScreen(db);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        //new ScrollableDialog("Accommodations", "What this lodge provides");
    }
}
