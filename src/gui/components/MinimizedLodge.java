package gui.components;

import api.Database;
import api.Lodge;
import api.User;
import gui.screens.LodgeScreen;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;
import static gui.bootstrap.Icons.*;

public class MinimizedLodge extends JPanel {
    //properties
    User currentUser;

    Lodge lodge;

    Database db;
    public JButton maximizeButton = new JButton();
    public JButton editButton = new JButton();
    public JButton deleteButton = new JButton();

    public MinimizedLodge(Database db, int width, int height, Lodge lodge, User currentUser, Color backgroundColor, Color foregroundColor, Color borderColor){
        this.setPreferredSize(new Dimension(width,height));
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        this.currentUser = currentUser;
        this.db = db;
        this.lodge = lodge;
        //this.setBorder(new RoundedLineBorder(borderColor, 1, 10, true));

        JLabel lodgeName = createNewLabel(lodge.getName(), buttonFont, 300, 30, Color.white, secondaryColor);
        lodgeName.setBackground(primaryColor);
        JLabel lodgeLocation = createNewLabel(lodge.getCity() + "," + lodge.getAddress() + "," + lodge.getZipCode(),
                smallFont, 30, 30, primaryColor, secondaryColor);
        JPanel nameAndLocationPanel = new JPanel(new BorderLayout());


        JLabel lodgeType = createNewLabel(lodge.getType(), mainFont, 100, 60, primaryColor, secondaryColor);

        JLabel lodgeRating = createNewLabel("4.5 (15)", mainFont, 100, 60, primaryColor, secondaryColor);

        maximizeButton.setIcon(maximize);
        maximizeButton.setBackground(primaryColor);
        maximizeButton.setPreferredSize(new Dimension(40,40));
        maximizeButton.addActionListener(e -> {
            new LodgeScreen(db, lodge);
            //this.dispose();
        });

        editButton.setIcon(pencil);
        editButton.setBackground(primaryColor);
        editButton.setPreferredSize(new Dimension(40,40));
        editButton.addActionListener(e -> {
            new LodgeProducer(this.db, this.currentUser, lodge);
        });

        deleteButton.setIcon(trashCan);
        deleteButton.setBackground(primaryColor);
        deleteButton.setPreferredSize(new Dimension(40,40));


        nameAndLocationPanel.add(lodgeName,BorderLayout.NORTH);
        nameAndLocationPanel.add(lodgeLocation,BorderLayout.SOUTH);

        this.add(nameAndLocationPanel);

        this.add(lodgeType);

        this.add(lodgeRating);
    }

    private JLabel createNewLabel(String text, Font font, int width, int height, Color backgroundColor, Color foregroundColor){
        JLabel tempLabel = new JLabel(text, SwingConstants.CENTER);
        tempLabel.setFont(font);
        tempLabel.setPreferredSize(new Dimension(width, height));
        tempLabel.setBackground(backgroundColor);
        tempLabel.setOpaque(true);

        return tempLabel;
    }

    public void addMaximizeButton(){
        this.add(maximizeButton);

    }

    public void addEditButtons(){
        this.add(editButton);
        this.add(deleteButton);
    }

    public Lodge getLodge() {
        return this.lodge;
    }
}
