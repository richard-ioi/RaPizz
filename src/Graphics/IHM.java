package Graphics;

import DataAccess.IHM_ActionListener;
import DataAccess.IHM_MouseListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IHM extends JFrame implements ActionListener {

    public JTextField querryDonneesBrutes;
    JButton boutonOngletMenu;
    JButton boutonOngletCommandes;
    JButton boutonOngletDonneesBrutes;
    JPanel onglets;
    JPanel hautFenetre;
    ScrollPane menuWindow;
    JPanel commandesWindow;
    JPanel donneesBrutesWindow;
    JPanel menuInnerPanel;
    JPanel commandesInnerPanel;
    JTable commandesJTable;
    JTable donneesBrutesJTable;
    Object currentWindow;
    int LargeurFenetre;
    int HauteurFenetre;

    public IHM(int largeur, int hauteur) {
        LargeurFenetre=largeur;
        HauteurFenetre=hauteur;
        onglets = new JPanel();
        menuInnerPanel=new JPanel();
        commandesInnerPanel=new JPanel();
        onglets.setLayout(new GridLayout(1,3));
        menuInnerPanel.setLayout(new BoxLayout(menuInnerPanel,BoxLayout.PAGE_AXIS));
        commandesInnerPanel.setLayout(new BoxLayout(commandesInnerPanel,BoxLayout.PAGE_AXIS));
        boutonOngletMenu = new JButton("Menu");
        boutonOngletCommandes=new JButton("Commandes");
        boutonOngletDonneesBrutes=new JButton("Données brutes");
        onglets.add(boutonOngletMenu);
        onglets.add(boutonOngletCommandes);
        onglets.add(boutonOngletDonneesBrutes);
        commandesJTable=new JTable();
        donneesBrutesJTable=new JTable();
        boutonOngletMenu.addActionListener(this);
        boutonOngletCommandes.addActionListener(this);
        boutonOngletDonneesBrutes.addActionListener(this);
        createMenuWindow();
        createCommandesWindow();
        createDonneesBrutesWindow();
        setLayout(new BorderLayout());
        createHautFenetre("Menu");
        add(hautFenetre, BorderLayout.NORTH);
        add(menuWindow, BorderLayout.CENTER);
        currentWindow=menuWindow;
        setSize(LargeurFenetre,HauteurFenetre);
        setResizable(false);
    }

    public void createMenuWindow(){
        menuWindow=new ScrollPane();
        menuWindow.add(menuInnerPanel);
    }

    public void createCommandesWindow(){
        commandesWindow = new JPanel();
        commandesWindow.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel stats= new JPanel();
        stats.add(new Label("Meilleur livreur :"));
        stats.add(new Label("Meilleur client :"));
        commandesWindow.add(stats);
        ScrollPane commandesList=new ScrollPane();
        commandesList.add(commandesInnerPanel);
        commandesList.setPreferredSize(new Dimension(LargeurFenetre-10,(HauteurFenetre/3)*2));
        commandesWindow.add(commandesList);
        commandesWindow.add(commandesJTable);
    }

    public void createDonneesBrutesWindow(){
        donneesBrutesWindow = new JPanel();
        JPanel haut = new JPanel();
        JPanel bas = new JPanel();
        querryDonneesBrutes = new JTextField(50);
        querryDonneesBrutes.setSize(30,30);
        haut.add(querryDonneesBrutes);
        JButton execQuerryBrutes=new JButton("Exécuter la requête SQL");
        execQuerryBrutes.addActionListener(new IHM_ActionListener(this));
        haut.add(execQuerryBrutes);
        JTable resultsTab=new JTable(1,5);
        bas.add(resultsTab);
        donneesBrutesWindow.add(haut,BorderLayout.NORTH);
        donneesBrutesWindow.add(bas);
    }

    public void createMenuInnerPanel(String nomPizza, String Liste_Ingredients, int prix_naine, int prix_normal, int prix_ogresse){
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
        menuInnerPanel.add(pizzaBlock);
        menuInnerPanel.add(createBlankPanel());
    }

    public void createCommandesInnerPanel(int IDCommande, int IDClient, String NomClient, int IDPizza, String NomPizza, int IDLivreur, String NomLivreur, String DepartLivraison, String ArriveeLivraison){
        JPanel commandeBlock = new JPanel();
        commandeBlock.setLayout(new GridLayout(2,1));
        JPanel haut=new JPanel();
        JPanel bas=new JPanel();
        haut.setLayout(new GridLayout(1,1));
        JLabel commandeIDLabel=new JLabel("COMMANDE ID : "+IDCommande);
        commandeIDLabel.setForeground(Color.white);
        haut.add(commandeIDLabel);
        haut.setBackground(Color.DARK_GRAY);
        bas.setLayout(new GridLayout(1,5));
        bas.add(createCell("CLIENT",IDClient,NomClient));
        bas.add(createCell("PIZZA",IDPizza,NomPizza));
        bas.add(createCell("LIVREUR",IDLivreur,NomLivreur));
        bas.add(createCell("",0,DepartLivraison));
        bas.add(createCell("",0,ArriveeLivraison));
        commandeBlock.add(haut);
        commandeBlock.add(bas);
        commandeBlock.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        commandesInnerPanel.add(commandeBlock);
        commandesInnerPanel.add(createBlankPanel());
    }

    public void createCommandesJTable(String[][] cellules, String[] columnNames){
        commandesJTable=new JTable(new DefaultTableModel(cellules,columnNames));
        reBuildIHM();
    }

    public void createDonneesBrutesJTable(String[][] cellules, String[] columnNames){
        donneesBrutesJTable=new JTable(new DefaultTableModel(cellules,columnNames));
        reBuildIHM();
    }

    public void reBuildIHM(){
        getContentPane().removeAll();

        createMenuWindow();
        createCommandesWindow();
        createDonneesBrutesWindow();

        if (currentWindow == menuWindow){
            createHautFenetre("Menu");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(menuWindow);
        }
        if (currentWindow == commandesWindow){
            createHautFenetre("Commandes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(commandesWindow);
        }
        if (currentWindow == donneesBrutesWindow){
            createHautFenetre("Données Brutes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(donneesBrutesWindow);
        }

        repaint();
        printAll(getGraphics());
    }

    private JPanel createCell(String Type, int ID, String text){
        JPanel cell=new JPanel();
        Label CellLabel=new Label(text);
        CellLabel.addMouseListener(new IHM_MouseListener(Type,ID));
        cell.add(CellLabel);
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
            getContentPane().add(menuWindow);
            currentWindow=menuWindow;
        }
        if (source == boutonOngletCommandes){
            createHautFenetre("Commandes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(commandesWindow);
            currentWindow=commandesWindow;
        }
        if (source == boutonOngletDonneesBrutes){
            createHautFenetre("Données Brutes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(donneesBrutesWindow);
            currentWindow=donneesBrutesWindow;
        }
        repaint();
        printAll(getGraphics());
    }
}