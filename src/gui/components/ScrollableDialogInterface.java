package gui.components;


import java.awt.*;

public interface ScrollableDialogInterface<T> {
    public void resizeMainPanel(int componentCount); //we want to add components to the main panel of the class dynamically.
    //main panel is scrollable, so we want to resize it everytime we add something to it

    public void addElement(T component); //adds the component to the main panel
}
