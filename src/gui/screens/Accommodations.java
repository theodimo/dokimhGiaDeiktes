package gui.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

import gui.components.AccommodationWithIcon;
import gui.components.AccommodationsContainer;
import gui.components.Panel;
import gui.components.Label;

import static gui.bootstrap.Fonts.*;

//this class is a dialog. It is responsible for displaying all the accommodations that a lodge may have
//it appears when user is at LodgeScreen and clicks "See all accommodations" button
public class Accommodations extends JDialog {
    //various variables
    private static Color primaryColor = new Color(21, 47, 80);
    private static Color secondaryColor = new Color(5, 68, 94);
    private static Color accentColor = new Color(212, 241, 244);
    public static Color paleBlue = new Color(29, 75, 135);

    public static Font smallFont = new Font("Rapor", Font.PLAIN, 14);
    public static Font bigFont = new Font("Rapor", Font.PLAIN, 26);

    public static Color dark = new Color(0, 0, 0);

    //properties
    private int width = 500;
    private int height = 600;
    private HashMap<String, String[]> accommodations;

    //components
    Panel northPanel; //this panel displays a title and a maybe a button
    Panel mainPanel; //this panel displays the accommodation categories and the accommodations themselves

    Label titleLabel; //displays the title What this lodge provides""

    public Accommodations() {
        //initialization
        //this.accommodations = accommodations;
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("Accommodations");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //components initialization
        this.northPanel = new Panel(this.width, 50, primaryColor, "preferredSize");
        this.mainPanel = new Panel(this.width, 800, primaryColor, "preferredSize");
        JScrollPane scrollable = new JScrollPane(this.mainPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        
        this.titleLabel = new Label("What this lodge provides", 200, 30, false);

        HashMap<String, String[]> accommodations = new HashMap<>() {
            {
                put("Θέα", new String[]{"Θέα σε πισίνα", "Θέα στο βουνό", "Θέα στο λιμάνι", "Θέα σε παραλία"});
                put("Θέρμανση & Κλιματισμός", new String[]{"Εσωτερικό τζάκι", "Κεντρική Θέρμανση"});
                put("Κουζίνα & Τραπεζαρία", new String[]{"Κουζίνα", "Πιάτα και Μαχαιροπίρουνα", "Ψυγείο", "Πλυντήριο Πιάτων"});
            }
        };

        AccommodationsContainer acc1 = new AccommodationsContainer("Θέα", accommodations.get("Θέα"), 400);
        AccommodationsContainer acc2 = new AccommodationsContainer("Θέρμανση & Κλιματισμός", accommodations.get("Θέρμανση & Κλιματισμός"), 400);
        AccommodationsContainer acc3 = new AccommodationsContainer("Κουζίνα & Τραπεζαρία", accommodations.get("Κουζίνα & Τραπεζαρία"), 400);

        //layouts
        this.setLayout(new BorderLayout());
        this.northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 30));

        //styling
        this.getContentPane().setBackground(primaryColor);
        this.titleLabel.style(primaryColor, dark, titleFont);
        acc1.style(secondaryColor, dark, accentColor, bigFont, mainFont);
        acc2.style(secondaryColor, dark, accentColor, bigFont, mainFont);
        acc3.style(secondaryColor, dark, accentColor, bigFont, mainFont);
        scrollable.setBorder(new EmptyBorder(0,0,0,0));

        //components addition
        this.northPanel.add(this.titleLabel);

        this.mainPanel.add(acc1);
        this.mainPanel.add(acc2);
        this.mainPanel.add(acc3);

        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(scrollable, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args) {
        Accommodations acc = new Accommodations();

        AccommodationWithIcon accom = new AccommodationWithIcon("Θέα στο δρόμο");
        System.out.println("hi");
        System.out.println(acc.mainPanel.getComponentCount());
        acc.revalidate();
        acc.repaint();
    }

}
