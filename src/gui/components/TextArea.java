package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class TextArea extends JTextArea implements KeyListener, FocusListener {
    //properties
    private int maxCharacters; //the maximum characters that user can type
    private int currentCharacters; //the characters that user has typed

    private int keyCode; //the code of the key that got lastly pressed

    private Color backgroundColor;
    private Color foregroundColor;
    private Color idleBorderColor;
    private Color focusBorderColor;
    private String defaultMessage;


    //components
    Label charactersLabel;

    public TextArea(String text, int width, int height, int maxCharacters) {
        //initialization
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        this.setBorder(new RoundedLineBorder(Color.BLACK,1,10,true));
        this.setText(text);
        this.maxCharacters = maxCharacters;
        this.currentCharacters = text.length();
        this.defaultMessage = text;

        //components
        this.charactersLabel = new Label("0 / " + this.maxCharacters, 100, 20, false);

        //styling
        this.charactersLabel.setHorizontalAlignment(JLabel.RIGHT);

        //add components
        this.add(this.charactersLabel, BorderLayout.SOUTH);

        //listeners
        this.addKeyListener(this);
        this.addFocusListener(this);
    }

    //setters
    public void setCurrentCharacters(int characters) {
        this.currentCharacters = characters;
        this.updateCharactersLabel();
    }

    //getters
    public Color getForegroundColor() {
        return this.foregroundColor;
    }

    public void style(Color backgroundColor, Color foregroundColor, Color idleBorderColor, Color focusBorderColor, Font font) {
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.idleBorderColor = idleBorderColor;
        this.focusBorderColor = focusBorderColor;

        this.setBackground(this.backgroundColor);
        this.setForeground(this.foregroundColor);
        this.setFont(font);
        this.setCaretColor(this.foregroundColor);
        this.charactersLabel.style(this.backgroundColor, this.foregroundColor, font);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLineWrap(true); //when text goes off textArea, it breaks it and continues at the next line
        this.setWrapStyleWord(true); //continues at the next line but keeps the word united the word that provoked the event
    }

     /**
     * Increments the number of the total characters that have been typed.
     * @return True if increment happened normally. False if text-area has reached its maximum character capacity
     */
    private boolean incrementCharacterCount() {
        boolean incremented = true;
        this.currentCharacters += 1;
        if (this.currentCharacters > this.maxCharacters) {
            this.currentCharacters = this.maxCharacters;
            incremented = false;
        }
        return incremented;
    }

     /**
     * Decrements the number of the total characters that have been typed.
     * @return True if decrement happened normally. False if there is no characters typed in text-area to delete
     */
    private boolean decrementCharacterCount() {
        boolean decremented = true;
        this.currentCharacters -=  1;
        if (this.currentCharacters < 0) {
            this.currentCharacters = 0;
            decremented = false;
        }
        return decremented;
    }

     /**
     * Deletes the last letter of the text of text-area
     */
    private void deleteLastCharacter() {
        String currentText = this.getText();
        this.setText(currentText.substring(0, currentText.length() - 1));
    }

    private void updateCharactersLabel() {
        this.charactersLabel.setText(this.currentCharacters + " / " + this.maxCharacters);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //logic for when a key is typed
        if (keyCode == 8) { //if user pressed backspace
            boolean decremented = decrementCharacterCount();
            if (decremented) {
                //the total number of characters that have been typed is larger than 0.
                //change the label in the bottom right corner of the text-area
                updateCharactersLabel();
            }

        } else { //if user didn't pressed backspace
            boolean incremented = incrementCharacterCount();
            if (incremented) {
                //the total number of characters that have been typed is less than the maximum number of characters.
                //change the label at the bottom right corner of the text-area
                updateCharactersLabel();
            } else {
                //if text area reached its maximum character capacity, then delete the character that got typed
                deleteLastCharacter();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //logic for when a key button is physically pressed down
        keyCode = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //logic for when a key button is physically released up

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().equals(this.defaultMessage)) {
            this.setText("");
            this.setCurrentCharacters(0);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().equals("")) {
            this.setText(this.defaultMessage);
            this.setCurrentCharacters(this.defaultMessage.length());
        }
    }
}
