package gui.components;

import gui.bootstrap.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**This is a simple button class which extends JButton. It implements a reactive button, which means a button that changes
 * background color on hover event
 *
 * @author Dimos Theocharis
 */
public class Button2 extends JButton {

    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR); //the cursor we want mouse has, when user hovers button
    Cursor arrowCursor = new Cursor(Cursor.DEFAULT_CURSOR); //the cursor we want mouse has as default

    private Color backgroundColor; //the background color that the button currently has
    private Color foregroundColor; //the color of button's text
    private Color idleColor; //the background color that the button will have when not being hovered
    private Color hoverColor; //the background color that the button will have when being hovered
    private Font font; //the font of the button's text


    public Button2(String text, int width, int height){
        this.setText(text);
        this.setPreferredSize(new Dimension(width, height));

    }

    public void style(Color foregroundColor, Color idleColor, Color hoverColor, Font font) {
        this.backgroundColor = idleColor;
        this.foregroundColor = foregroundColor;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.font = font;

        this.setFont(this.font);
        this.setBackground(this.idleColor);
        this.setForeground(this.foregroundColor);

        this.setBorder(BorderFactory.createLineBorder(Colors.secondaryColor,0));

        //addMouseListener
        this.addMouseListener(new MouseAdapter() {
            //things to do when user holds button
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setCursor(handCursor);
            }

            //things to do when user stops holding the button
            public void mouseExited(MouseEvent e) {
                setBackground(idleColor);
                setCursor(arrowCursor);
            }
        });
    }

    public void style(Color foregroundColor, Color idleColor, Color hoverColor, Font font, Color borderColor, int borderThickness) {
        this.style(foregroundColor, idleColor, hoverColor, font);
        this.setBorder(BorderFactory.createLineBorder(foregroundColor,0));
    }
}
