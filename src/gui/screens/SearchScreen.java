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
import java.util.Objects;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class SearchScreen extends JFrame {
    User currentUser;
    HashSet<Integer> searchedLodges;
    int heightOfMinimizedLodge = 60;
    int verticalGap = 25; //the gap between every minimized lodge

    TextField searchBar;
    Button2 searchButton;
    Button2 newButton;
    Button2 viewEntriesButton;
    Button2 logoutButton;

    Button2 allLodgesButton;

    Database db;

    public SearchScreen(Database db){
        this.searchedLodges = new HashSet<>();
        this.db = db;
        this.currentUser = db.getCurrentUser();

        //initialization of Panels
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,100));
        searchPanel.setPreferredSize(new Dimension(1080,250));
        searchPanel.setBackground(secondaryColor);


        JPanel recentSearchesPanel = new JPanel(new BorderLayout(0,0));
        recentSearchesPanel.setPreferredSize(new Dimension(1080,470));
        recentSearchesPanel.setBackground(secondaryColor);


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,200,50));
        titlePanel.setPreferredSize(new Dimension(1080,100));
        titlePanel.setBackground(primaryColor);


        JPanel minimizedLodgesContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,0,this.verticalGap));
        minimizedLodgesContainer.setPreferredSize(new Dimension(1010,300));
        minimizedLodgesContainer.setBackground(primaryColor);

        JScrollPane scrollable = new JScrollPane(minimizedLodgesContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollable.setBorder(new EmptyBorder(0,0,0,0));


        //initialization of components
        searchBar = new TextField(500,50,accentColor2,accentColor3,accentColor1,secondaryColor,"Search");

        searchButton = new Button2("Search",100,50);
        searchButton.addActionListener(e -> {
            minimizedLodgesContainer.removeAll();

            searchedLodges = db.performSearch(searchBar.getText().toLowerCase());
            //System.out.println("these are the searches now" + searchedLodges);

            if(searchedLodges.isEmpty()) {
                JLabel noResultsLabel = new JLabel("Sorry, we couldn't find anything to match '" + searchBar.getText() + "'");
                noResultsLabel.setFont(bigFont);
                noResultsLabel.setForeground(accentColor2);
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

                //resize the minimizedLodgesContainer based on the number of minimized lodges that got added
                //we want to compute the new height of the minimizedLodgesContainer.
                int totalMinimizedLodges = lodgesToDisplay.size();
                int totalHeight = (totalMinimizedLodges + 1) * this.verticalGap; //for x components, there are x+1 gaps around them
                totalHeight += (totalMinimizedLodges) * this.heightOfMinimizedLodge;

                int oldWidth = (int) minimizedLodgesContainer.getPreferredSize().getWidth();
                minimizedLodgesContainer.setPreferredSize(new Dimension(oldWidth, totalHeight));
            }

            minimizedLodgesContainer.revalidate();
            minimizedLodgesContainer.repaint();

        });

        newButton = new Button2("new Lodge", 100, 50);
        newButton.addActionListener(e -> {
            new LodgeProducer(this.db, this.currentUser);
        });

        viewEntriesButton = new Button2("View Entries", 100, 50);
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

        logoutButton = new Button2("Log out", 100,50);
        logoutButton.addActionListener(e -> {
            new LogInScreen();
            this.dispose();
        });

        allLodgesButton = new Button2("All lodges", 100, 50);
        allLodgesButton.addActionListener(e -> {
            new LodgesScreen(this.db);
            this.dispose();
        });

        //buttons styling
        searchButton.style(accentColor1, secondaryColor, primaryColor, mainFont);
        newButton.style(accentColor1, secondaryColor, primaryColor, mainFont);
        logoutButton.style(accentColor1, secondaryColor, primaryColor, mainFont);
        viewEntriesButton.style(accentColor1, secondaryColor, primaryColor, mainFont);
        allLodgesButton.style(accentColor1, secondaryColor, primaryColor, mainFont);

        searchButton.setFocusable(false);
        newButton.setFocusable(false);
        logoutButton.setFocusable(false);
        viewEntriesButton.setFocusable(false);
        allLodgesButton.setFocusable(false);

        /*
        searchButton.style(accentColor1, primaryColor, secondaryColor, mainFont);
        newButton.style(accentColor1, primaryColor, secondaryColor, mainFont);
        logoutButton.style(accentColor1, primaryColor, secondaryColor, mainFont);
        viewEntriesButton.style(accentColor1, primaryColor, secondaryColor, mainFont);
        allLodgesButton.style(accentColor1, primaryColor, secondaryColor, mainFont);
         */

        JLabel title = new JLabel("Your Searches:");
        title.setFont(bigFont);
        title.setForeground(accentColor3);

        //adding components to the frame
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);
        if(Objects.equals(this.currentUser.getType(), "provider"))
            searchPanel.add(newButton);
        searchPanel.add(viewEntriesButton);
        searchPanel.add(allLodgesButton);
        searchPanel.add(logoutButton);
        titlePanel.add(title);
        recentSearchesPanel.add(titlePanel,BorderLayout.NORTH);
        recentSearchesPanel.add(scrollable,BorderLayout.CENTER);

        this.add(searchPanel,BorderLayout.CENTER);
        this.add(recentSearchesPanel,BorderLayout.SOUTH);


        this.getRootPane().setDefaultButton(searchButton); //this will automatically listen to the "Enter" key
                                                           //and trigger the action listener of the given button

        this.setTitle("Search Screen");
        this.setSize(new Dimension(1080,720 + 48));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private ArrayList<MinimizedLodge> convertToMinimized(ArrayList<Lodge> lodges){
        ArrayList<MinimizedLodge> minimizedLodges = new ArrayList<>();

        for (Lodge tempLodge: lodges) {
            MinimizedLodge tempMinimized = new MinimizedLodge(this.db, 800,this.heightOfMinimizedLodge,tempLodge,this.currentUser,secondaryColor,accentColor2,accentColor3,primaryColor);

            minimizedLodges.add(tempMinimized);
        }

        return minimizedLodges;
    }

    public static void main(String[] args) {

        //new SearchScreen(new User("Dimitris", "Tzikas", "tzikaman", "1234", "provider"));
    }
}
