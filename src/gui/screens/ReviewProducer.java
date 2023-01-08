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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class ReviewProducer extends JDialog {

    String reviewText;
    int rating;
    JLabel titleLabel;
    JLabel errorMessageLabel;
    TextArea textArea;
    String defaultMessage;
    Button2 submitButton;
    ButtonGroup ratingGroup = new ButtonGroup();
    ArrayList<JRadioButton> ratingButtons = new ArrayList<>();

    public ReviewProducer(Database db, Lodge lodge){
        //this.setModal(true);
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

        errorMessageLabel = new JLabel();
        errorMessageLabel.setFont(smallFont);
        errorMessageLabel.setForeground(primaryColor);

        this.defaultMessage = "Write a comment";
        textArea = new TextArea(defaultMessage, 500, 200, 500);
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
            if(getSelectedRating() != 0 && ( !Objects.equals(textArea.getText(), "") && !Objects.equals(textArea.getText(), defaultMessage) ) ) {
                this.reviewText = textArea.getText();
                User owner = db.getCurrentUser();
                owner.addReviewIndex(db.getReviewsCount()); //add the position of the review to the user's review positions

                db.createReview(owner,reviewText, this.rating, java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); //create the review

                lodge.addReviewIndex(db.getReviewsCount() - 1, db); //add the position of the review to the lodge's reviews indexes

                db.saveLodges();

                this.dispose();
            }
            else {
                if(getSelectedRating() == 0){
                    errorMessageLabel.setText("Please select rating");
                    errorMessageLabel.setForeground(Color.red);
                }
                else if(Objects.equals(textArea.getText(), "") || Objects.equals(textArea.getText(), defaultMessage)){
                    errorMessageLabel.setText("Please write a comment about this lodge");
                    errorMessageLabel.setForeground(Color.red);
                }
            }


        });

        //adding components to panels
        titlePanel.add(titleLabel);

        for (JRadioButton ratingButton : ratingButtons){
            ratingPanel.add(ratingButton);
        }

        ratingPanel.add(errorMessageLabel);


        textPanel.add(textArea);
        reviewPanel.add(ratingPanel,BorderLayout.NORTH);
        reviewPanel.add(textPanel,BorderLayout.CENTER);
        buttonsPanel.add(submitButton);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(reviewPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }


    public ReviewProducer(Database db,Lodge lodge, Review review) {
        this(db, lodge);

        this.setTitle("Edit Review");

        //initialize the components based on the existed review
        this.ratingButtons.get(review.getRating() - 1).setSelected(true);
        this.textArea.setText(review.getText());

        //remove current ActionListeners and added a new one for the submit button
        for (ActionListener listener: this.submitButton.getActionListeners()) {
            this.submitButton.removeActionListener(listener);
        }

        this.submitButton.addActionListener(e -> {

            if(!Objects.equals(textArea.getText(), "") && !Objects.equals(textArea.getText(), defaultMessage) ){
                //updating the review on the Database
                review.setText(this.textArea.getText());
                review.setRating(this.getSelectedRating());

                db.saveReviews();

                //updating the reviewed lodge
                lodge.updateTotalRating(db);

                db.saveLodges();

                this.dispose();
            }
            else if(Objects.equals(textArea.getText(), "") || Objects.equals(textArea.getText(), defaultMessage)){
                    errorMessageLabel.setText("Please write a comment about this lodge");
                    errorMessageLabel.setForeground(Color.red);
            }

        });

    }

    private int getSelectedRating(){
        int count = 0;
        int rating = 0;

        for (JRadioButton radioButton : this.ratingButtons) {
            if(radioButton.isSelected())
                rating = count + 1;

            count++;
        }

        return rating;
    }


}
