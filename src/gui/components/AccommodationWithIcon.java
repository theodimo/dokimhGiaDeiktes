package gui.components;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Icons.*;

public class AccommodationWithIcon extends JPanel {
    //properties
    String name;
    String iconUrl;

    //components
    Label nameLabel; //a label that displays the name of the accommodation
    ImageIcon icon;

    JLabel iconLabel;

    //constructor
    public AccommodationWithIcon(String name) {
        //initialization
        this.name = name;
        this.iconUrl = "src/gui/assets/" + accommodationIcons.get(this.name);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.setPreferredSize(new Dimension(800, 30));

        //components initialization
        this.nameLabel = new Label(this.name, 200, 30, false);
        this.icon = new ImageIcon(this.iconUrl);
        this.iconLabel = new JLabel();
        this.iconLabel.setIcon(this.icon);

        //components adding
        this.add(this.iconLabel);
        this.add(this.nameLabel);
    }

    public void style(Color backgroundColor, Color foregroundColor, Font font) {
        this.setBackground(backgroundColor);
        this.nameLabel.style(backgroundColor, foregroundColor, font);
    }

}
