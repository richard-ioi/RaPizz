package Graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IHM extends JFrame
        implements ActionListener {

    JButton boutonOngletMenu;
    JButton boutonOngletCommandes;
    JButton boutonOngletDonneesBrutes;
    JPanel onglets;
    JScrollPane menu;
    ScrollPane commandes;
    ScrollPane donneesBrutes;

    public IHM() {
        onglets = new JPanel();
        onglets.setLayout(new GridLayout(1,3));
        boutonOngletMenu = new JButton("Menu");
        boutonOngletCommandes=new JButton("Commandes");
        boutonOngletDonneesBrutes=new JButton("Données brutes");
        onglets.add(boutonOngletMenu);
        onglets.add(boutonOngletCommandes);
        onglets.add(boutonOngletDonneesBrutes);
        boutonOngletMenu.addActionListener(this);
        boutonOngletCommandes.addActionListener(this);
        boutonOngletDonneesBrutes.addActionListener(this);
        getMenuScrollPane();
        getCommandesScrollPane();
        getDonneesBrutesScrollPane();
        setLayout(new BorderLayout());
        add(onglets, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
    }

    public void getMenuScrollPane(){
        JPanel test=new JPanel();
        test.setLayout(new GridLayout(20,1));
        //menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
        test.add(new Label("Bienvenue sur l'onglet menu"));
        test.add(new Label("TEST1"));
        test.add(new Label("TEST2"));
        test.add(new Label("TEST3"));
        menu=new JScrollPane(test);
    }

    public void getCommandesScrollPane(){
        commandes = new ScrollPane();
        commandes.add(new Label("Bienvenue sur l'onglet commandes"));
    }

    public void getDonneesBrutesScrollPane(){
        donneesBrutes = new ScrollPane();
        donneesBrutes.add(new Label("Bienvenue sur l'onglet données brutes"));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        getContentPane().removeAll();
        getContentPane().add(onglets, BorderLayout.NORTH);
        if (source == boutonOngletMenu){
            getContentPane().add(menu);
        }
        if (source == boutonOngletCommandes){
            getContentPane().add(commandes);
        }
        if (source == boutonOngletDonneesBrutes){
            getContentPane().add(donneesBrutes);
        }
        repaint();
        printAll(getGraphics());//Extort print all content
    }

    public static void main(String[] arg)
    {
        JFrame f = new IHM();
        f.setSize(800, 400);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        f.setVisible(true);
    }
}