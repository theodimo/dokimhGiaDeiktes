package gui.screens;

import api.Lodge;

import api.Review;
import api.User;
import gui.components.*;
import gui.components.Label;
import gui.components.Panel;
import gui.components.Button2;

import static gui.bootstrap.Fonts.*;

import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

//this screen is responsible for displaying the information about a Lodge
public class LodgeScreen extends JFrame {
    //various variables
    private static Color primaryColor = new Color(21, 47, 80);
    private static Color secondaryColor = new Color(5, 68, 94);
    private static Color accentColor = new Color(212, 241, 244);
    public static Color paleBlue = new Color(29, 75, 135);

    public static Font smallFont = new Font("Rapor", Font.PLAIN, 14);
    public static Font bigFont = new Font("Rapor", Font.PLAIN, 26);

    public static Color dark = new Color(0, 0, 0);

    public static Color gray = new Color(96, 96, 96);

    //dimensions
    private int width = 1080;
    private int height = 770;

    //CLASS VARIABLES
    Lodge lodge;

    //panels
    Panel identityPanel; //the panel that provides the most important info about the Lodge, ie its name, location & creator
    Panel descriptionPanel; //the panel that displays the description of the lodge
    Panel accommodationsPanel; //the panel that displays a title & and another panel containing the accommodations of the lodge
    Panel accommodationsContainer; //the interior panel that displays the accommodations themselves

    Panel accommodationsButtonContainer; //a panel that wraps the button for seeing more accommodations. If i didn't have this, then the button would stretch and cover 1000px wide

    Panel reviewsPanel; //the panel that displays the reviews for the lodge

    Panel reviewsButtonContainer; //a panel that wraps the button for seeing more reviews


    //labels
    Label titleLabel; //displays the title of the lodge
    Label locationLabel; //displays the location of the lodge
    Label ownerLabel; //display the owner of the lodge
    Label descriptionTitleLabel; //a title that displays "Description"
    Label descriptionLabel; //displays the description of the lodges
    Label accommodationsTitleLabel; //a title that displays "What this lodge provides"

    Label reviewsLabel; //a title that displays "Reviews"

    //buttons
    Button2 seeMoreAccommodationsButton; //a button which opens a jdialog that displays all accommodatios of the lodge

    Button2 seeMoreReviewsButton; //a button which opens a jdialog that displays all reviews of the lodge


