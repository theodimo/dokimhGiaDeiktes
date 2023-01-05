package gui.components;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class TitledTextField extends JPanel {
    TextField field;
    JLabel errorLabel;
    public TitledTextField (String title, String defaultMessage, String errorMessage, boolean isPasswordField){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250,90));

        //panels
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
        titlePanel.setBackground(Color.white);
        titlePanel.setPreferredSize(new Dimension(250,25));

        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
        textFieldPanel.setPreferredSize(new Dimension(250,40));

        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,5,0));
        errorPanel.setBackground(Color.white);
        errorPanel.setPreferredSize(new Dimension(250,20));

        //components
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(mainFont);

        field = new TextField(250,40,primaryColor,secondaryColor,accentColor,secondaryColor,defaultMessage,isPasswordField);

        errorLabel = new JLabel(errorMessage);
        errorLabel.setForeground(Color.white);
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

    public void showErrorMessage(boolean enable){
        if(enable)
            errorLabel.setForeground(Color.red);
        else
            errorLabel.setForeground(Color.white);
    }
}
