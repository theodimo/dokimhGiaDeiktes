package gui.components;

import javax.swing.*;

import api.Database;
import api.Lodge;
import api.User;
import gui.bootstrap.Colors;
import gui.components.TextField;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import static gui.bootstrap.Fonts.*;

public class LodgeProducer extends JFrame implements ActionListener {
    private static Color primaryColor = new Color(21, 47, 80);
    private static Color secondaryColor = new Color(5, 68, 94);
    private static Color accentColor = new Color(212, 241, 244);
    public static Color paleBlue = new Color(29, 75, 135);

    public static Color dark = new Color(20, 20, 20);

    //properties
    private int width = 850;
    private int height = 650;

    private String[] types = {"Apartment", "Maisonette"};

    private String[] accommodationTitles = {"Θέα", "Μπάνιο", "Πλύσιμο Ρούχων", "Ψυχαγωγία", "Θέρμανση & Κλιματισμός", "Διαδίκτυο", "Κουζίνα & Τραπεζαρία", "Εξωτερικός Χώρος", "Χώρος Σταύθμεσης"};
    private String[][] availableAccommodations = {
            {"Θέα σε πισίνα", "Θέα σε παραλία", "Θέα σε θάλασσα", "Θέα στο λιμάνι", "Θέα στο βουνό", "Θέα στο δρόμο"},
            {"Πιστολάκι μαλλιών"},
            {"Πλυντήριο ρούχων", "Στεγνωτήριο"},
            {"Τηλεόραση"},
            {"Εσωτερικό τζάκι", "Κλιματισμός", "Κεντρική Θέρμανση"},
            {"Wifi", "Ethernet"},
            {"Κουζίνα", "Ψυγείο", "Φούρνος Μικροκυμάτων", "Μαγειρικά Είδη", "Πιάτα και Μαχαιροπίρουνα", "Πλυντήριο Πιάτων", "Καφετιέρα"},
            {"Μπαλκόνι", "Αυλή"},
            {"Δωρεάν χώρος σταύθμεσης στην ιδιοκτησία", "Δωρεάν πάρκινγ στο δρόμο"}
    };

    private HashMap<String, String[]> accommodations; //this object will connect our accommodation titles with the accommodation themselves

    private Database db;
    //components
    Panel fieldsPanel; //the container of all input fields
    Panel buttonsPanel; //the container of the buttons

    Panel accommodationsPanel; //here i will place the entire functionality and ui display for the accommodations
    Panel accommodationBoxesPanel; //here i will place the boxes that display accommodations titles and accommodations themselves

    Panel selectedAccommodationsPanel; //here i will place all the accommodations that have been selected

    TextField titleField; //the field for the title of the lodge
    TextField addressField; //the field for the address of the lodge
    TextField cityField; //the field for the city where lodge is

    TextField zipCodeField; //the field for the zipcode area where the lodge is

    ComboBox typeBox; //this combo box contains the types that the lodge can have

    Button2 createButton; //this button is responsible for registering the lodge to the database

    TextArea descriptionField; //an area where provider can describe his lodge

    ComboBox accommodationTitlesBox; //the combo box whose values are the titles of accommodations
    ComboBox accommodationsBox; //the combo box whose values are the accommodations of the selected accommodation-title

    Button2 addAccommodationButton; //a button that is used to add the selected accommodation to the selectedAccommodationsPanel