    //constructor
    public LodgeScreen(Lodge lodge) {
        //initialization
        this.lodge = lodge;
        this.setSize(this.width, this.height);
        this.setTitle("Lodge Screen");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 8));
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //components initialization
        this.identityPanel = new Panel(1000, 70, secondaryColor, "preferredSize");
        this.descriptionPanel = new Panel(1000, 150, primaryColor, "preferredSize");
        this.accommodationsPanel = new Panel(1000, 220, primaryColor, "preferredSize");
        this.accommodationsContainer = new Panel(1000, 160, secondaryColor, "preferredSize");
        this.accommodationsButtonContainer = new Panel(1000, 60, primaryColor, "preferredSize");
        this.reviewsPanel = new Panel(1000, 245, primaryColor, "preferredSize");
        this.reviewsButtonContainer = new Panel(1000, 50, primaryColor, "preferredSize");

        this.titleLabel = new Label(this.lodge.getName().toUpperCase(), 400, 30, false);
        this.ownerLabel = new Label("owned by Dimos Theocharis", 250, 30, false);
        this.locationLabel = new Label(this.lodge.getCity() + "," + this.lodge.getAddress() + ", " + this.lodge.getZipCode(), 300, 20, false);
        this.descriptionTitleLabel = new Label("Description", 1000, 40, false);
        this.descriptionLabel = new Label(this.transformToHtml(this.lodge.getDescription()), 960, (int) (this.descriptionPanel.getPreferredSize().getHeight() - this.descriptionTitleLabel.getHeight()), false);
        this.accommodationsTitleLabel = new Label("What this lodge provides", 1000, 40, false);
        this.reviewsLabel = new Label("Reviews (4.5)", 1000, 40, false);

        this.seeMoreAccommodationsButton = new Button2("See all accommodations", 250, 40);
        this.seeMoreReviewsButton = new Button2("See all reviews", 250, 40);


        AccommodationWithIcon acc = new AccommodationWithIcon("Θέα στο βουνό");
        AccommodationWithIcon acc1 = new AccommodationWithIcon("Αυλή");
        AccommodationWithIcon acc2 = new AccommodationWithIcon("Κεντρική Θέρμανση");

        User u1 = new User("Dimos", "Theocharis", "thanatopios7", "1234", "en");

        Review R1 = new Review(u1, "Poly kalo diamerisma", (float) 4.4, "2/1/2023");
        ReviewUi r1 = new ReviewUi(R1, 1000, 120);



        //layouts
        this.identityPanel.setLayout(null);
        this.descriptionPanel.setLayout(new BorderLayout());
        this.accommodationsPanel.setLayout(new BorderLayout());
        this.accommodationsContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.accommodationsButtonContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.reviewsPanel.setLayout(new BorderLayout());
        this.reviewsButtonContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));


        //styling
        this.getContentPane().setBackground(primaryColor);
        this.titleLabel.style(secondaryColor, accentColor, titleFont);
        this.ownerLabel.style(secondaryColor, dark, mainFont);
        this.locationLabel.style(secondaryColor, dark, smallFont);
        this.descriptionTitleLabel.style(primaryColor, dark, bigFont);
        this.descriptionLabel.style(secondaryColor, accentColor, mainFont);
        this.accommodationsTitleLabel.style(primaryColor, dark, bigFont);
        this.reviewsLabel.style(primaryColor, dark, bigFont);

        acc.style(secondaryColor, accentColor, mainFont);
        acc1.style(secondaryColor, accentColor, mainFont);
        acc2.style(secondaryColor, accentColor, mainFont);

        r1.style(primaryColor, secondaryColor, dark, gray, accentColor, dark, inputLabel, smallFont, mainFont, inputLabel);

        this.titleLabel.setBounds(10, 10, this.titleLabel.getWidth(), this.titleLabel.getHeight());
        this.ownerLabel.setBounds(1040 - this.ownerLabel.getWidth(), 10, this.ownerLabel.getWidth(), this.ownerLabel.getHeight());
        this.locationLabel.setBounds(15, 10 + this.titleLabel.getHeight(), this.locationLabel.getWidth(), this.locationLabel.getHeight());
        this.descriptionLabel.setVerticalAlignment(Label.TOP);
        this.descriptionLabel.setBorder(new EmptyBorder(5, 10, 0, 0));

        this.seeMoreAccommodationsButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.seeMoreReviewsButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.seeMoreAccommodationsButton.setFocusPainted(false);
        this.seeMoreReviewsButton.setFocusPainted(false);


        //components addition
        this.identityPanel.add(this.titleLabel);
        this.identityPanel.add(this.ownerLabel);
        this.identityPanel.add(this.locationLabel);

        this.descriptionPanel.add(this.descriptionTitleLabel, BorderLayout.NORTH);
        this.descriptionPanel.add(this.descriptionLabel, BorderLayout.CENTER);

        this.accommodationsContainer.add(acc);
        this.accommodationsContainer.add(acc1);
        this.accommodationsContainer.add(acc2);

        this.accommodationsButtonContainer.add(this.seeMoreAccommodationsButton);
        this.reviewsButtonContainer.add(this.seeMoreReviewsButton);

        this.accommodationsPanel.add(this.accommodationsTitleLabel, BorderLayout.NORTH);
        this.accommodationsPanel.add(this.accommodationsContainer, BorderLayout.CENTER);
        this.accommodationsPanel.add(this.accommodationsButtonContainer, BorderLayout.SOUTH);

        this.reviewsPanel.add(this.reviewsLabel, BorderLayout.NORTH);
        this.reviewsPanel.add(r1, BorderLayout.CENTER);
        this.reviewsPanel.add(this.reviewsButtonContainer, BorderLayout.SOUTH);

        this.add(this.identityPanel);
        this.add(this.descriptionPanel);
        this.add(this.accommodationsPanel);
        this.add(this.reviewsPanel);

        this.setVisible(true);

        //listeners
        this.seeMoreAccommodationsButton.addActionListener(e -> {
            Accommodations acco = new Accommodations();

            AccommodationWithIcon accom = new AccommodationWithIcon("Θέα στο δρόμο");
            System.out.println("hi");
            AccommodationsContainer container = (AccommodationsContainer) acco.mainPanel.getComponent(0);
            container.mainPanel.add(accom);
            container.setPreferredSize(new Dimension((int) container.getPreferredSize().getWidth(), (int ) (container.getPreferredSize().getHeight() + accom.getPreferredSize().getHeight() + 10)));
            acco.mainPanel.add(container, 0);
            acc.revalidate();
            acc.repaint();
        });
    }


    /**
     * Splits the given text into lines of words, (each line has 180 characters), and adds <html>, </html> tags at start and
     * end of text. Lastly, it forces the text to continue at the next line when it reaches the end of the panel, by inserting
     * <br> after each line.
     * @param text the text that is going to be splitted and put in html tags
     * @return the formated text
     */
    private String transformToHtml(String text) {
        int lineLength = text.length() > 180 ? 180 : text.length();
        int totalLines = text.length() / 100 + 1;
        String currentLine;
        String newText = "<html>";

        int i;
        for (i = 0; i < totalLines - 1; i++) {
            currentLine = text.substring(i * lineLength, (i + 1) * lineLength);
            newText += currentLine;
            newText += "<br>";
        }

        //append the last not entire line
        currentLine = text.substring(i * lineLength, text.length());
        newText += currentLine;
        newText += "</html>";

        return newText;
    }

    public static void main(String[] args) {
        new LodgeScreen(new Lodge("Διώροφο στη Ροτόντα", "Διαμέρισμα", "Ολύμπου 122", "Θεσσαλονίκη", 45630, "Το συγκεκριμένο σπίτι βρίσκεται στον 2ο όροφο, στην περιχοή Ροτόντα της Θεσσαλονίκης. Είναι διαμπερές, φωτεινό και ευρύχωρο. Βρίσκεται κοντά στον Οδυσσέα και στον Πολυνίκη."));
    }
}
