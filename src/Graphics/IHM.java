package Graphics;

import DataAccess.IHM_ActionListener;
import DataAccess.IHM_MouseListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IHM extends JFrame implements ActionListener {

    public JTextField queryDonneesBrutes;
    JButton boutonOngletMenu;
    JButton boutonOngletCommandes;
    JButton boutonOngletDonneesBrutes;
    JButton boutonOngletStats;
    JButton boutonOngletClients;
    JPanel onglets;
    JPanel hautFenetre;
    ScrollPane menuWindow;
    JPanel clientsInnerPanel;
    JPanel statsWindow;
    JPanel clientsWindow;
    JPanel commandesWindow;
    JPanel donneesBrutesWindow;
    JPanel menuInnerPanel;
    JPanel statsInnerPanel;
    JPanel commandesInnerPanel;
    JPanel commandesStatsPanel;
    JTable commandesJTable;
    JTable donneesBrutesJTable;
    String currentWindow;
    int LargeurFenetre;
    int HauteurFenetre;

    public IHM(int largeur, int hauteur) {
        LargeurFenetre=largeur;
        HauteurFenetre=hauteur;
        initInnerPanels();
        createMenuWindow();
        createCommandesWindow();
        createStatsWindow();
        createClientsWindow();
        createDonneesBrutesWindow();
        createOngletButtons();
        setLayout(new BorderLayout());
        createHautFenetre("Menu");
        add(hautFenetre, BorderLayout.NORTH);
        add(menuWindow, BorderLayout.CENTER);
        currentWindow="menuWindow";
        setSize(LargeurFenetre,HauteurFenetre);
        setResizable(false);
    }

    public void initInnerPanels(){
        menuInnerPanel=new JPanel();
        commandesInnerPanel=new JPanel();
        commandesStatsPanel=new JPanel();
        statsInnerPanel=new JPanel();
        menuInnerPanel.setLayout(new BoxLayout(menuInnerPanel,BoxLayout.PAGE_AXIS));
        commandesInnerPanel.setLayout(new BoxLayout(commandesInnerPanel,BoxLayout.PAGE_AXIS));
        clientsInnerPanel.setLayout(new BoxLayout(commandesInnerPanel,BoxLayout.PAGE_AXIS));
        commandesStatsPanel.setLayout(new GridLayout(1,3));
        commandesJTable=new JTable();
        donneesBrutesJTable=new JTable();
        clientsInnerPanel=new JPanel();
    }
    public void createOngletButtons(){
        onglets = new JPanel();
        onglets.setLayout(new GridLayout(1,5));
        boutonOngletMenu = new JButton("Menu");
        boutonOngletCommandes=new JButton("Commandes");
        boutonOngletStats=new JButton("Statistiques globales");
        boutonOngletClients=new JButton("Clients");
        boutonOngletDonneesBrutes=new JButton("Données brutes");
        boutonOngletMenu.addActionListener(this);
        boutonOngletCommandes.addActionListener(this);
        boutonOngletStats.addActionListener(this);
        boutonOngletClients.addActionListener(this);
        boutonOngletDonneesBrutes.addActionListener(this);
        onglets.add(boutonOngletMenu);
        onglets.add(boutonOngletCommandes);
        onglets.add(boutonOngletStats);
        onglets.add(boutonOngletClients);
        onglets.add(boutonOngletDonneesBrutes);
    }

    public void createStatsWindow(){
        statsWindow=new JPanel();
        statsWindow.add(statsInnerPanel);
    }

    public void createClientsWindow(){
        clientsWindow=new JPanel();
       // clientsWindow.setLayout(new FlowLayout(FlowLayout.CENTER));
        ScrollPane clientsList=new ScrollPane();
        clientsList.add(clientsInnerPanel);
        clientsList.setPreferredSize(new Dimension(LargeurFenetre-10,(HauteurFenetre)));
        clientsWindow.add(clientsList);
    }

    public void createMenuWindow(){
        menuWindow=new ScrollPane();
        menuWindow.setPreferredSize(new Dimension(LargeurFenetre-10,(HauteurFenetre/3)*2));
        menuWindow.add(menuInnerPanel);
        //menuWindow.setPreferredSize(new Dimension(LargeurFenetre-10,(HauteurFenetre/3)*2));
    }

    public void createCommandesWindow(){
        commandesWindow = new JPanel();
        commandesWindow.setLayout(new FlowLayout(FlowLayout.CENTER));
        commandesWindow.add(commandesStatsPanel);
        ScrollPane commandesList=new ScrollPane();
        commandesList.add(commandesInnerPanel);
        commandesList.setPreferredSize(new Dimension(LargeurFenetre-10,(HauteurFenetre/3)*2));
        commandesWindow.add(commandesList);
        commandesWindow.add(new JScrollPane(commandesJTable));
    }

    public void createDonneesBrutesWindow(){
        donneesBrutesWindow = new JPanel();
        JPanel haut = new JPanel();
        JPanel bas = new JPanel();
        queryDonneesBrutes = new JTextField(50);
        queryDonneesBrutes.setSize(30,30);
        haut.add(queryDonneesBrutes);
        JButton execQuerryBrutes=new JButton("Exécuter la requête SQL");
        execQuerryBrutes.addActionListener(new IHM_ActionListener(this));
        haut.add(execQuerryBrutes);
        JPanel tablePanel=new JPanel();
        tablePanel.add(donneesBrutesJTable, BorderLayout.CENTER);
        tablePanel.add(donneesBrutesJTable.getTableHeader(), BorderLayout.NORTH);
        bas.add(new JScrollPane(donneesBrutesJTable));
        donneesBrutesWindow.add(haut,BorderLayout.NORTH);
        donneesBrutesWindow.add(bas);
    }

    public void createMenuInnerPanel(String nomPizza, String Liste_Ingredients, double prix_naine, double prix_normal, double prix_ogresse){
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
        prix.add(createDividedCell("Naine",prix_naine+" €",Color.DARK_GRAY));
        prix.add(createDividedCell("Normale",prix_normal+" €",Color.DARK_GRAY));
        prix.add(createDividedCell("Ogresse",prix_ogresse+" €",Color.DARK_GRAY));
        bas.add(ingredients);
        bas.add(prix);
        pizzaBlock.add(haut);
        pizzaBlock.add(bas);
        pizzaBlock.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        menuInnerPanel.add(pizzaBlock);
        menuInnerPanel.add(createBlankPanel());
    }

    public void createCommandesInnerPanel(int IDCommande, int IDClient, String NomClient, int IDPizza, String NomPizza, int IDLivreur, String NomLivreur, String DepartLivraison, String ArriveeLivraison, String PrixCommande){
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
        bas.add(createCell("CLIENT",IDClient,NomClient,Color.DARK_GRAY));
        bas.add(createCell("PIZZA",IDPizza,NomPizza,Color.DARK_GRAY));
        bas.add(createCell("LIVREUR",IDLivreur,NomLivreur,Color.DARK_GRAY));
        bas.add(createCell("",0,DepartLivraison,Color.DARK_GRAY));
        bas.add(createCell("",0,ArriveeLivraison,Color.DARK_GRAY));
        bas.add(createCell("",0,PrixCommande+" €",Color.RED));
        commandeBlock.add(haut);
        commandeBlock.add(bas);
        commandeBlock.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        commandesInnerPanel.add(commandeBlock);
        commandesInnerPanel.add(createBlankPanel());
    }

    public void createCommandesStatsPanel(String pireLivreur, String meilleurClient){
        commandesStatsPanel=new JPanel();
        commandesStatsPanel.setLayout(new GridLayout(1,3));
        commandesStatsPanel.add(new Label("Pire livreur : "+pireLivreur));
        commandesStatsPanel.add(createBlankPanel());
        commandesStatsPanel.add(new Label("Meilleur client :"+meilleurClient));
        reBuildIHM();
    }

    public void createCommandesJTable(String[][] cellules, String[] columnNames){
        commandesJTable=new JTable(new DefaultTableModel(cellules,columnNames));
        reBuildIHM();
    }

    public void createDonneesBrutesJTable(String[][] cellules, String[] columnNames){
        donneesBrutesJTable=new JTable(new DefaultTableModel(cellules,columnNames));
        reBuildIHM();
    }

    // Moyenne des commandes
    // Quels sont les véhicules n'ayant jamais servi
    // Suivi du chiffre d'affaires
    // Identification de la pizza la plus / la moins demandée
    // Ingrédient favori
    public void createStatsInnerPanel(int moyenneCommandes, String vehiculesJamaisServi, double chiffreDaffaire, String pizzaPlusDemandee, String pizzaMoinsDemandee, String ingredientFavori){
        statsInnerPanel=new JPanel();
        statsInnerPanel.setLayout(new GridLayout(3,3));
        JLabel moyenneCommandesLabel=new JLabel("Moyenne des commandes : "+String.valueOf(moyenneCommandes));
        JLabel vehNonServisLabel=new JLabel("Véhicules jamais servis : "+ vehiculesJamaisServi);
        JLabel chiffreDaffaireLabel=new JLabel("Chiffre d'affaires : "+ String.valueOf(chiffreDaffaire));
        JLabel pizzaPlusDemandeeLabel=new JLabel("Pizza la plus demandée : "+ pizzaPlusDemandee);
        JLabel pizzaMoinsDemandeeLabel=new JLabel("Pizza la moins demandée : "+pizzaMoinsDemandee);
        JLabel ingredientFavoriLabel=new JLabel("Ingrédient favori : "+ingredientFavori);
        statsInnerPanel.add(moyenneCommandesLabel);
        statsInnerPanel.add(createBlankPanel());
        statsInnerPanel.add(chiffreDaffaireLabel);
        statsInnerPanel.add(vehNonServisLabel);
        statsInnerPanel.add(createBlankPanel());
        statsInnerPanel.add(pizzaPlusDemandeeLabel);
        statsInnerPanel.add(pizzaMoinsDemandeeLabel);
        statsInnerPanel.add(createBlankPanel());
        statsInnerPanel.add(ingredientFavoriLabel);

        reBuildIHM();
    }

    // Extraction des clients ayant commandé plus que la moyenne
    // Nombre de commandes par clients

    public void createClientInnerPanel(int IDClient, String nomClient, int nbCommandes, boolean commandePlusQueLaMoyenne){
        JPanel clientBlock = new JPanel();
        Color c;
        if(commandePlusQueLaMoyenne){
            c=Color.GREEN;
        }else{
            c=Color.DARK_GRAY;
        }
        clientBlock.setLayout(new GridLayout(2,1));
        JPanel haut=new JPanel();
        JPanel bas=new JPanel();
        haut.setLayout(new GridLayout(1,1));
        JLabel IDClientLabel=new JLabel("ID Client: "+IDClient);
        IDClientLabel.setForeground(Color.white);
        haut.add(IDClientLabel);
        haut.setBackground(c);
        bas.setLayout(new GridLayout(1,2));
        bas.add(createCell("",0,nomClient,c));
        bas.add(createCell("",0, String.valueOf(nbCommandes),c));
        clientBlock.add(haut);
        clientBlock.add(bas);
        clientBlock.setBorder(BorderFactory.createLineBorder(c));
        clientsInnerPanel.add(clientBlock);
        clientsInnerPanel.add(createBlankPanel());
    }

    private JPanel createCell(String Type, int ID, String text, Color c){
        JPanel cell=new JPanel();
        Label CellLabel=new Label(text);
        CellLabel.addMouseListener(new IHM_MouseListener(Type,ID));
        cell.add(CellLabel);
        cell.setBorder(BorderFactory.createLineBorder(c));//Color.DARK_GRAY
        return cell;
    }
    private JPanel createDividedCell(String text1, String text2, Color c){
        JPanel cell=new JPanel();
        cell.setLayout(new GridLayout(2,1));
        JPanel haut=new JPanel();
        JPanel bas=new JPanel();
        haut.add(new Label(text1));
        haut.setBorder(BorderFactory.createLineBorder(c));
        bas.add(new Label(text2));
        bas.setBorder(BorderFactory.createLineBorder(c));
        cell.add(haut);
        cell.add(bas);
        cell.setBorder(BorderFactory.createLineBorder(c));
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

    public void reBuildIHM(){
        getContentPane().removeAll();

        createMenuWindow();
        createCommandesWindow();
        createDonneesBrutesWindow();
        createStatsWindow();
        createClientsWindow();

        if (currentWindow == "menuWindow"){
            createHautFenetre("Menu");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(menuWindow);
        }
        if (currentWindow == "commandesWindow"){
            createHautFenetre("Commandes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(commandesWindow);
        }
        if (currentWindow == "donneesBrutesWindow"){
            createHautFenetre("Données Brutes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(donneesBrutesWindow);
        }
        if (currentWindow == "clientsWindow"){
            createHautFenetre("Clients");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(clientsWindow);
        }
        if (currentWindow == "statsWindow"){
            createHautFenetre("Statistiques globales");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(statsWindow);
        }

        repaint();
        printAll(getGraphics());
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        getContentPane().removeAll();
        if (source == boutonOngletMenu){
            createHautFenetre("Menu");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(menuWindow);
            currentWindow="menuWindow";
        }
        if (source == boutonOngletCommandes){
            createHautFenetre("Commandes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(commandesWindow);
            currentWindow="commandesWindow";
        }
        if (source == boutonOngletDonneesBrutes){
            createHautFenetre("Données Brutes");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(donneesBrutesWindow);
            currentWindow="donneesBrutesWindow";
        }
        if (source == boutonOngletClients){
            createHautFenetre("Clients");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(clientsWindow);
            currentWindow="clientsWindow";
        }
        if (source == boutonOngletStats){
            createHautFenetre("Statistiques globales");
            getContentPane().add(hautFenetre, BorderLayout.NORTH);
            getContentPane().add(statsWindow);
            currentWindow="statsWindow";
        }
        repaint();
        printAll(getGraphics());
    }
}