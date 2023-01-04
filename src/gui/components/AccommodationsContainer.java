package gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import gui.components.Panel;
import gui.components.Label;
import gui.screens.Accommodations;

//this class is responsible for displays the title of a specific accommodation category, and all the accommodations that belong to it
//the structure we want to achieve is:
//  Title                 ie       Θέα
//    --Accommodation1  ----->       --Θέα σε πισίνα
//    --Accommodation2               --Θέα σε βουνό
//each accommodation will be an instance of the class AccommodationWithIcon. It will display an icon and the name of the accommodation
public class AccommodationsContainer extends JPanel implements ScrollableElement {
    //properties
    private String categoryTitle; //the title of the accommodation category
    private String[] accommodations; //the names of all accommodations that belong to this category
    private int verticalGap; //the space between each component inside mainPanel. Used at resizing

    //components
    Panel titlePanel; //the panel that will wrap the title of the category
    public Panel mainPanel; //the panel that will contain the accommodations

    Label titleLabel; //displays the title of the accommodation

    public AccommodationsContainer(String categoryTitle, String[] accommodations, int width) {
        //initialization
        this.categoryTitle = categoryTitle;
        this.accommodations = accommodations;
        this.verticalGap = 20;

        //components initialization
        this.titlePanel = new Panel(width, 40, null, "preferredSize");
        this.mainPanel = new Panel(width, this.computeMainPanelHeight(20), null, "preferredSize");
        this.titleLabel = new Label(this.categoryTitle, 200, this.verticalGap, false);

        this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, this.verticalGap));
        for (String accommodationName: accommodations) {
            AccommodationWithIcon accommodation = new AccommodationWithIcon(accommodationName);
            this.mainPanel.add(accommodation);
        }

        //layouts
        this.setLayout(new BorderLayout());
        this.titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        //components addition
        this.titlePanel.add(this.titleLabel);

        this.add(this.titlePanel, BorderLayout.NORTH);
        this.add(this.mainPanel, BorderLayout.CENTER);
    }


    public void style(Color backgroundColor, Color titleColor, Color accommodationColor, Font titleFont, Font accommodationFont) {
        this.titlePanel.setBackground(backgroundColor);
        this.mainPanel.setBackground(backgroundColor);
        this.titleLabel.style(backgroundColor, titleColor, titleFont);

        //my goal is to style all the accommodations with the given rules. In order to do this, i will take the accommodations
        //that have been added, remove them from mainPanel, style them, and then add them again
        ArrayList<AccommodationWithIcon> newAccommodations = new ArrayList<>();
        for (Component component: this.mainPanel.getComponents()) {
            AccommodationWithIcon accommodation = (AccommodationWithIcon) component;
            //style the accommodation
            accommodation.style(backgroundColor, accommodationColor, accommodationFont);

            newAccommodations.add(accommodation);
        }
        //remove the accommodations
        this.mainPanel.removeAll();

        //add them again
        for (AccommodationWithIcon accommodation: newAccommodations) {
            this.mainPanel.add(accommodation);
        }

        this.add(this.mainPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    /**
     * Computes and returns the height of the main panel. The function will consider the number of accommodations and the gap
     * @param gap the vertical space between each accommodation
     * @return the total height of the main panel
     */
    private int computeMainPanelHeight(int gap) {
        int totalHeight = this.accommodations.length * 30; //30 because each accommodationWithIcon is 30px long
        totalHeight += (this.accommodations.length + 1) * gap;
        return totalHeight;
    }

    @Override
    public int computeHeight() {
        int height = this.computeMainPanelHeight(20);
        height += this.titlePanel.getPreferredSize().getHeight();
        return height;
    }
}
