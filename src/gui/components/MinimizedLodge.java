package gui.components;

import api.Database;
import api.Lodge;
import api.User;
import gui.screens.LodgeProducer;
import gui.screens.LodgeScreen;

import javax.swing.*;
import java.awt.*;

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

        maximizeButton.setIcon(maximize);
        maximizeButton.setBackground(buttonsColor);
        maximizeButton.setPreferredSize(new Dimension(40,40));
        maximizeButton.setFocusable(false);
        maximizeButton.addActionListener(e -> {
            new LodgeScreen(db, lodge);
            //this.dispose();
        });

        editButton.setIcon(pencil);
        editButton.setBackground(buttonsColor);
        editButton.setPreferredSize(new Dimension(40,40));
        editButton.addActionListener(e -> {
            new LodgeProducer(this.db, this.currentUser, lodge);
        });

        deleteButton.setIcon(trashCan);
        deleteButton.setBackground(buttonsColor);
        deleteButton.setPreferredSize(new Dimension(40,40));


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
