package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

import gui.components.AccommodationWithIcon;
import gui.components.AccommodationsContainer;
import gui.components.Panel;
import gui.components.Label;

import static gui.bootstrap.Fonts.*;

//this class displays a jdialog. It creates the basic structure of the window, the colors, fonts etc. Hovewer, it does not display
//any data because it should be expanded by other dialogs that will put different data into this "vessel"

public abstract class ScrollableDialog extends JDialog {
    //various variables
    protected static Color primaryColor = new Color(21, 47, 80);
    protected static Color secondaryColor = new Color(5, 68, 94);
    protected static Color accentColor = new Color(212, 241, 244);
    protected static Color paleBlue = new Color(29, 75, 135);

    protected static Font smallFont = new Font("Rapor", Font.PLAIN, 14);
    protected static Font bigFont = new Font("Rapor", Font.PLAIN, 26);

    protected static Color dark = new Color(0, 0, 0);

    protected static Color gray = new Color(96, 96, 96);

    //properties
    private int width = 500;
    private int height = 600;
    protected int verticalGap; //the space between each component inside mainPanel. Used at resizing
    private HashMap<String, String[]> accommodations;

    //components
    Panel northPanel; //this panel displays a title and a maybe a button
    protected Panel mainPanel; //this panel displays the accommodation categories and the accommodations themselves

    Label titleLabel; //displays the title What this lodge provides""

    public ScrollableDialog(String title, String titleMessage) {
        //initialization
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.verticalGap = 30;

        //components initialization
        this.northPanel = new Panel(this.width, 50, primaryColor, "preferredSize");
        this.mainPanel = new Panel(this.width, 400, primaryColor, "preferredSize");
        JScrollPane scrollable = new JScrollPane(this.mainPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        this.titleLabel = new Label(titleMessage, 200, verticalGap, false);


        //layouts
        this.setLayout(new BorderLayout());
        this.northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, this.verticalGap));

        //styling
        this.getContentPane().setBackground(primaryColor);
        this.titleLabel.style(primaryColor, dark, titleFont);
        scrollable.setBorder(new EmptyBorder(0,0,0,0));

        //components addition
        this.northPanel.add(this.titleLabel);

        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(scrollable, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args) {
        new ScrollableDialog("Accommodations", "What this lodge provides");
    }
}
