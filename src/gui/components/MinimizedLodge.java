package gui.components;

import api.Database;
import api.Lodge;
import api.User;
import gui.screens.LodgeProducer;
import gui.screens.LodgeScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static gui.bootstrap.Fonts.*;
import static gui.bootstrap.Icons.*;
import static gui.bootstrap.Colors.*;

public class MinimizedLodge extends JPanel {
    //properties
    User currentUser;
    Lodge lodge;
    Database db;
    public Button2 maximizeButton;
    public Button2 editButton;
    public Button2 deleteButton;

    public MinimizedLodge(Database db, int width, int height, Lodge lodge, User currentUser, Color backgroundColor, Color foregroundColor, Color titleColor, Color buttonsColor){
        this.setPreferredSize(new Dimension(width,height));
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        this.currentUser = currentUser;
        this.db = db;
        this.lodge = lodge;

        JLabel lodgeName = createNewLabel(lodge.getName(), buttonFont, 300, 30, backgroundColor, titleColor);
        lodgeName.setBackground(backgroundColor);
        JLabel lodgeLocation = createNewLabel(lodge.getCity() + "," + lodge.getAddress() + "," + lodge.getZipCode(),
                smallFont, 30, 30, backgroundColor, foregroundColor);
        JPanel nameAndLocationPanel = new JPanel(new BorderLayout());

        JLabel lodgeType = createNewLabel(lodge.getType(), mainFont, 100, 60, backgroundColor, foregroundColor);

        JLabel lodgeRating = createNewLabel(lodge.getTotalRating() + " (" + lodge.getTotalReviews() + ")", mainFont, 100, 60, backgroundColor, foregroundColor);

        JLabel lodgeId = createNewLabel(String.valueOf(lodge.getIndex()), mainFont, 50, 60, backgroundColor,foregroundColor);

        maximizeButton = new Button2("", 40, 40);
        maximizeButton.style(buttonsColor, buttonsColor, accentColor1, mainFont);
        maximizeButton.setIcon(maximize);
        maximizeButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        maximizeButton.setFocusable(false);
        maximizeButton.addActionListener(e -> {
            new LodgeScreen(db, lodge);
            //this.dispose();
        });

        editButton = new Button2("", 40, 40);
        editButton.style(buttonsColor, buttonsColor, accentColor1, mainFont);
        editButton.setIcon(pencil);
        editButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        editButton.setFocusable(false);
        editButton.addActionListener(e -> {
            new LodgeProducer(this.db, this.currentUser, lodge);
        });

        deleteButton = new Button2("", 40, 40);
        deleteButton.style(buttonsColor, buttonsColor, accentColor1, mainFont);
        deleteButton.setIcon(trashCan);
        deleteButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        deleteButton.setFocusable(false);

        nameAndLocationPanel.add(lodgeName,BorderLayout.NORTH);
        nameAndLocationPanel.add(lodgeLocation,BorderLayout.SOUTH);

        this.add(nameAndLocationPanel);

        this.add(lodgeType);

        this.add(lodgeRating);

        this.add(lodgeId);
    }

    private JLabel createNewLabel(String text, Font font, int width, int height, Color backgroundColor, Color foregroundColor){
        JLabel tempLabel = new JLabel(text, SwingConstants.CENTER);
        tempLabel.setFont(font);
        tempLabel.setPreferredSize(new Dimension(width, height));
        tempLabel.setBackground(backgroundColor);
        tempLabel.setForeground(foregroundColor);
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
