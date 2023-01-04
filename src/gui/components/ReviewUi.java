package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import api.Review;

import java.awt.*;

//this class is responsible for displaying a review. Specifically, it is a panel that provides information about
//the author, the rating, the date and the review itself
public class ReviewUi extends JPanel implements ScrollableElement {
    //properties
    private Review review; //the review that is being displayed

    //components
    private Panel credentialsPanel; //a panel that display the essential information that form review's id, ie author, date
    private Panel mainPanel; //the panel that contains the text of the review
    private Panel ratingPanel; //the panel that contains the rating

    private Label authorLabel; //displays the author of the review
    private Label dateLabel; //displays the creation time of the review
    private Label textLabel; //displays the text of the review
    private Label ratingLabel; //displays the rating of the review

    //constructor
    public ReviewUi(Review review, int width, int height) {
        //initialization
        this.review = review;
        this.setPreferredSize(new Dimension(width, height));

        //components initialization
        this.credentialsPanel = new Panel(width, (int) (height * 0.3), null, "preferredSize");
        this.mainPanel = new Panel(width, (int) (height * 0.5), null, "preferredSize");
        this.ratingPanel = new Panel(width, (int) (height * 0.2), null, "preferredSize");

        this.authorLabel = new Label(this.review.getAuthor().getName() + " " + this.review.getAuthor().getSurname(), 300, (int) (height * 0.2), false);
        this.dateLabel = new Label(this.review.getDate(), 60, (int) (height * 0.2), false);
        this.textLabel = new Label(this.review.getText(), width, (int) (height * 0.4), false);
        this.ratingLabel = new Label("Rating: " + this.review.getRating() + "/5", width, (int) (height * 0.2), false);

        //layouts
        this.setLayout(new BorderLayout(0, 5));
        this.credentialsPanel.setLayout(null);
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        this.ratingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        //styling
        this.authorLabel.setBounds(5, 0, this.authorLabel.getWidth(), this.authorLabel.getHeight());
        this.dateLabel.setBounds(width - this.dateLabel.getWidth() - 5, 0, this.dateLabel.getWidth(), this.dateLabel.getHeight());


        //components adding
        this.credentialsPanel.add(this.authorLabel);
        this.credentialsPanel.add(this.dateLabel);

        this.mainPanel.add(this.textLabel);

        this.ratingPanel.add(this.ratingLabel);

        this.add(this.credentialsPanel, BorderLayout.NORTH);
        this.add(this.mainPanel, BorderLayout.CENTER);
        this.add(this.ratingPanel, BorderLayout.SOUTH);
    }

    public void style(Color backgroundColor, Color textBackgroundColor, Color authorColor, Color dateColor, Color textColor, Color ratingColor, Font authorFont, Font dateFont, Font textFont, Font ratingFont) {
        this.setBackground(backgroundColor);
        this.setBorder(new LineBorder(authorColor, 2, true));
        this.mainPanel.setBackground(textBackgroundColor);
        this.ratingPanel.setBackground(backgroundColor);

        this.authorLabel.style(backgroundColor, authorColor, authorFont);
        this.dateLabel.style(backgroundColor, dateColor, dateFont);
        this.textLabel.style(textBackgroundColor, textColor, textFont);
        this.ratingLabel.style(backgroundColor, ratingColor, ratingFont);


        this.revalidate();
        this.repaint();
    }

    @Override
    public int computeHeight() {
        return (int) this.getPreferredSize().getHeight();
    }
}
