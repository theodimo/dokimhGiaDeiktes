package gui.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static gui.bootstrap.Fonts.smallFont;

public class TitledTextArea extends JPanel {
    //properties
    Color primaryColor;

    //components
    Panel titlePanel;
    Panel areaPanel;
    Panel errorPanel;

    Label titleLabel;
    Label errorLabel;
    TextArea area;

    //constructor
    public TitledTextArea(int width, int height, String title, String defaultMessage, String errorMessage, int maxCharacters) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());

        //components initialization
        this.titlePanel = new Panel(width, 20, null, "preferredSize");
        this.areaPanel = new Panel(width, height - 40, null, "preferredSize");
        this.errorPanel = new Panel(width, 20, null, "preferredSize");

        this.titleLabel = new Label(title, width, 20, false);
        this.errorLabel = new Label(errorMessage, width, 20, false);

        this.area = new TextArea(defaultMessage, width, height - 40, maxCharacters);

        //layouts
        this.titlePanel.setLayout(null);
        this.errorPanel.setLayout(null);

        this.titleLabel.setBounds(5, 0, width, 20);
        this.errorLabel.setBounds(5, 0, width, 20);

        //components addition
        this.titlePanel.add(this.titleLabel);
        this.areaPanel.add(this.area);
        this.errorPanel.add(this.errorLabel);

        this.add(this.titlePanel, BorderLayout.NORTH);
        this.add(this.areaPanel, BorderLayout.CENTER);
        this.add(this.errorPanel, BorderLayout.SOUTH);
    }

    /**
     * Styles the class with the given 4 colours
     * @param primaryColor The background color of north,south panels
     * @param secondaryColor The background color of the center panel
     * @param accentColor1 The foreground color of the center panel, and its border
     * @param accentColor2 The foreground color the north panel
     * @param font The font of the area
     */
    public void style(Color primaryColor, Color secondaryColor, Color accentColor1, Color accentColor2, Font font) {
        this.primaryColor = primaryColor;

        this.titlePanel.setBackground(primaryColor);
        this.areaPanel.setBackground(primaryColor);
        this.errorPanel.setBackground(primaryColor);

        this.area.style(secondaryColor, accentColor1, accentColor2, accentColor1, font);

        this.titleLabel.setForeground(accentColor2);
        this.errorLabel.setForeground(primaryColor);
        this.errorLabel.setFont(smallFont);

        this.revalidate();
        this.repaint();
    }


    public void showErrorMessage(boolean enable){
        if (enable) {
            this.errorLabel.setForeground(Color.red);
        }
        else {
            this.errorLabel.setForeground(this.primaryColor);
        }
    }

    /**
     * Sets the default text of the area in the center panel
     * @param text
     */
    public void setText(String text) {
        this.area.setText(text);
    }

    /**
     * Sets the color of the area in the center panel
     * @param foregroundColor  the desired foreground <code>Color</code>
     */
    public void setForegroundColor(Color foregroundColor) {
        this.area.setForeground(foregroundColor);
    }

    /**
     * @return the color of the field in the center panel
     */
    public Color getForegroundColor() {
        return this.area.getForegroundColor();
    }

    public void setCurrentCharacters(int characters) {
        this.area.setCurrentCharacters(characters);
    }

    public String getText(){
        return this.area.getText();
    }


}
