package gui.screens;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Fonts.*;
import static gui.bootstrap.Colors.*;

/**This class is a template that will be used by the ui classes SignUpScreen & SignInScreen
 * It contains code that both these classes have in common
 * It creates a frame 1080x720, centers a panel where the content will be displayed, and applies styles
 *
 * @author Dimos Theocharis
 * @author tzikaman
 */


public abstract class SignTemplate extends JFrame {
    protected int fieldsWidth = 250;
    protected int fieldsHeight = 40;
    protected int buttonsWidth = 200;
    protected int buttonsHeight = 30;

    //components
    private JLabel titleLabel;
    public JPanel titlePanel;
    public JPanel fieldsPanel; //the panel that contains all fields and their labels
    public JPanel buttonsPanel; //the panel that contains the buttons, such as Sign Up etc

    public SignTemplate(String title, int width, int height){
        //initialization of Frame
        this.setSize(width, height);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setFocusable(true);

        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //initialization of Panels
        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,50));
        titlePanel.setPreferredSize(new Dimension(width, 100));

        fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,5));
        fieldsPanel.setPreferredSize(new Dimension(width, 350));

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        buttonsPanel.setPreferredSize(new Dimension(width, 150));

        //set title in the screen
        titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont);
        titlePanel.add(titleLabel);
        titleLabel.setForeground(accentColor3);

        //styling the Panels
        titlePanel.setBackground(secondaryColor);
        fieldsPanel.setBackground(primaryColor);
        buttonsPanel.setBackground(secondaryColor);

        //adding Panels in the Frame
        this.add(titlePanel,BorderLayout.NORTH);
        this.add(fieldsPanel, BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);

    }
}
