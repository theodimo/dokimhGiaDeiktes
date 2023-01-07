package gui.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class TitledTextField extends JPanel {
    private String errorMessage;
    private Color primaryColor;
    private TextField field;

    private JPanel titlePanel;
    private JPanel errorPanel;
    private JPanel textFieldPanel;
    private JLabel errorLabel;
    private JLabel titleLabel;

    public TitledTextField (String title, String defaultMessage, String errorMessage, boolean isPasswordField){
        this.errorMessage = errorMessage;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250,90));

        //panels
        this.titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
        this.titlePanel.setPreferredSize(new Dimension(250,25));

        this.textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
        this.textFieldPanel.setPreferredSize(new Dimension(250,40));

        this.errorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,5,0));
        this.errorPanel.setPreferredSize(new Dimension(250,20));

        //components
        this.titleLabel = new JLabel(title);
        this.titleLabel.setFont(inputLabel);

        this.field = new TextField(250,40,secondaryColor,accentColor1,accentColor1,accentColor1,defaultMessage,isPasswordField);

        errorLabel = new JLabel(this.errorMessage);
        errorLabel.setFont(smallFont);

        //adding components to panels
        titlePanel.add(titleLabel);

        textFieldPanel.add(field);
        errorPanel.add(errorLabel);

        //adding panels to main component
        this.add(titlePanel,BorderLayout.NORTH);
        this.add(textFieldPanel,BorderLayout.CENTER);
        this.add(errorPanel,BorderLayout.SOUTH);
    }

    public String getText(){
        return String.valueOf(field.getPassword());
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorLabel.setText(this.errorMessage);
    }


    /**
     * Styles the class with the given 4 colours
     * @param primaryColor The background color of north,south panels
     * @param secondaryColor The background color of the center panel
     * @param accentColor1 The foreground color of the center panel, and its border
     * @param accentColor2 The foreground color the north panel
     */
    public void style(Color primaryColor, Color secondaryColor, Color accentColor1, Color accentColor2) {
        this.primaryColor = primaryColor;

        this.titlePanel.setBackground(primaryColor);
        this.textFieldPanel.setBackground(primaryColor);
        this.errorPanel.setBackground(primaryColor);

        this.field.changeColors(secondaryColor, accentColor1, accentColor2, accentColor1);

        this.titleLabel.setForeground(accentColor2);
        this.errorLabel.setForeground(primaryColor);
    }

    public void showErrorMessage(boolean enable){
        if (enable) {
            errorLabel.setForeground(Color.red);
        }
        else {
            errorLabel.setForeground(this.primaryColor);
        }
    }

    /**
     * Sets the default text of the field in the center panel
     * @param text
     */
    public void setText(String text) {
        this.field.setText(text);
    }

    /**
     * Sets the color of the field in the center panel
     * @param foregroundColor  the desired foreground <code>Color</code>
     */
    public void setForegroundColor(Color foregroundColor) {
        this.field.setForeground(foregroundColor);
    }

    /**
     * @return the color of the field in the center panel
     */
    public Color getForegroundColor() {
        return this.field.getForegroundColor();
    }

}
