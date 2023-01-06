package gui.screens;

import api.Database;
import api.Lodge;
import api.User;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class SearchScreen extends JFrame {
    User currentUser;
    HashSet<Integer> searchedLodges;

    TextField searchBar;
    Button searchButton;
    Button newButton;
    Button viewEntriesButton;
    Button logoutButton;

    Database db;

    public SearchScreen(Database db, User currentUser){
        this.searchedLodges = new HashSet<>();
        this.currentUser = currentUser;
        this.db = db;

        //initialization of Panels
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,100));
        searchPanel.setPreferredSize(new Dimension(1080,250));
        searchPanel.setBackground(primaryColor);
        this.add(searchPanel,BorderLayout.CENTER);

        JPanel recentSearchesPanel = new JPanel(new BorderLayout(0,0));
        recentSearchesPanel.setPreferredSize(new Dimension(1080,470));
        recentSearchesPanel.setBackground(primaryColor);
        this.add(recentSearchesPanel,BorderLayout.SOUTH);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,200,50));
        titlePanel.setPreferredSize(new Dimension(1080,100));
        titlePanel.setBackground(primaryColor);
        recentSearchesPanel.add(titlePanel,BorderLayout.NORTH);

        JPanel minimizedLodgesContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,0,25));
        minimizedLodgesContainer.setPreferredSize(new Dimension(1010,300));
        minimizedLodgesContainer.setBackground(Color.lightGray);
        recentSearchesPanel.add(minimizedLodgesContainer,BorderLayout.CENTER);



        //initialization of components
        searchBar = new TextField(500,50,Color.white,secondaryColor,accentColor,secondaryColor,"Search");

        searchButton = new Button("Search",100,50,characterColor,secondaryColor);
        searchButton.addActionListener(e -> {
            minimizedLodgesContainer.removeAll();

            searchedLodges = db.performSearch(searchBar.getText());
            //System.out.println("these are the searches now" + searchedLodges);


            ArrayList<Lodge> temp = new ArrayList<>();
            for (Integer index : searchedLodges) {
                temp.add(db.getLodge(index));
            }

            ArrayList<MinimizedLodge> lodgesToDisplay;
            lodgesToDisplay = this.convertToMinimized(temp);

            for (MinimizedLodge lodgeToDisplay : lodgesToDisplay) {
                minimizedLodgesContainer.add(lodgeToDisplay);
            }

            minimizedLodgesContainer.revalidate();
            minimizedLodgesContainer.repaint();

        });

        newButton = new Button("new Lodge", 100, 50, characterColor, secondaryColor);
        newButton.addActionListener(e -> {
            new LodgeProducer(this.db, this.currentUser);
        });

        viewEntriesButton = new Button("View Entries", 100, 50, characterColor, secondaryColor);
        viewEntriesButton.addActionListener(e -> {
            new ViewEntries(this.db, this.currentUser);
            this.dispose();
        });

        logoutButton = new Button("Log out", 100,50, characterColor, secondaryColor);
        logoutButton.addActionListener(e -> {
            new LogInScreen();
            this.dispose();
        });

        JLabel title = new JLabel("Your Searches:");
        title.setFont(buttonFont);

        //adding components to the frame
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);
        searchPanel.add(newButton);
        searchPanel.add(logoutButton);
        searchPanel.add(viewEntriesButton);
        titlePanel.add(title);

        this.getRootPane().setDefaultButton(searchButton); //this will automatically listen to the "Enter" key
                                                           //and trigger the action listener of the given button

        this.setTitle("Search Screen");
        this.setSize(new Dimension(1080,720 + 48));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private ArrayList<MinimizedLodge> convertToMinimized(ArrayList<Lodge> lodges){
        ArrayList<MinimizedLodge> minimizedLodges = new ArrayList<>();

        for (Lodge tempLodge: lodges) {
            MinimizedLodge tempMinimized = new MinimizedLodge(this.db, 655,60,tempLodge,this.currentUser,primaryColor,secondaryColor,accentColor);
            tempMinimized.addMaximizeButton();
            tempMinimized.addEditButtons();

            minimizedLodges.add(tempMinimized);
        }

        return minimizedLodges;
    }

    public static void main(String[] args) {

        //new SearchScreen(new User("Dimitris", "Tzikas", "tzikaman", "1234", "provider"));
    }
}
