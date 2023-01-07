package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;
import static gui.bootstrap.Fonts.*;

/**
 * This is a custom class that extends JTextField and
 * implements FocusListener. It initializes and customizes
 * a TextField component
 *
 * @author tzikaman
 */
public class TextField extends JPasswordField implements FocusListener{

    private Color idleBorderColor; //the color of the border when it is NOT interacted
    private Color focusBorderColor; //the color of the border when it is interacted
    private Color backgroundColor; //the color of the field
    private Color foregroundColor; //the color of the text in the field
    private Color defaultMessageColor; //the color of the default text in the field
    private String defaultMessage; //the text
    private boolean isPasswordField = false; //shows if the field is going to be a passwordField or not

    public TextField(int width, int height,
                     Color backgroundColor, Color foregroundColor,
                     Color idleBorderColor, Color focusBorderColor,
                     String defaultMessage) {

        this.changeColors(backgroundColor, foregroundColor, idleBorderColor, focusBorderColor);
        this.defaultMessage = defaultMessage;


        this.setText(defaultMessage);

        this.setCaretColor(this.foregroundColor);
        this.setEchoChar((char) 0);
        this.setFont(mainFont);
        this.setPreferredSize(new Dimension(width, height));
        this.addFocusListener(this);
    }

    public TextField(int width, int height,
                     Color backgroundColor, Color foregroundColor,
                     Color idleBorderColor, Color focusBorderColor,
                     String defaultMessage, boolean isPasswordField) {
        this(width,height,backgroundColor,foregroundColor,idleBorderColor,focusBorderColor,defaultMessage);


        this.isPasswordField = isPasswordField;
    }

    //getters
    public Color getForegroundColor() {
        return this.foregroundColor;
    }

    public void changeColors(Color backgroundColor, Color foregroundColor, Color idleBorderColor, Color focusBorderColor) {
        this.idleBorderColor = idleBorderColor;
        this.focusBorderColor = focusBorderColor;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.defaultMessageColor = idleBorderColor;

        this.setBackground(this.backgroundColor);
        this.setForeground(this.foregroundColor);

        //this.setBorder(new RoundedLineBorder(idleBorderColor,1,10,true));
        this.setBorder(new EmptyBorder(0, 10, 0, 0));
        this.setCaretColor(focusBorderColor);
    }

    @Override
    public void focusGained(FocusEvent e) {

        if(Objects.equals(String.valueOf(this.getPassword()), defaultMessage)){
            if(isPasswordField){
                this.setEchoChar('•');
            }
            this.setText("");
            this.setForeground(foregroundColor);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

        if(Objects.equals(String.valueOf(this.getPassword()), "")){
            if(isPasswordField){
                this.setEchoChar((char) 0);
            }
            this.setText(defaultMessage);
            this.setForeground(defaultMessageColor);
        }
    }

}
