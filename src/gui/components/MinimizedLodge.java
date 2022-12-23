package gui.components;

import api.Lodge;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.mainFont;
import static gui.bootstrap.Icons.*;

public class MinimizedLodge extends JPanel {

    public JButton maximizeButton = new JButton();
    public JButton editButton = new JButton();
    public JButton deleteButton = new JButton();

    public MinimizedLodge(int width, int height, Lodge lodge, Color backgroundColor, Color foregroundColor, Color borderColor){
        this.setPreferredSize(new Dimension(width,height));
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        //this.setBorder(new RoundedLineBorder(borderColor, 1, 10, true));

        JLabel lodgeName = createNewLabel(lodge.getName(), mainFont, 200, height-20, Color.white, secondaryColor);
        JLabel lodgeType = createNewLabel(lodge.getType(), mainFont, 100, height-20, Color.white, secondaryColor);
        JLabel lodgeLocation = createNewLabel(lodge.getCity(), mainFont, 100, height-20, Color.white, secondaryColor);
        JLabel lodgeRating = createNewLabel("rating", mainFont, 80, height-20, Color.lightGray, secondaryColor);

        maximizeButton.setIcon(rect);
        maximizeButton.setBackground(Color.magenta);
        maximizeButton.setPreferredSize(new Dimension(40,40));
        maximizeButton.addActionListener(e -> {
            //new MaximizedLodge(lodge);
            //this.dispose();
        });

        editButton.setIcon(pencil);
        editButton.setBackground(primaryColor);
        editButton.setPreferredSize(new Dimension(40,40));

        deleteButton.setIcon(trashCan);
        deleteButton.setBackground(primaryColor);
        deleteButton.setPreferredSize(new Dimension(40,40));


        this.add(lodgeName);
        this.add(lodgeType);
        this.add(lodgeLocation);
        this.add(lodgeRating);

        this.addMaximizeButton();
        this.addEditButtons();
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
}
