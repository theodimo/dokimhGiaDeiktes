package gui.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import api.Database;
import api.Lodge;
import api.User;
import gui.bootstrap.Colors;
import gui.components.*;
import gui.components.Panel;
import gui.components.TextField;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static gui.bootstrap.Fonts.*;

public class LodgeProducer extends JFrame {
    private static Color primaryColor = new Color(21, 47, 80);
    private static Color secondaryColor = new Color(5, 68, 94);
    private static Color accentColor = new Color(212, 241, 244);
    public static Color paleBlue = new Color(29, 75, 135);

    //public static Color dark = new Color(20, 20, 20);
    public static Color dark = new Color(56, 233, 246);

    //properties
    private int width = 850;
    private int height = 700;

    private String[] types = {"Apartment", "Maisonette", "Hotel"};

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
    gui.components.Panel fieldsPanel; //the container of all input fields
    gui.components.Panel buttonsPanel; //the container of the buttons

    gui.components.Panel accommodationsPanel; //here i will place the entire functionality and ui display for the accommodations
    gui.components.Panel accommodationBoxesPanel; //here i will place the boxes that display accommodations titles and accommodations themselves

    gui.components.Panel selectedAccommodationsPanel; //here i will place all the accommodations that have been selected

    TitledTextField titleField; //the field for the title of the lodge
    TitledTextField addressField; //the field for the address of the lodge
    TitledTextField cityField; //the field for the city where lodge is
    TitledTextField zipCodeField; //the field for the zipcode area where the lodge is

    ComboBox typeBox; //this combo box contains the types that the lodge can have

    Button2 createButton; //this button is responsible for registering the lodge to the database

    TitledTextArea descriptionField; //an area where provider can describe his lodge

    ComboBox accommodationTitlesBox; //the combo box whose values are the titles of accommodations
    ComboBox accommodationsBox; //the combo box whose values are the accommodations of the selected accommodation-title

    Button2 addAccommodationButton; //a button that is used to add the selected accommodation to the selectedAccommodationsPanel

