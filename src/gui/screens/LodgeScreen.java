package gui.screens;

import api.Database;
import api.Lodge;

import api.Review;

import static api.StringEditor.*;
import gui.components.*;
import gui.components.Label;
import gui.components.Panel;
import gui.components.Button2;

import static gui.bootstrap.Fonts.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

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
    Button2 seeMoreAccommodationsButton; //a button which opens a JDialog that displays all accommodations of the lodge

    Button2 seeMoreReviewsButton; //a button which opens a JDialog that displays all reviews of the lodge

    Button2 addReviewButton; //a button which opens a JDialog that lets you write a review for the Lodge


    //constructor
    public LodgeScreen(Database db, Lodge lodge) {
        //initialization
        this.lodge = lodge;
        this.setSize(this.width, this.height);
        this.setTitle("Lodge Screen");
        this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
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

        this.titleLabel = new Label(this.lodge.getName().toUpperCase(), 700, 30, false);
        this.ownerLabel = new Label( "Owner: " + this.lodge.getOwner().getName() + " " + this.lodge.getOwner().getSurname(), 250, 30, false);
        this.locationLabel = new Label(this.lodge.getCity() + "," + this.lodge.getAddress() + ", " + this.lodge.getZipCode(), 300, 20, false);
        this.descriptionTitleLabel = new Label("Description", 1000, 40, false);
        this.descriptionLabel = new Label(transformToHtml(this.lodge.getDescription(), 180), 960, (int) (this.descriptionPanel.getPreferredSize().getHeight() - this.descriptionTitleLabel.getHeight()), false);
        this.accommodationsTitleLabel = new Label("What this lodge provides", 1000, 40, false);
        this.reviewsLabel = new Label("Reviews " + lodge.getTotalRating(), 1000, 40, false);

        this.seeMoreAccommodationsButton = new Button2("See all accommodations", 250, 40);
        this.seeMoreReviewsButton = new Button2("See all reviews", 250, 40);
        this.addReviewButton = new Button2("Add Review",250, 40);


        ArrayList<AccommodationWithIcon> AccommodationsToShow  = new ArrayList<>();

        for (String key : lodge.getAccommodations().keySet()) {
            for (String value : lodge.getAccommodations().get(key)) {
                AccommodationsToShow.add(new AccommodationWithIcon(value));
            }
        }


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

        for (AccommodationWithIcon acc : AccommodationsToShow) {
            acc.style(secondaryColor, accentColor, mainFont);
        }


        this.titleLabel.setBounds(10, 10, this.titleLabel.getWidth(), this.titleLabel.getHeight());
        this.ownerLabel.setBounds(1040 - this.ownerLabel.getWidth(), 10, this.ownerLabel.getWidth(), this.ownerLabel.getHeight());
        this.locationLabel.setBounds(15, 10 + this.titleLabel.getHeight(), this.locationLabel.getWidth(), this.locationLabel.getHeight());
        this.descriptionLabel.setVerticalAlignment(Label.TOP);
        this.descriptionLabel.setBorder(new EmptyBorder(5, 10, 0, 0));

        this.seeMoreAccommodationsButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.seeMoreReviewsButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.addReviewButton.style(accentColor, secondaryColor, paleBlue, inputLabel);

        this.seeMoreAccommodationsButton.setFocusPainted(false);
        this.seeMoreReviewsButton.setFocusPainted(false);
        this.addReviewButton.setFocusPainted(false);


        //components addition
        this.identityPanel.add(this.titleLabel);
        this.identityPanel.add(this.ownerLabel);
        this.identityPanel.add(this.locationLabel);

        this.descriptionPanel.add(this.descriptionTitleLabel, BorderLayout.NORTH);
        this.descriptionPanel.add(this.descriptionLabel, BorderLayout.CENTER);

        for (AccommodationWithIcon acc : AccommodationsToShow) {
            this.accommodationsContainer.add(acc);
        }

        this.accommodationsButtonContainer.add(this.seeMoreAccommodationsButton);
        this.reviewsButtonContainer.add(this.seeMoreReviewsButton);
        this.reviewsButtonContainer.add(this.addReviewButton);

        this.accommodationsPanel.add(this.accommodationsTitleLabel, BorderLayout.NORTH);
        this.accommodationsPanel.add(this.accommodationsContainer, BorderLayout.CENTER);
        this.accommodationsPanel.add(this.accommodationsButtonContainer, BorderLayout.SOUTH);

        this.reviewsPanel.add(this.reviewsLabel, BorderLayout.NORTH);

        if(lodge.getReviewsIndexes().isEmpty()){
            JLabel noReviewsLabel = new JLabel("There are no reviews yet");
            noReviewsLabel.setFont(mainFont);
            noReviewsLabel.setForeground(Color.white);
            this.reviewsPanel.add(noReviewsLabel, BorderLayout.CENTER);
        }else {
            //this.reviewsPanel.removeAll();

            Review R1 = db.getReview(lodge.getReviewsIndexes().get(0));
            ReviewUi r1 = new ReviewUi(R1, 1000, 120);

            r1.style(primaryColor, secondaryColor, dark, gray, accentColor, dark, inputLabel, smallFont, mainFont, inputLabel);
            this.reviewsPanel.add(r1, BorderLayout.CENTER);
        }

        this.reviewsPanel.add(this.reviewsButtonContainer, BorderLayout.SOUTH);

        this.add(this.identityPanel);
        this.add(this.descriptionPanel);
        this.add(this.accommodationsPanel);
        this.add(this.reviewsPanel);

        this.setVisible(true);

        //listeners
        this.seeMoreAccommodationsButton.addActionListener(e -> {
            new Accommodations(db, this.lodge.getAccommodations());
        });

        this.seeMoreReviewsButton.addActionListener(e -> {
            ArrayList<Review> reviews = new ArrayList<>();
            System.out.println("eee");
            System.out.println(this.lodge.getReviewsIndexes());
            for (Integer index: this.lodge.getReviewsIndexes()) {
                reviews.add(db.getReview(index));
            }
            new Reviews(db, reviews);
        });

        this.addReviewButton.addActionListener(e -> {
            new ReviewProducer(db, lodge);
        });

    }
}
