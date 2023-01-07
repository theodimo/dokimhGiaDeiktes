package gui.screens;

import api.Database;
import api.Review;
import gui.components.ReviewUi;
import gui.components.ScrollableDialog;
import gui.components.ScrollableDialogInterface;

import java.awt.*;
import java.util.ArrayList;

import static gui.bootstrap.Fonts.inputLabel;
import static gui.bootstrap.Fonts.mainFont;

//this class is a dialog. It is responsible for displaying all the reviews that a lodge may have
//it appears when user is at LodgeScreen and clicks "See all reviews" button
public class Reviews extends ScrollableDialog implements ScrollableDialogInterface<ReviewUi> {
    //properties
    private ArrayList<Review> reviews;

    public Reviews(Database db, ArrayList<Review> reviews) {
        super(db, "Reviews", "The reviews for that lodge", 500);
        //initialization
        this.reviews = reviews;

        for (Review review: this.reviews) {
            //create the component
            ReviewUi reviewComponent = new ReviewUi(review, 400, 180);
            //style the component
            reviewComponent.style(primaryColor, secondaryColor, dark, gray, accentColor, dark, inputLabel, smallFont, mainFont, inputLabel);
            //add the component
            this.addElement(reviewComponent);
        }

        //resize the main panel
        this.resizeMainPanel(3);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void resizeMainPanel(int componentCount) {
        int height = (componentCount + 1) * this.verticalGap;
        for (Component component: this.mainPanel.getComponents()) {
            ReviewUi reviewComponent = (ReviewUi) component;
            height += reviewComponent.computeHeight();
        }
        int oldWidth = (int) this.mainPanel.getPreferredSize().getWidth();
        this.mainPanel.setPreferredSize(new Dimension(oldWidth, height));
    }

    @Override
    public void addElement(ReviewUi component) {
        this.mainPanel.add(component);
    }
}