    public LodgeProducer(User currentUser) {
        int i;
        //initialization of the jdialog
        this.setSize(this.width, this.height);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.accommodations = new HashMap<>();
        this.db = new Database();


        //components initialization
        this.fieldsPanel = new Panel(this.width, (int) (this.height * 0.85), primaryColor, "preferredSize");
        this.buttonsPanel = new Panel(this.width, (int) (this.height * 0.15), primaryColor, "preferredSize");
        this.accommodationsPanel = new Panel((int) (this.width * 0.9), 150, secondaryColor, "preferredSize");
        this.accommodationBoxesPanel = new Panel((int) (this.accommodationsPanel.getPreferredSize().getWidth() * 0.3), 150, secondaryColor, "preferredSize");
        this.selectedAccommodationsPanel = new Panel((int) (this.accommodationsPanel.getPreferredSize().getWidth() * 0.7), 145, secondaryColor, "preferredSize");

        this.titleField = new TextField((int) (this.width * 0.4), 50, secondaryColor, accentColor, dark, accentColor,"Title");
        this.addressField = new TextField((int) (this.width * 0.3), 50, secondaryColor, accentColor, dark, accentColor,"Address");
        this.cityField = new TextField((int) (this.width * 0.2), 50, secondaryColor, accentColor, dark, accentColor, "City");
        this.zipCodeField = new TextField((int) (this.width * 0.2), 50, secondaryColor, accentColor, dark, accentColor, "Zip Code");
        this.descriptionField = new TextArea("Description", (int) (this.width * 0.9), 100, 250);

        this.createButton = new Button2("Create Lodge", 200, 50);
        this.addAccommodationButton = new Button2("Add", 120,30);

        for (i = 0; i < this.accommodationTitles.length; i++) {
            this.accommodations.put(this.accommodationTitles[i], this.availableAccommodations[i]);
        }

        this.typeBox = new ComboBox((int) (this.width * 0.25), 50, this.types);
        this.accommodationTitlesBox = new ComboBox(100, 40, this.accommodationTitles);
        this.accommodationsBox = new ComboBox(150, 40, this.accommodations.get(this.accommodationTitlesBox.getSelectedItem()));


        //layouts
        this.fieldsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 40));
        this.buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.accommodationsPanel.setLayout(new BorderLayout());
        this.accommodationBoxesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 10));
        this.selectedAccommodationsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));


        //styling
        this.descriptionField.style(secondaryColor, accentColor, dark, accentColor, mainFont);
        this.typeBox.style(secondaryColor, accentColor, secondaryColor, secondaryColor, mainFont);
        this.createButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.addAccommodationButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.accommodationTitlesBox.style(accentColor, dark, accentColor, accentColor, mainFont);
        this.accommodationsBox.style(accentColor, dark, accentColor, accentColor, mainFont);
        this.fieldsPanel.setOpaque(true);


        //components addition
        this.accommodationBoxesPanel.add(this.accommodationTitlesBox);
        this.accommodationBoxesPanel.add(this.accommodationsBox);
        this.accommodationBoxesPanel.add(this.addAccommodationButton);

        this.accommodationsPanel.add(new JScrollPane(this.selectedAccommodationsPanel,
                                                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
                                    BorderLayout.EAST);
        this.accommodationsPanel.add(this.accommodationBoxesPanel, BorderLayout.WEST);


        this.fieldsPanel.add(this.titleField);
        this.fieldsPanel.add(this.typeBox);
        this.fieldsPanel.add(this.addressField);
        this.fieldsPanel.add(this.cityField);
        this.fieldsPanel.add(this.zipCodeField);
        this.fieldsPanel.add(this.descriptionField);
        this.fieldsPanel.add(this.accommodationsPanel);

        this.buttonsPanel.add(this.createButton);

        this.add(this.buttonsPanel, BorderLayout.SOUTH);
        this.add(this.fieldsPanel, BorderLayout.NORTH);
        this.setVisible(true);

        //listeners
        this.accommodationTitlesBox.addItemListener(e -> {
            //user clicks a value from the combo box that contains the titles of accommodations. We want the accommodations
            //of the second combo box to changed based on that value
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.updateAccommodationBoxValues((String) e.getItem());
            }
        });

        this.addAccommodationButton.addActionListener(e -> {
            if (this.accommodationsBox.getSelectedItem() != null) {
                //user clicks the button ADD. We want to add the selected accommodation to the panel that holds the selected accommodations
                //first, create the accommodation:
                String accommodationName = this.accommodationsBox.getSelectedItem().toString();
                int accommodationTitleIndex = this.accommodationTitlesBox.getSelectedIndex();
                Accommodation accommodation = new Accommodation(accommodationName, accommodationTitleIndex, 350, 20);
                accommodation.style(secondaryColor, accentColor, Color.RED, smallFont);

                //add listener to accommodation
                accommodation.deleteButton.addActionListener(e2 -> {
                    this.removeAccommodation(accommodation);
                    this.addAccommodationValue(accommodation);
                });

                //then, add the accommodation to the panel and remove it from the combo box with accommodations
                this.addAccommodation(accommodation);
                this.removeAccommodationValue(accommodationName);
            }
        });

        this.createButton.addActionListener(e -> {
            //user clicks the button in the bottom right corner of the dialog. We want to take the values that user has fill in the fields
            //and create a new lodge with these values, if they are adequate
            HashMap<String, String[]> selectedAccommodations = new HashMap<>(); //the final object that will be used to
            //create a new lodge. Each key is a string that represents a accommodation category (or title). Each value is
            //a array of strings that represent the accommodations that belong to these categories

            //build the object. I will iterate through all Accommodations in selectedAccommodationsPanel, and add their name to
            //the object. If category doesn't exist, then create it.
            String[] accommodationNames = {};
            for (Component component: this.selectedAccommodationsPanel.getComponents()) {
                Accommodation accommodation = (Accommodation) component;
                String name = accommodation.name; //the name of the accommodation
                int titleIndex = accommodation.titleIndex;
                String title = this.accommodationTitles[titleIndex]; //the title of the category that the accommodation belongs to

                if (selectedAccommodations.containsKey(title)) {
                    //the category already exists at the HashMap, so just add the name to the current values
                    accommodationNames = selectedAccommodations.get(title);
                    accommodationNames = this.addItemToArray(accommodationNames, name);
                } else {
                    //the category does not exist. Create it and add the name inside it
                    accommodationNames = new String[]{name};
                }
                selectedAccommodations.put(title, accommodationNames);
            }

            //get the rest of the essential properties for lodge creation
            String name = this.titleField.getText();
            String type = this.typeBox.getSelectedItem().toString();
            String address = this.addressField.getText();
            String city = this.cityField.getText();
            int zipcode = Integer.parseInt(this.zipCodeField.getText());
            String description = this.descriptionField.getText();


            currentUser.addEntry(this.db.createLodge(currentUser,name, type, address, city, zipcode, description, selectedAccommodations));
            this.dispose();

        });

    }


     /**
     * This function changes the values of the second combo box inside accommodation panel based on the values of the selected item
     * of the first combo box. For example, if 1st combo box had "Θέα" as selected item, and user click the item "Πλύσιμο Ρούχων", we
     * we want the values of the 2nd combo box to be "Πλυντήριο ρούχων", "Στεγνωτήριο"
     * @param newAccommodationTitle  The title of the selected accommodation after change
     */
    public void updateAccommodationBoxValues(String newAccommodationTitle) {
        String[] newValues = this.accommodations.get(newAccommodationTitle);
        //remove all previous values
        this.accommodationsBox.removeAllItems();
        //put new values
        for (String value: newValues) {
            this.accommodationsBox.addItem(value);
        }
    }

     /**
     * Removes the given accommodation gui component from the selectedAccommodationsPanel
     * @param accommodation the accommodation component to be removed
     */
    public void removeAccommodation(Accommodation accommodation) {
        this.selectedAccommodationsPanel.remove(accommodation);
        this.resizeSelectedAccommodationsPanel((int) accommodation.getPreferredSize().getHeight(), 20);
        this.revalidate();
        this.repaint();
    }

     /**
     * Adds the given accommodation gui component to the selectedAccommodationsPanel
     * @param accommodation the accommodation component to be added
     */
    public void addAccommodation(Accommodation accommodation) {
        this.selectedAccommodationsPanel.add(accommodation);
        this.resizeSelectedAccommodationsPanel((int) accommodation.getPreferredSize().getHeight(), 20);
        this.revalidate();
        this.repaint();
    }

     /**
     * When we add or remove an accommodation from selectedAccommodationsPanel, it doesn't resize automatically. So, make
     * sure that it does, by setting its preferred size to be the sum of height of all the accommodations, plus the spaces between them
     * @param height the height of an accommodation
     * @param verticalPadding the space between 2 accommodations
     */
    public void resizeSelectedAccommodationsPanel(int height, int verticalPadding) {
        int totalAccommodations = this.selectedAccommodationsPanel.getComponentCount();
        int panelHeight = totalAccommodations * height + (totalAccommodations + 1) * verticalPadding; //the height of the extended selectedAccommodationsPanel
        int currentWidth = (int) this.selectedAccommodationsPanel.getPreferredSize().getWidth();
        this.selectedAccommodationsPanel.setPreferredSize(new Dimension(currentWidth, panelHeight));
    }

     /**
     * This function adds the name of the given accommodation to this.accommodations object, at its parent category. Also, we have
     * two occasions. If the selected accommodations title is the same title with the title that the accommodation belongs to,
     * ie, 'Θέα στο βουνό' belongs to 'Θέα', then add the accommodation to the accommodationsBox. Otherwise, if the 2 titles that i just
     * mentioned are different, we only want to add the accommodation to the object: this.accommodations object, in order to be fetched and appeared
     * when user selects the category of accommodations that the given accommodation belongs to
     * @param accommodation the accommodation that we want to put its name to this.accommodations
     */
    public void addAccommodationValue(Accommodation accommodation) {
        int accommodationTitleIndex = accommodation.titleIndex;
        String accommodationName = accommodation.name;
        //if selected title is the same with the title of the category that the accommodation belongs to
        if (this.accommodationTitlesBox.getSelectedIndex() == accommodationTitleIndex) {
            this.accommodationsBox.addItem(accommodationName);
        }
        //here we want to store the previous values of accommodations at the category that the given accommodation belongs to
        String title = this.accommodationTitles[accommodationTitleIndex];
        String[] prev = this.accommodations.get(title);
        //then add the name of the accommodation
        prev = this.addItemToArray(prev, accommodationName);
        //and put the result back to the original object
        this.accommodations.put(title, prev);
        this.revalidate();
        this.repaint();
    }

     /**
     * Does the opposite work of addAccommodationValue. This function removes the name of the given accommodation from the this.accommodations object
     * At the first place, it removes the name of the accommodation from the accommodations box. Then, we want to find the index of the accommodation's name
     * inside an array that holds the names of all accommodations that belong to the same category. After that, we remove the given accommodation
     * and store the result back to the original object
     * @param accommodationName the accommodation that we want to pop its name from this.accommodations
     */
    public void removeAccommodationValue(String accommodationName) {
        this.accommodationsBox.removeItem(accommodationName);
        String title = this.accommodationTitlesBox.getSelectedItem().toString();
        String[] accommodations = this.accommodations.get(title);
        //find the index of the accommodation inside accommodations object
        int foundPos = this.findItemAtArray(accommodations, accommodationName);
        if (foundPos != -1) {
            accommodations = this.removeItemFromArray(accommodations, foundPos);
        }
        this.accommodations.put(title, accommodations);
        this.revalidate();
        this.repaint();
    }

     /**
     * Removes the item at the given index from the given array
     * @param array The array where we will remove the item from
     * @param index The index of the item that will be removed
     * @return the altered array, ie the array after removal
     */
    private String[] removeItemFromArray(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != index) {
                newArray[k] = array[i];
                k += 1;
            }
        }
        return newArray;
    }

     /**
     * Adds the item at the given index to the given array
     * @param array The array where we will put the item in
     * @param newItem The item that will be added
     * @return the altered array, ie the array after addition
     */
    private String[] addItemToArray(String[] array, String newItem) {
        String[] newArray = new String[array.length + 1];
        int i;
        for (i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[i] = newItem;
        return newArray;
    }

     /**
     * Searches the given item at the given array
     * @param array the array where the search will be performed
     * @param item the item whose position we want find at the given array
     * @return the position of the item in the array, or -1 if item does not exist
     */
    private int findItemAtArray(String[] array, String item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        LodgeProducer l = new LodgeProducer((User) null);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