    public LodgeProducer(Database db, User currentUser) {
        int i;
        //initialization of the jdialog
        this.setSize(this.width, this.height);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.accommodations = new HashMap<>();
        this.db = db;


        //components initialization
        this.fieldsPanel = new gui.components.Panel(this.width, (int) (this.height * 0.9), primaryColor, "preferredSize");
        this.buttonsPanel = new gui.components.Panel(this.width, (int) (this.height * 0.1), primaryColor, "preferredSize");
        this.accommodationsPanel = new gui.components.Panel((int) (this.width * 0.9), 150, secondaryColor, "preferredSize");
        this.accommodationBoxesPanel = new gui.components.Panel((int) (this.accommodationsPanel.getPreferredSize().getWidth() * 0.3), 150, secondaryColor, "preferredSize");
        this.selectedAccommodationsPanel = new Panel((int) (this.accommodationsPanel.getPreferredSize().getWidth() * 0.7), 145, secondaryColor, "preferredSize");

        this.titleField = new TitledTextField("Title", "", "Title should consist from 6 to 20 characters!", false);
        this.addressField = new TitledTextField("Address", "", "Address should have 10 to 20 characters", false);
        this.cityField = new TitledTextField("City", "", "City should consist from 4 to 16 characters", false);
        this.zipCodeField = new TitledTextField("Zip Code", "", "Zip Code should be a 5 digit number", false);
        this.descriptionField = new TitledTextArea((int) (this.width * 0.9), 160, "Description", "", "Description should be at lease 50 characters long",250);

        this.createButton = new Button2("Create Lodge", 200, 50);
        this.addAccommodationButton = new Button2("Add", 120,30);

        for (i = 0; i < this.accommodationTitles.length; i++) {
            this.accommodations.put(this.accommodationTitles[i], this.availableAccommodations[i]);
        }

        this.typeBox = new ComboBox((int) (this.width * 0.25), 50, this.types);
        this.accommodationTitlesBox = new ComboBox(100, 40, this.accommodationTitles);
        this.accommodationsBox = new ComboBox(150, 40, this.accommodations.get(this.accommodationTitlesBox.getSelectedItem()));

        JScrollPane scrollable = new JScrollPane(this.selectedAccommodationsPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //layouts
        this.fieldsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        this.buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.accommodationsPanel.setLayout(new BorderLayout());
        this.accommodationBoxesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 10));
        this.selectedAccommodationsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));


        //styling
        this.typeBox.style(secondaryColor, accentColor, secondaryColor, secondaryColor, mainFont);
        this.createButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.addAccommodationButton.style(accentColor, secondaryColor, paleBlue, inputLabel);
        this.accommodationTitlesBox.style(accentColor, dark, accentColor, accentColor, mainFont);
        this.accommodationsBox.style(accentColor, dark, accentColor, accentColor, mainFont);
        this.fieldsPanel.setOpaque(true);
        scrollable.setBorder(new EmptyBorder(0,0,0,0));

        this.descriptionField.style(primaryColor, secondaryColor, accentColor, dark, mainFont);
        this.titleField.style(primaryColor, secondaryColor, accentColor, dark);
        this.addressField.style(primaryColor, secondaryColor, accentColor, dark);
        this.cityField.style(primaryColor, secondaryColor, accentColor, dark);
        this.zipCodeField.style(primaryColor, secondaryColor, accentColor, dark);

        this.accommodationsBox.setBackground(primaryColor);
        this.accommodationTitlesBox.setBackground(primaryColor);
        this.accommodationsBox.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.accommodationTitlesBox.setBorder(new EmptyBorder(0, 0, 0, 0));


        //components addition
        this.accommodationBoxesPanel.add(this.accommodationTitlesBox);
        this.accommodationBoxesPanel.add(this.accommodationsBox);
        this.accommodationBoxesPanel.add(this.addAccommodationButton);

        this.accommodationsPanel.add(scrollable, BorderLayout.EAST);
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
            //user clicks a value from the combo box that contains the titles of accommodations.
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.selectAccommodationTitle();
            }
        });

        this.addAccommodationButton.addActionListener(e -> {
            //user clicks the button ADD.
            this.selectAccommodation();
        });

        this.createButton.addActionListener(e -> {
            //user clicks the button in the bottom right corner of the dialog.

            //extract the data from the fields
            HashMap<String, String> lodgeData = this.extractData();
            String name = lodgeData.get("name");
            String type = lodgeData.get("type");
            String address = lodgeData.get("address");
            String city = lodgeData.get("city");
            String zipcode = lodgeData.get("zipcode");
            String description = lodgeData.get("description");
            HashMap<String, String[]> selectedAccommodations = this.extractAccommodations();

            //check if data are valid
            boolean titleValidation = this.db.validateLodgeTitle(name);
            boolean addressValidation = this.db.validateLodgeAddress(address);
            boolean cityValidation = this.db.validateLodgeCity(city);
            int zipCodeValidation = this.db.validateLodgeZipCode(zipcode);
            boolean descriptionValidation = this.db.validateLodgeDescription(description);

            if (titleValidation && addressValidation && cityValidation && zipCodeValidation == 0 && descriptionValidation) {
                //if they are valid, create the lodge
                this.db.createLodge(currentUser, name, type, address, city, Integer.parseInt(zipcode), description, selectedAccommodations);

                this.db.printData();
                this.dispose();
            } else {
                //if they are invalid, show the messages of the fields whose values are invalid
                this.showErrorMessages(titleValidation, addressValidation, cityValidation, zipCodeValidation, descriptionValidation);
            }
        });

    }

     /**
     * This constructor takes a lodge as a parameter and fills the fields with lodge's values. It implements the "edit" mode
     * @param currentUser the user that is currently signed in
     * @param lodge the lodge we want to edit
     */
    public LodgeProducer(Database db, User currentUser, Lodge lodge) {
        this(db, currentUser);
        //functionality for "edit" mode
        this.titleField.setText(lodge.getName());
        this.typeBox.setSelectedItem(lodge.getType());
        this.addressField.setText(lodge.getAddress());
        this.cityField.setText(lodge.getCity());
        this.zipCodeField.setText(lodge.getZipCode() + "");
        this.descriptionField.setText(lodge.getDescription());
        this.descriptionField.setCurrentCharacters(lodge.getDescription().length());
        //this.descriptionField.revalidate();
        //this.descriptionField.repaint();

        //move the accommodations of the lodge, to the selectedAccommodations panel
        HashMap<String, String[]> accommodations = lodge.getAccommodations();
        for (String accommodationCategory: accommodations.keySet()) {
            //set the current category as the selected one
            this.accommodationTitlesBox.setSelectedItem(accommodationCategory);
            this.selectAccommodationTitle();

            //for every accommodation that the lodge has, we want to move it from the combo box to the panel with the selectedAccommodations
            for (String accommodationName: accommodations.get(accommodationCategory)) {
                this.accommodationsBox.setSelectedItem(accommodationName);
                this.selectAccommodation();
            }
        }

        //styling
        this.titleField.setForegroundColor(this.titleField.getForegroundColor());
        this.addressField.setForegroundColor(this.addressField.getForegroundColor());
        this.cityField.setForegroundColor(this.cityField.getForegroundColor());
        this.zipCodeField.setForegroundColor(this.zipCodeField.getForegroundColor());
        this.descriptionField.setForeground(this.descriptionField.getForegroundColor());

        //remove the button's actionListener that got added from the main constructor
        ActionListener[] listeners = this.createButton.getActionListeners();
        for (ActionListener listener: listeners) {
            this.createButton.removeActionListener(listener);
        }

        //change the button's text and event listener in the bottom right corner of the screen
        this.createButton.setText("Save");
        this.createButton.addActionListener(e -> {
            //this code will run when user clicks the button which tells "Save"

            //extract the data from the fields
            HashMap<String, String> lodgeData = this.extractData();
            String name = lodgeData.get("name");
            String type = lodgeData.get("type");
            String address = lodgeData.get("address");
            String city = lodgeData.get("city");
            String zipcode = lodgeData.get("zipcode");
            String description = lodgeData.get("description");
            HashMap<String, String[]> selectedAccommodations = this.extractAccommodations();

            //check if data are valid
            boolean titleValidation = this.db.validateLodgeTitle(name);
            boolean addressValidation = this.db.validateLodgeAddress(address);
            boolean cityValidation = this.db.validateLodgeCity(city);
            int zipCodeValidation = this.db.validateLodgeZipCode(zipcode);
            boolean descriptionValidation = this.db.validateLodgeDescription(description);

            if (titleValidation && addressValidation && cityValidation && zipCodeValidation == 0 && descriptionValidation) {
                //if they are valid, save the changes to the lodge
                //update the lodge
                lodge.setName(name);
                lodge.setType(type);
                lodge.setAddress(address);
                lodge.setCity(city);
                lodge.setZipCode(Integer.parseInt(zipcode));
                lodge.setDescription(description);
                lodge.setAccommodations(selectedAccommodations);

                //save the changes to the file. The db is a pointer so the property Lodges of our database changed too
                System.out.println("Current number of lodges: " + this.db.getLodgesCount());
                this.db.saveLodges();
                this.db.createAVL();

                this.dispose();

            } else {
                //if they are invalid, show the error messages of the fields whose values are invalid
                this.showErrorMessages(titleValidation, addressValidation, cityValidation, zipCodeValidation, descriptionValidation);
            }

        });

        //refresh the frame
        //this.revalidate();
        //this.repaint();
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

    /**
     * This function moves the accommodation name that the user has selected, to the selectedAccommodations panel
     */
    private void selectAccommodation() {
        if (this.accommodationsBox.getSelectedItem() != null) {
            //We want to add the selected accommodation to the panel that holds the selected accommodations
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
    }


    public void selectAccommodationTitle() {
        //we want the accommodations of the second combo box to changed based on the selected value of the combo box
        //that contains the categories (titles) of the accommodations
        this.updateAccommodationBoxValues(this.accommodationTitlesBox.getSelectedItem().toString());
    }

    /**
     * This functions creates an arrayList with the values of all the fields in lodgeProducer.
     * @return the arrayList with the data extracted from the fields
     */
    public HashMap<String, String> extractData() {
        //we want to take the values that user has fill in the fields
        //and create a new lodge with these values, if they are adequate

        String name = this.titleField.getText();
        String type = this.typeBox.getSelectedItem().toString();
        String address = this.addressField.getText();
        String city = this.cityField.getText();
        String zipcode;
        if (zipCodeField.getText().length() == 0) {
            zipcode = "wrong"; //we will handle this zipCode at the next phase of the actionListener of the create button
        } else {
            zipcode = zipCodeField.getText() + "";
        }
        String description = this.descriptionField.getText();

        //create the data object for the lodge
        HashMap<String, String> lodgeData = new HashMap<>();
        lodgeData.put("name", name);
        lodgeData.put("type", type);
        lodgeData.put("address", address);
        lodgeData.put("city", city);
        lodgeData.put("zipcode", zipcode);
        lodgeData.put("description", description);

        return lodgeData;
    }

    /**
     * This function creates a hashMap whose keys are accommodation categories (titles), and values are lists with the
     * accommodations that belong to that category. These accommodations are the ones that user selected.
     * @return
     */
    public HashMap<String, String[]> extractAccommodations() {
        HashMap<String, String[]> selectedAccommodations = new HashMap<>(); //the final object that will be used to
        //create a new lodge. Each key is a string that represents a accommodation category (or title). Each value is
        //an array of strings that represent the accommodations that belong to these categories

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

        return selectedAccommodations;
    }

    /**
     * This functions shows the error messages of the fields whose values are invalid
     * @param titleValidation whether title is validated
     * @param addressValidation whether address is validated
     * @param cityValidation whether city is validated
     * @param zipCodeValidation whether zipCode is validated
     * @param descriptionValidation whether description is validated
     */
    public void showErrorMessages(boolean titleValidation, boolean addressValidation, boolean cityValidation, int zipCodeValidation, boolean descriptionValidation) {
        this.titleField.showErrorMessage(!titleValidation);
        this.addressField.showErrorMessage(!addressValidation);
        this.cityField.showErrorMessage(!cityValidation);

        //the zipCode is unique field, because we want to display 2 error messages because its validation consists of 2 causes
        //give zipCode specific error message based on the cause of non-validation
        if (zipCodeValidation == -1) {
            this.zipCodeField.setErrorMessage("ZipCode's length should be 5");
        } else if (zipCodeValidation == 1) {
            this.zipCodeField.setErrorMessage("Zipcode should consist of numeric digits");
        }

        this.zipCodeField.showErrorMessage(zipCodeValidation != 0);
        this.descriptionField.showErrorMessage(!descriptionValidation);

        revalidate();
        repaint();
    }

}
