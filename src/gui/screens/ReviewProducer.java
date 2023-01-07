package gui.screens;

import api.Database;
import api.Lodge;
import api.Review;
import api.User;
import gui.components.Button2;
import gui.components.TextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class ReviewProducer extends JDialog {

    String reviewText;
    int rating;
    JLabel titleLabel;
    TextArea textArea;
    Button2 submitButton;
    ButtonGroup ratingGroup = new ButtonGroup();
    ArrayList<JRadioButton> ratingButtons = new ArrayList<>();

    public ReviewProducer(Database db, Lodge lodge){
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setTitle("Add Review");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(600,400 + 40));
        this.setLocationRelativeTo(null);

        //panels initialization
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,25,25));
        titlePanel.setPreferredSize(new Dimension(600,80));
        titlePanel.setBackground(Color.lightGray);

        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.setPreferredSize(new Dimension(600,240));
        reviewPanel.setBackground(primaryColor);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,5));
        ratingPanel.setPreferredSize(new Dimension(600,40));
        ratingPanel.setBackground(primaryColor);

        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,0));
        textPanel.setPreferredSize(new Dimension(600,200));
        textPanel.setBackground(primaryColor);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING,25,20));
        buttonsPanel.setPreferredSize(new Dimension(600,80));
        buttonsPanel.setBackground(primaryColor);

        //components initialization
        titleLabel = new JLabel("Write a review for " + lodge.getName());
        titleLabel.setFont(titleFont);

        textArea = new TextArea("Write something", 500, 200, 500);
        textArea.style(Color.lightGray,secondaryColor,primaryColor,secondaryColor,mainFont);


        for (int i = 0; i < 5; i++) {
            JRadioButton ratingButton = new JRadioButton(String.valueOf(i+1));
            ratingButton.setBackground(primaryColor);

            int finalI = i+1;
            ratingButton.addActionListener(e -> {
                this.rating = finalI;
                System.out.println("the rating is " + this.rating);
            });

            ratingButtons.add(ratingButton);
            ratingGroup.add(ratingButton);
        }

        submitButton = new Button2("Submit",150,40);
        submitButton.style(secondaryColor,Color.lightGray,Color.gray,buttonFont);
        submitButton.addActionListener(e -> {
            this.reviewText = textArea.getText();
            User owner = lodge.getOwner();
            owner.addReviewIndex(db.getReviewsCount()); //add the position of the review to the user's review positions

            Review review = db.createReview(owner,reviewText,this.rating,"12/12/2012"); //create the review

            lodge.addReviewIndex(db.getReviewsCount() - 1, db); //add the position of the review to the lodge's reviews indexes

            db.saveLodges();

            this.dispose();
        });

        //adding components to panels
        titlePanel.add(titleLabel);

        for (JRadioButton ratingButton : ratingButtons){
            ratingPanel.add(ratingButton);
        }


        textPanel.add(textArea);
        reviewPanel.add(ratingPanel,BorderLayout.NORTH);
        reviewPanel.add(textPanel,BorderLayout.CENTER);
        buttonsPanel.add(submitButton);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(reviewPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    /*
    public ReviewProducer(Database db,Lodge lodge, Review review) {
        this(db, lodge);

        for (ActionListener listener: this.submitButton.getActionListeners()) {
            this.submitButton.removeActionListener(listener);
        }

        System.out.println(textArea.getText());

        this.ratingButtons.get(review.getRating()-1).setSelected(true);
        this.ratingButtons.get(review.getRating()-1).revalidate();
        this.ratingButtons.get(review.getRating()-1).repaint();

        this.textArea.setText(review.getText());
        this.textArea.revalidate();
        this.textArea.repaint();

        this.revalidate();
        this.repaint();
    }

     */

    public ReviewProducer(Database db,Lodge lodge, Review review) {
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setTitle("Add Review");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(600,400 + 40));
        this.setLocationRelativeTo(null);

        //panels initialization
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING,25,25));
        titlePanel.setPreferredSize(new Dimension(600,80));
        titlePanel.setBackground(Color.lightGray);

        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.setPreferredSize(new Dimension(600,240));
        reviewPanel.setBackground(primaryColor);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,5));
        ratingPanel.setPreferredSize(new Dimension(600,40));
        ratingPanel.setBackground(primaryColor);

        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,50,0));
        textPanel.setPreferredSize(new Dimension(600,200));
        textPanel.setBackground(primaryColor);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING,25,20));
        buttonsPanel.setPreferredSize(new Dimension(600,80));
        buttonsPanel.setBackground(primaryColor);

        //components initialization
        titleLabel = new JLabel("Write a review for " + lodge.getName());
        titleLabel.setFont(titleFont);

        textArea = new TextArea(review.getText(), 500, 200, 500);
        textArea.style(Color.lightGray,secondaryColor,primaryColor,secondaryColor,mainFont);


        for (int i = 0; i < 5; i++) {
            JRadioButton ratingButton = new JRadioButton(String.valueOf(i+1));
            ratingButton.setBackground(primaryColor);

            int finalI = i+1;
            ratingButton.addActionListener(e -> {
                this.rating = finalI;
                System.out.println("the rating is " + this.rating);
            });

            ratingButtons.add(ratingButton);
            ratingGroup.add(ratingButton);
        }
        this.ratingButtons.get(review.getRating()-1).setSelected(true);

        submitButton = new Button2("Submit",150,40);
        submitButton.style(secondaryColor,Color.lightGray,Color.gray,buttonFont);
        submitButton.addActionListener(e -> {
            this.reviewText = textArea.getText();
            User owner = lodge.getOwner();
            owner.addReviewIndex(db.getReviewsCount()); //add the position of the review to the user's review positions

            //Review review = db.createReview(owner,reviewText,this.rating,"12/12/2012"); //create the review

            lodge.addReviewIndex(db.getReviewsCount() - 1, db); //add the position of the review to the lodge's reviews indexes

            db.saveLodges();

            this.dispose();
        });

        //adding components to panels
        titlePanel.add(titleLabel);

        for (JRadioButton ratingButton : ratingButtons){
            ratingPanel.add(ratingButton);
        }


        textPanel.add(textArea);
        reviewPanel.add(ratingPanel,BorderLayout.NORTH);
        reviewPanel.add(textPanel,BorderLayout.CENTER);
        buttonsPanel.add(submitButton);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(reviewPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}
