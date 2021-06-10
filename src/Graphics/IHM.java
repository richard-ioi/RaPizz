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
    JPanel hautFenetre;
    ScrollPane menu;
    JPanel commandes;
    JPanel donneesBrutes;
    JTextField querryDonneesBrutes;
    int Largeur;
    int Hauteur;

    public IHM(int largeur, int hauteur) {
        Largeur=largeur;
        Hauteur=hauteur;
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
        createMenuWindow();
        createCommandesWindow();
        createDonneesBrutesWindow();
        setLayout(new BorderLayout());
        createHautFenetre("Menu");
        add(hautFenetre, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
        setSize(Largeur,Hauteur);
        setResizable(false);
    }

    public void createMenuWindow(){
        JPanel innerPanel=new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));
        for(int i=0;i<30;i++) {
            innerPanel.add(createPizzaBlock(5, "Margarita", "Origan, Oignons, Tomates, Parmesan, Sauce tomate", 5, 10, 15));
            innerPanel.add(createBlankPanel());
        }
        menu=new ScrollPane();
        menu.add(innerPanel);
    }

    public void createCommandesWindow(){
        commandes = new JPanel();
        //commandes.setLayout(new BoxLayout(commandes,BoxLayout.PAGE_AXIS));
        commandes.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel stats= new JPanel();
        stats.add(new Label("Meilleur livreur :"));
        stats.add(new Label("Meilleur client :"));
        commandes.add(stats);
        ScrollPane commandesList=new ScrollPane();
        JPanel innerPanel=new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));
        for(int i=0;i<30;i++) {
            innerPanel.add(createCommandeBlock(10, "Emily RENARD", "Margarita", "EL_PIZZAYOLO93", "04.04.20000", "15.06.2000"));
            innerPanel.add(createBlankPanel());
        }
        commandesList.add(innerPanel);
        commandesList.setPreferredSize(new Dimension(Largeur-10,(Hauteur/3)*2));
        commandes.add(commandesList);
        commandes.add(new Label("Résultats Requête"));
    }

    public void createDonneesBrutesWindow(){
        donneesBrutes = new JPanel();
        JPanel haut = new JPanel();
        JPanel bas = new JPanel();
        querryDonneesBrutes = new JTextField(60);
        querryDonneesBrutes.setSize(60,60);
        haut.add(querryDonneesBrutes);
        haut.add(new JButton("Exécuter la requête SQL"));
        JTable resultsTab=new JTable(1,5);
        bas.add(resultsTab);
        //donneesBrutes.add(new Label("Bienvenue sur l'onglet données brutes"));
        donneesBrutes.add(haut,BorderLayout.NORTH);
        donneesBrutes.add(bas);
    }

    private JPanel createCommandeBlock(int IDCommande, String Client, String Pizza, String Livreur, String Depart_Livraison, String Arrivee_Livraison){
        JPanel commande = new JPanel();
        commande.setLayout(new GridLayout(2,1));
        JPanel haut=new JPanel();
        JPanel bas=new JPanel();
        haut.setLayout(new GridLayout(1,1));
        JLabel commandeIDLabel=new JLabel("COMMANDE ID : "+IDCommande);
        commandeIDLabel.setForeground(Color.white);
        haut.add(commandeIDLabel);
        haut.setBackground(Color.DARK_GRAY);
        bas.setLayout(new GridLayout(1,5));
        bas.add(createCell(Client));
        bas.add(createCell(Pizza));
        bas.add(createCell(Livreur));
        bas.add(createCell(Depart_Livraison));
        bas.add(createCell(Arrivee_Livraison));
        commande.add(haut);
        commande.add(bas);
        commande.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return commande;
    }

    private JPanel createPizzaBlock(int IDPizza, String nomPizza, String Liste_Ingredients, int prix_naine, int prix_normal, int prix_ogresse){
        JPanel pizzaBlock = new JPanel();
        pizzaBlock.setLayout(new GridLayout(2,1));
        JPanel haut=new JPanel();
        JPanel bas=new JPanel();
        JPanel prix=new JPanel();
        JPanel ingredients=new JPanel();
        haut.setLayout(new GridLayout(1,1));
        bas.setLayout(new GridLayout(1,2));
        JLabel commandeIDLabel=new JLabel(nomPizza);
        commandeIDLabel.setForeground(Color.white);
        haut.add(commandeIDLabel);
        haut.setBackground(Color.DARK_GRAY);
        ingredients.add(new Label(Liste_Ingredients));
        prix.setLayout(new GridLayout(1,3));
        prix.add(createDividedCell("Naine",prix_naine+"€"));
        prix.add(createDividedCell("Normale",prix_normal+"€"));
        prix.add(createDividedCell("Ogresse",prix_ogresse+"€"));
        bas.add(ingredients);
        bas.add(prix);
        pizzaBlock.add(haut);
        pizzaBlock.add(bas);
        pizzaBlock.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return pizzaBlock;
    }

    private JPanel createCell(String text){
        JPanel cell=new JPanel();
        cell.add(new Label(text));
        cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return cell;
    }
    private JPanel createDividedCell(String text1, String text2){
        JPanel cell=new JPanel();
        cell.setLayout(new GridLayout(2,1));
        JPanel haut=new JPanel();
        JPanel bas=new JPanel();
        haut.add(new Label(text1));
        haut.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        bas.add(new Label(text2));
        bas.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        cell.add(haut);
        cell.add(bas);
        cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return cell;
    }
    private JPanel createBlankPanel(){
        JPanel blank=new JPanel();
        blank.setPreferredSize(new Dimension(1,15));
        return blank;
    }

    private void createHautFenetre(String titre){
        hautFenetre=new JPanel();
        hautFenetre.setLayout(new GridLayout(2,1));
        JPanel titreFenetre=new JPanel();
        titreFenetre.setBackground(Color.LIGHT_GRAY);
        titreFenetre.add(new Label(titre));
        hautFenetre.add(onglets);
        hautFenetre.add(titreFenetre);
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        getContentPane().removeAll();
        if (source == boutonOngletMenu){
            createHautFenetre("Menu");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(menu);
        }
        if (source == boutonOngletCommandes){
            createHautFenetre("Commandes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(commandes);
        }
        if (source == boutonOngletDonneesBrutes){
            createHautFenetre("Données Brutes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(donneesBrutes);
        }
        repaint();
        printAll(getGraphics());//Extort print all content
    }

    public static void main(String[] arg)
    {
        JFrame f = new IHM(800,800);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        f.setVisible(true);
    }
}