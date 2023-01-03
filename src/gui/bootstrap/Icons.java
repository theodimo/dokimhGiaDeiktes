package gui.bootstrap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Icons {
    public static ImageIcon pencil = new ImageIcon("src/gui/bootstrap/pencil.png");
    public static ImageIcon trashCan = new ImageIcon("src/gui/bootstrap/trashCan.png");
    public static ImageIcon placeholder = new ImageIcon("src/gui/bootstrap/smallPlaceholder.png");
    public ImageIcon house = new ImageIcon();

    public static HashMap<String, String> accommodationIcons = new HashMap<>() {
        {
            //θέα
            put("Θέα σε πισίνα", "thea-se-pisina.png");
            put("Θέα σε παραλία", "thea-se-paralia.png");
            put("Θέα σε θάλασσα", "thea-se-thalassa.png");
            put("Θέα στο λιμάνι", "thea-sto-limani.png");
            put("Θέα στο βουνό", "thea-sto-bouno.png");
            put("Θέα στο δρόμο", "thea-sto-dromo.png");

            //Μπάνιο
            put("Πιστολάκι μαλλιών", "pistolaki-malliwn.png");

            //Πλύσιμο Ρούχων
            put("Πλυντήριο ρούχων", "plunthrio-rouxwn.png");
            put("Στεγνωτήριο", "stegnwthrio.png");

            //Ψυχαγωγία
            put("Τηλεόραση", "thleorash.png");

            //Θέρμανση & Κλιματισμός
            put("Εσωτερικό τζάκι", "eswteriko-tzaki.png");
            put("Κλιματισμός", "klimatismos.png");
            put("Κεντρική Θέρμανση", "kentrikh-thermansh.png");

            //Διαδίκτυο
            put("Wifi", "wifi.png");
            put("Ethernet", "ethernet.png");

            //Κουζίνα & Τραπεζαρία
            put("Κουζίνα", "kouzina.png");
            put("Ψυγείο", "psygeio.png");
            put("Φούρνος Μικροκυμάτων", "fournos-mikrokumatwn.png");
            put("Μαγειρικά Είδη", "mageirika-eidh.png");
            put("Πιάτα και Μαχαιροπίρουνα", "piata-kai-maxairopirouna.png");
            put("Πλυντήριο Πιάτων", "plhnthrio-piatwn.png");
            put("Καφετιέρα", "kafetieria.png");

            //Εξωτερικός Χώρος
            put("Μπαλκόνι", "mpalkoni.png");
            put("Αυλή", "aulh.png");

            //Χώρος Σταύθμεσης
            put("Δωρεάν χώρος σταύθμεσης στην ιδιοκτησία", "parking-sthn-idiokthsia.png");
            put("Δωρεάν πάρκινγ στο δρόμο", "parking-sto-dromo.png");




        }
    };
}
