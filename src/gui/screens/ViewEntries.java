package gui.screens;

import api.Lodge;
import api.User;
import gui.components.Button;
import gui.components.LodgeProducer;
import gui.components.MinimizedLodge;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class ViewEntries extends JFrame {

    private ArrayList<MinimizedLodge> entries;
    private User currentUser;
    public ViewEntries(User currentUser){
        this.currentUser = currentUser;
        this.entries = new ArrayList<>();
        this.entries = this.convertToMinimized(currentUser.getLodgeEntries());

        //Panels initialization
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,50));
        titlePanel.setPreferredSize(new Dimension(1080,125));
        titlePanel.setBackground(Color.lightGray);
        this.add(titlePanel,BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,100,35));
        buttonsPanel.setPreferredSize(new Dimension(1080,125));
        buttonsPanel.setBackground(primaryColor);
        this.add(buttonsPanel,BorderLayout.CENTER);

        JPanel entriesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,35,100));
        entriesPanel.setPreferredSize(new Dimension(1080,470));
        entriesPanel.setBackground(primaryColor);
        this.add(entriesPanel,BorderLayout.SOUTH);

        //Components initialization
        Button newEntryButton = new Button("+ New Entry", 100, 50, characterColor, secondaryColor);
        newEntryButton.addActionListener(e -> {
            new LodgeProducer(currentUser);
        });

        Button backButton = new Button("Back", 100, 50, characterColor, secondaryColor);
        backButton.addActionListener(e -> {
            new SearchScreen(currentUser);
            this.dispose();
        });

        JLabel titleLabel = new JLabel("View Entries");
        titleLabel.setFont(titleFont);



        //Adding components to the frame
        titlePanel.add(titleLabel);
        buttonsPanel.add(backButton);
        buttonsPanel.add(newEntryButton);

        for (MinimizedLodge lodgeToAdd : entries) {
            entriesPanel.add(lodgeToAdd);
        }





        this.setTitle("View Entries");
        this.setSize(new Dimension(1080,720 + 48));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }



    private ArrayList<MinimizedLodge> convertToMinimized(ArrayList<Lodge> lodges){
        ArrayList<MinimizedLodge> minimizedLodges = new ArrayList<>();

        for (Lodge tempLodge: lodges) {
            MinimizedLodge tempMinimized = new MinimizedLodge(655,60,tempLodge,primaryColor,secondaryColor,accentColor);
            tempMinimized.addMaximizeButton();
            tempMinimized.addEditButtons();

            minimizedLodges.add(tempMinimized);
        }

        return minimizedLodges;
    }

    public static void main(String[] args){
        new ViewEntries(null);
    }
}


