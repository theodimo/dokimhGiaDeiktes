package gui.components;

import gui.bootstrap.Colors;

import javax.swing.*;
import java.awt.*;

public class Accommodation extends JPanel {
    //properties
    public String name;
    public int titleIndex;

    //components
    public Button2 deleteButton;
    Label titleLabel;

    public Accommodation(String name, int titleIndex, int width, int height) {
        //initialization
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(false);
        this.name = name;
        this.titleIndex = titleIndex;
        this.deleteButton = new Button2("x", height, height);
        this.deleteButton.setFocusable(false);
        this.titleLabel = new Label(name, width - height - 5, height, false);

        //layout
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        //adding components
        this.add(this.deleteButton);
        this.add(this.titleLabel);

    }

    public void style(Color backgroundColor, Color foregroundColor, Color hoverColor, Font font) {
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        this.setFont(font);
        this.deleteButton.style(foregroundColor, backgroundColor, hoverColor, font);
        this.titleLabel.style(backgroundColor, foregroundColor, font);
    }
}
