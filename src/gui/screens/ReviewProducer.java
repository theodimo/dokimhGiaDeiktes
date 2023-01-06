package gui.screens;

import api.Database;
import api.Lodge;
import gui.components.Button2;
import gui.components.TextArea;

import javax.swing.*;
import java.awt.*;

import static gui.bootstrap.Colors.*;
import static gui.bootstrap.Fonts.*;

public class ReviewProducer extends JDialog {

    String reviewText;
    int rating;

    public ReviewProducer(Lodge lodge){
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
        JLabel titleLabel = new JLabel("Write a review for " + lodge.getName());
        titleLabel.setFont(titleFont);

        TextArea textArea = new TextArea("Write something", 500, 200, 250);
        textArea.style(Color.lightGray,secondaryColor,primaryColor,secondaryColor,mainFont);

        ButtonGroup ratingGroup = new ButtonGroup();

        JRadioButton rating1 = new JRadioButton("1");
        rating1.setBackground(primaryColor);
        rating1.addActionListener(e -> {
            this.rating = 1;
            System.out.println("the rating is " + this.rating);
        });

        JRadioButton rating2 = new JRadioButton("2");
        rating2.setBackground(primaryColor);
        rating2.addActionListener(e -> {
            this.rating = 2;
            System.out.println("the rating is " + this.rating);
        });

        JRadioButton rating3 = new JRadioButton("3");
        rating3.setBackground(primaryColor);
        rating3.addActionListener(e -> {
            this.rating = 3;
            System.out.println("the rating is " + this.rating);
        });

        JRadioButton rating4 = new JRadioButton("4");
        rating4.setBackground(primaryColor);
        rating4.addActionListener(e -> {
            this.rating = 4;
            System.out.println("the rating is " + this.rating);
        });

        JRadioButton rating5 = new JRadioButton("5");
        rating5.setBackground(primaryColor);
        rating5.addActionListener(e -> {
            this.rating = 5;
            System.out.println("the rating is " + this.rating);
        });

        ratingGroup.add(rating1);
        ratingGroup.add(rating2);
        ratingGroup.add(rating3);
        ratingGroup.add(rating4);
        ratingGroup.add(rating5);


        Button2 submitButton = new Button2("Submit",150,40);
        submitButton.style(secondaryColor,Color.lightGray,Color.gray,buttonFont);
        submitButton.addActionListener(e -> {
            this.reviewText = textArea.getText();

            new Database().createReview(lodge.getOwner(),reviewText,this.rating,"12/12/2012");

            this.dispose();
        });

        //adding components to panels
        titlePanel.add(titleLabel);
        ratingPanel.add(rating1);
        ratingPanel.add(rating2);
        ratingPanel.add(rating3);
        ratingPanel.add(rating4);
        ratingPanel.add(rating5);
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
