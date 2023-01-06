package gui.components;

import javax.swing.*;
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

    private final Color idleBorderColor; //the color of the border when it is NOT interacted
    private final Color focusBorderColor; //the color of the border when it is interacted
    private final  Color backgroundColor; //the color of the field
    private final Color foregroundColor; //the color of the text in the field
    private final Color defaultMessageColor; //the color of the default text in the field
    private final String defaultMessage; //the text
    private boolean isPasswordField = false; //shows if the field is going to be a passwordField or not

    public TextField(int width, int height,
                     Color backgroundColor, Color foregroundColor,
                     Color idleBorderColor, Color focusBorderColor,
                     String defaultMessage) {

        this.idleBorderColor = idleBorderColor;
        this.focusBorderColor = focusBorderColor;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.defaultMessageColor = idleBorderColor;
        this.defaultMessage = defaultMessage;

        this.setBackground(this.backgroundColor);
        this.setForeground(this.defaultMessageColor);

        this.setBorder(new RoundedLineBorder(idleBorderColor,1,10,true));
        this.setText(defaultMessage);
        this.setCaretColor(focusBorderColor);

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

    @Override
    public void focusGained(FocusEvent e) {
        this.setBorder(new RoundedLineBorder(focusBorderColor,1,10,true));

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
        this.setBorder(new RoundedLineBorder(idleBorderColor,1,10,true));

        if(Objects.equals(String.valueOf(this.getPassword()), "")){
            if(isPasswordField){
                this.setEchoChar((char) 0);
            }
            this.setText(defaultMessage);
            this.setForeground(defaultMessageColor);
        }
    }

}
