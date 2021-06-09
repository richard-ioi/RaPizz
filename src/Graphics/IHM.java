package Graphics;

import java.awt.*;
import java.awt.event.*;
public class IHM extends Panel
        implements ActionListener {

    Button boutonOngletMenu;
    Button boutonOngletCommandes;
    Button boutonOngletDonneesBrutes;
    Panel onglets;
    Panel menu;
    Panel commandes;
    Panel donneesBrutes;
    public IHM() {
        onglets = new Panel();
        menu = new Panel();
        onglets.setLayout(new GridLayout(1,3));
        boutonOngletMenu = new Button("Données Brutes");
        MeDonnées brutes}

    @OvCommandesde
    public vCommandesactionPerformed(ActionEvent e) {

    }
}
