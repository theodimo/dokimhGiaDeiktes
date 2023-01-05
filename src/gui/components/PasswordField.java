package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

import static gui.bootstrap.Fonts.*;

/**
 * This is a custom class that extends JPasswordField and
 * implements FocusListener. It initializes and customizes
 * a PasswordField component
 *
 * @author tzikaman
 */
public class PasswordField extends JPasswordField implements FocusListener {

    private final Color idleBorderColor;
    private final Color focusBorderColor;
    private final  Color backgroundColor;
    private final Color foregroundColor;
    private final Color defaultMessageColor;
    private final String defaultMessage;

    public PasswordField(int width, int height,
                         Color backgroundColor, Color foregroundColor,
                         Color idleBorderColor, Color focusBorderColor,
                         String defaultMessage)
    {
        this.idleBorderColor = idleBorderColor;
        this.focusBorderColor = focusBorderColor;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.defaultMessageColor = idleBorderColor;
        this.defaultMessage = defaultMessage;

        this.setBackground(this.backgroundColor);
        this.setForeground(this.defaultMessageColor);

        this.setBorder(new RoundedLineBorder(idleBorderColor,1,10,true));
        this.setEchoChar((char) 0);
        this.setText(defaultMessage);
        this.setCaretColor(focusBorderColor);

        this.setFont(mainFont);
        this.setPreferredSize(new Dimension(width, height));
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        this.setBorder(new RoundedLineBorder(focusBorderColor,1,10,true));

        if(Objects.equals(String.valueOf(this.getPassword()), defaultMessage)){
            this.setEchoChar('•');
            this.setText("");
            this.setForeground(foregroundColor);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        this.setBorder(new RoundedLineBorder(idleBorderColor,1,10,true));

        if(Objects.equals(String.valueOf(this.getPassword()), "")){
            this.setEchoChar((char) 0);
            this.setText(defaultMessage);
            this.setForeground(defaultMessageColor);
        }
    }
}
