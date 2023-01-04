package gui.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

import gui.components.*;
import gui.components.Label;
import gui.components.Panel;

import static gui.bootstrap.Fonts.*;

//this class is a dialog. It is responsible for displaying all the accommodations that a lodge may have
//it appears when user is at LodgeScreen and clicks "See all accommodations" button
public class Accommodations extends ScrollableDialog implements ScrollableDialogInterface<AccommodationsContainer> {
    //properties
    private HashMap<String, String[]> accommodations;

    public Accommodations(HashMap<String, String[]> accommodations) {
        super("Accommodations", "What this lodge provides");
        //initialization
        this.accommodations = accommodations;

        for (String accommodationCategory: this.accommodations.keySet()) {
            //create the container
            AccommodationsContainer container = new AccommodationsContainer(accommodationCategory, this.accommodations.get(accommodationCategory), 400);
            //style the container
            container.style(secondaryColor, dark, accentColor, bigFont, mainFont);
            //add the container
            this.addElement(container);
        }

        //resize the main panel
        this.resizeMainPanel(3);

        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        HashMap<String, String[]> accommodations = new HashMap<>() {
            {
                put("Θέα", new String[]{"Θέα σε πισίνα", "Θέα στο βουνό", "Θέα στο λιμάνι", "Θέα σε παραλία"});
                put("Θέρμανση & Κλιματισμός", new String[]{"Εσωτερικό τζάκι", "Κεντρική Θέρμανση"});
                put("Κουζίνα & Τραπεζαρία", new String[]{"Κουζίνα", "Πιάτα και Μαχαιροπίρουνα", "Ψυγείο", "Πλυντήριο Πιάτων"});
            }
        };
        Accommodations acc = new Accommodations(accommodations);
    }

    @Override
    public void resizeMainPanel(int componentCount) {
        int height = (componentCount + 1) * this.verticalGap;
        for (Component comp: this.mainPanel.getComponents()) {
            AccommodationsContainer acc = (AccommodationsContainer) comp;
            height += acc.computeHeight();
        }
        int oldWidth = (int) this.mainPanel.getPreferredSize().getWidth();
        this.mainPanel.setPreferredSize(new Dimension(oldWidth, height));
    }

    @Override
    public void addElement(AccommodationsContainer component) {
        this.mainPanel.add(component);
    }
}
