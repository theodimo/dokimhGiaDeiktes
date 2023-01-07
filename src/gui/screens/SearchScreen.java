package gui.screens;

import api.Database;
import api.Lodge;
import api.User;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    Button allLodgesButton;

    Database db;

    public SearchScreen(Database db, User currentUser){
        this.searchedLodges = new HashSet<>();
        this.currentUser = currentUser;
        this.db = db;

        //initialization of Panels
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,100));
        searchPanel.setPreferredSize(new Dimension(1080,250));
        searchPanel.setBackground(primaryColor);


        JPanel recentSearchesPanel = new JPanel(new BorderLayout(0,0));
        recentSearchesPanel.setPreferredSize(new Dimension(1080,470));
        recentSearchesPanel.setBackground(primaryColor);


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,200,50));
        titlePanel.setPreferredSize(new Dimension(1080,100));
        titlePanel.setBackground(primaryColor);


        JPanel minimizedLodgesContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,0,25));
        minimizedLodgesContainer.setPreferredSize(new Dimension(1010,300));
        minimizedLodgesContainer.setBackground(Color.lightGray);
        //recentSearchesPanel.add(minimizedLodgesContainer,BorderLayout.CENTER);

        JScrollPane scrollable = new JScrollPane(minimizedLodgesContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollable.setBorder(new EmptyBorder(0,0,0,0));




        //initialization of components
        searchBar = new TextField(500,50,Color.white,secondaryColor,accentColor,secondaryColor,"Search");

        searchButton = new Button("Search",100,50,characterColor,secondaryColor);
        searchButton.addActionListener(e -> {
            minimizedLodgesContainer.removeAll();

            searchedLodges = db.performSearch(searchBar.getText());
            //System.out.println("these are the searches now" + searchedLodges);

            if(searchedLodges.isEmpty()) {
                JLabel noResultsLabel = new JLabel("Sorry, we couldn't find anything to match ' " + searchBar.getText() + "'");
                noResultsLabel.setFont(titleFont);
                minimizedLodgesContainer.add(noResultsLabel);
            }
            else {
                ArrayList<Lodge> temp = new ArrayList<>();
                for (Integer index : searchedLodges) {
                    temp.add(db.getLodge(index));
                }

                ArrayList<MinimizedLodge> lodgesToDisplay;
                lodgesToDisplay = this.convertToMinimized(temp);

                for (MinimizedLodge lodgeToDisplay : lodgesToDisplay) {
                    minimizedLodgesContainer.add(lodgeToDisplay);
                }
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
            System.out.println("Index of lodges:");
            for (int i = 0; i < this.db.getLodgesCount(); i++) {
                System.out.println(this.db.getLodge(i).getIndex());
            }
            System.out.println("index tou current user");
            for (Integer index: this.currentUser.getLodgeIndexes()) {
                System.out.println(index);
            }
            new ViewEntries(this.db, this.currentUser);
            this.dispose();
        });

        logoutButton = new Button("Log out", 100,50, characterColor, secondaryColor);
        logoutButton.addActionListener(e -> {
            new LogInScreen();
            this.dispose();
        });

        allLodgesButton = new Button("All lodges", 100, 50, characterColor, secondaryColor);
        allLodgesButton.addActionListener(e -> {
            new LodgesScreen(this.db);
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
        searchPanel.add(allLodgesButton);
        titlePanel.add(title);
        recentSearchesPanel.add(titlePanel,BorderLayout.NORTH);
        recentSearchesPanel.add(scrollable,BorderLayout.CENTER);

        this.add(recentSearchesPanel,BorderLayout.SOUTH);
        this.add(searchPanel,BorderLayout.CENTER);


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
            MinimizedLodge tempMinimized = new MinimizedLodge(this.db, 800,60,tempLodge,this.currentUser,primaryColor,secondaryColor,accentColor,accentColor);
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
