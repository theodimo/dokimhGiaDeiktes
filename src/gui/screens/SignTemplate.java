package gui.screens;

import gui.bootstrap.Colors;
import gui.components.Panel;
import gui.components.Label;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

/**This class is a template that will be used by the ui classes SignUpScreen & SignInScreen
 * It contains code that both these classes have in common
 * It creates a frame 1080x720, centers a panel where the content will be displayed, and applies styles
 *
 * @author Dimos Theocharis
 * @author tzikaman
 */


public abstract class SignTemplate extends JFrame {


    private final int width = 400;
    private final int height = 600 + 48;
    protected int fieldsWidth = 250;
    protected int fieldsHeight = 40;
    protected int buttonsWidth = 80;
    protected int buttonsHeight = 30;

    //components
    private JLabel titleLabel;
    public JPanel titlePanel;
    public JPanel fieldsPanel; //the panel that contains all fields and their labels
    public JPanel buttonsPanel; //the panel that contains the buttons, such as Sign Up etc


    public SignTemplate(String title){
        //initialization
        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        //components
        this.titlePanel = new Panel(this.width / 2, this.height * 1 / 2, Colors.secondaryColor, "size");
        this.titlePanel.setLayout(new BorderLayout());
        this.fieldsPanel = new Panel(this.titlePanel.getWidth(), this.titlePanel.getHeight() * 2 / 3, accentColor, "preferredSize");
        this.buttonsPanel = new Panel(this.titlePanel.getWidth(), this.titlePanel.getHeight() * 1 / 3, secondaryColor, "preferredSize");
        this.titleLabel = new Label(title, 450, 40, true);

        this.fieldsPanel.setLayout(null);
        this.buttonsPanel.setLayout(new FlowLayout());

        //styling
        this.getContentPane().setBackground(Colors.primaryColor);
        //this.titleLabel.style(Colors.accentColor, Colors.secondaryColor, Fonts.titleFont);

        //additions
        this.getContentPane().add(this.titlePanel);
        this.getContentPane().add(this.titleLabel);
        this.titlePanel.add(this.fieldsPanel, BorderLayout.NORTH);
        this.titlePanel.add(this.buttonsPanel, BorderLayout.SOUTH);


        this.setVisible(true);

        //other functionality
        //this.container.center(this);
        //this.titleLabel.centerX(this, (this.container.getY() - this.getContentPane().getY() - this.titleLabel.getHeight()) / 2);
    }



    public SignTemplate(String title,int dummie){
        //initialization of Frame
        this.setSize(width, height);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setFocusable(true);

        this.setLocationRelativeTo(null);
        //this.setResizable(false);

        //initialization of Panels
        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,50));
        titlePanel.setPreferredSize(new Dimension(width, 100));

        fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,25));
        fieldsPanel.setPreferredSize(new Dimension(width, 350));

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,50));
        buttonsPanel.setPreferredSize(new Dimension(width, 150));

        //set title in the screen
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont);
        titlePanel.add(titleLabel);

        //styling the Panels
        titlePanel.setBackground(Color.lightGray);
        buttonsPanel.setBackground(Color.white);
        fieldsPanel.setBackground(Color.white);

        //adding Panels in the Frame
        this.add(titlePanel,BorderLayout.NORTH);
        this.add(fieldsPanel, BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);

    }
}
