package gui.screens;

import api.Database;
import api.Lodge;
import api.User;
import gui.components.Button2;
import gui.components.MinimizedLodge;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class ViewEntries extends JFrame {
    //properties
    Database db;
    int verticalGap = 40;
    int heightOfMinimizedLodge = 60;

    private ArrayList<MinimizedLodge> entries;
    private User currentUser;
    public ViewEntries(Database db, User currentUser){
        this.db = db;
        this.currentUser = currentUser;
        this.entries = new ArrayList<>();
        this.entries = this.convertToMinimized(currentUser.getLodgeIndexes());

        //Panels initialization
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,50));
        titlePanel.setPreferredSize(new Dimension(1080,125));
        titlePanel.setBackground(secondaryColor);
        this.add(titlePanel,BorderLayout.NORTH);

        JPanel generalInfoPanel = new JPanel(new BorderLayout());
        generalInfoPanel.setPreferredSize(new Dimension(1080, 125));
        generalInfoPanel.setBackground(secondaryColor);
        this.add(generalInfoPanel,BorderLayout.SOUTH);

        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,5));
        userInfoPanel.setPreferredSize(new Dimension(300,125));
        userInfoPanel.setBackground(secondaryColor);
        generalInfoPanel.add(userInfoPanel,BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING,20,35));
        buttonsPanel.setPreferredSize(new Dimension(780,125));
        buttonsPanel.setBackground(secondaryColor);
        generalInfoPanel.add(buttonsPanel,BorderLayout.EAST);

        JPanel entriesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,35,this.verticalGap));
        entriesPanel.setPreferredSize(new Dimension(1080,470));
        entriesPanel.setBackground(primaryColor);
        JScrollPane scrollable = new JScrollPane(entriesPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollable.setBorder(new EmptyBorder(0,0,0,0));
        this.add(scrollable,BorderLayout.CENTER);

        //Components initialization
        JLabel userNameLabel = new JLabel(currentUser.getName() + " " + currentUser.getSurname());
        userNameLabel.setFont(bigFont);
        userNameLabel.setForeground(accentColor3);

        JLabel totalReviewsPanel = new JLabel("Total Reviews: " + currentUser.getTotalReviewCount());
        totalReviewsPanel.setFont(mainFont);
        totalReviewsPanel.setForeground(accentColor2);

        JLabel overallRatingPanel = new JLabel("Overall Rating: " + currentUser.getOverallRating());
        overallRatingPanel.setFont(mainFont);
        overallRatingPanel.setForeground(accentColor2);

        Button2 newEntryButton = new Button2("+ New Entry", 100, 50);
        newEntryButton.style(accentColor2, primaryColor, accentColor1, mainFont);
        newEntryButton.setFocusable(false);
        newEntryButton.addActionListener(e -> {
            new LodgeProducer(this.db, currentUser);
        });

        Button2 backButton = new Button2("Back", 100, 50);
        backButton.style(accentColor2, primaryColor, accentColor1, mainFont);
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            new SearchScreen(this.db);
            this.dispose();
        });

        JLabel titleLabel = new JLabel("View Entries");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(accentColor3);

        //Adding components to the frame
        titlePanel.add(titleLabel);

        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(totalReviewsPanel);
        userInfoPanel.add(overallRatingPanel);


        buttonsPanel.add(backButton);
        buttonsPanel.add(newEntryButton);

        for (MinimizedLodge lodgeToAdd : entries) {
            //add listener to delete the lodge
            lodgeToAdd.deleteButton.addActionListener(e -> {
                System.out.println("klithika");
                //delete the lodge from the database
                this.db.deleteLodge(lodgeToAdd.getLodge(), currentUser);

                //build again the avl tree because we don't longer want the words of the deleted lodge to be there
                this.db.createAVL();

                //when deleting a lodge, make sure to remove its minimizedLodge from entriesPanel
                entriesPanel.remove(lodgeToAdd);

                this.db.printData();

                //refresh the frame
                revalidate();
                repaint();
            });
            entriesPanel.add(lodgeToAdd);
        }

        //resize the entriesPanel based on the number of minimizedLodges added
        //we want to compute the new height of the entriesPanel
        int totalMinimizedLodges = entries.size();
        int totalHeight = (totalMinimizedLodges + 1) * this.verticalGap; //for x components, there are x+1 gaps around them
        totalHeight += totalMinimizedLodges * this.heightOfMinimizedLodge;

        int oldWidth = (int) entriesPanel.getPreferredSize().getWidth();
        entriesPanel.setPreferredSize(new Dimension(oldWidth, totalHeight));

        this.setTitle("View Entries");
        this.setSize(new Dimension(1080,720 + 48));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }



    private ArrayList<MinimizedLodge> convertToMinimized(ArrayList<Integer> lodgesIndexes){
        ArrayList<MinimizedLodge> minimizedLodges = new ArrayList<>();


        for (Integer index: lodgesIndexes) {
            Lodge tempLodge = this.db.getLodge(index);
            MinimizedLodge tempMinimized = new MinimizedLodge(this.db,800,this.heightOfMinimizedLodge,tempLodge,this.currentUser,secondaryColor,accentColor2,accentColor3,primaryColor);

            minimizedLodges.add(tempMinimized);
        }

        return minimizedLodges;
    }

    public static void main(String[] args){
        //new ViewEntries(null);
    }
}


